/*
 * Copyright [2011] [ADInfo, Alexandre Denault]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Learned to write this using
// http://tacticadev.wordpress.com/2010/03/02/iphone-client-for-multiplayer-with-nsstreams/

#import "BEncoding.h"
#import "RougeCommunicator.h"
#import "RougeObject.h"

@implementation RougeCommunicator

@synthesize host;
@synthesize port;

@synthesize msgHandler;
@synthesize socketLoad;

@synthesize bEncode;

-(id) init {
	
	if( (self=[super init] )) {
	
		msgStack = [[NSMutableArray alloc] init];
		[msgStack retain];
		msgBuffer = [[NSMutableString alloc] initWithString:@""];
		[msgBuffer retain];
	
	}
	
	return self;
}

- (void) setupConnection {
	
	CFReadStreamRef readStream;
	CFWriteStreamRef writeStream;
	
    NSString *myHost = [NSString stringWithFormat:@"http://%@/", host];
	NSURL* serverURL=[NSURL URLWithString:myHost];
    
	NSLog(@"Setting up connection to %@ : %i",[serverURL absoluteString], port);
	
	CFStreamCreatePairWithSocketToHost(
		kCFAllocatorDefault, (CFStringRef)[serverURL host], port, &readStream, &writeStream);
	
	if (!CFWriteStreamOpen(writeStream)) {
		NSLog(@"Error , writestream not open");
		return;
	}
	
	inputStream = (NSInputStream *)readStream;
	outputStream = (NSOutputStream *)writeStream;
	[inputStream retain];
	[outputStream retain];
	
	[self open]; 
	
	outputIdle=NO;
}


- (void) open {

	[inputStream setDelegate:self];
	[outputStream setDelegate:self];
	[inputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
	[outputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
	[inputStream open];
	[outputStream open];
}

- (void) close{
	
	[inputStream close];
	[outputStream close];
	[inputStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
	[outputStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
	[inputStream setDelegate:nil];
	[outputStream setDelegate:nil];
	[inputStream release];
	[outputStream release];
	inputStream = nil;
	outputStream = nil;
}

- (void) writeToOutput:(NSString*)s {
	
	uint8_t* buf=(uint8_t*)[s UTF8String];
	[outputStream write:buf maxLength:strlen((char*)buf)];  
}

- (void) tellAboutNewOutMsg {
	
	if (outputIdle) { 
		
		[self writeToOutput:[msgStack objectAtIndex:0]];
		[msgStack removeObjectAtIndex:0];
		outputIdle=NO;
	}
	
}

- (void) send:(NSString *)command withPayLoad:(RougeObject *)RougeObject {
    
    NSMutableDictionary* toSend = [[NSMutableDictionary alloc] init];
    [toSend setObject:command forKey:@"command"];
    [toSend setObject:[RougeObject toDictionary]  forKey:@"payload"];
    
    if (bEncode) {
        NSData* bEncodeData = [BEncoding encodedDataFromObject:toSend];
        NSString *bEncodeString = [[NSString alloc] initWithData:bEncodeData encoding:NSASCIIStringEncoding];
        
        //NSLog(@"%@", bEncodeString);
        
        [msgStack addObject:bEncodeString];
    } else {
        NSString *jsonData = [[CJSONSerializer serializer] serializeDictionary:toSend];
        jsonData = [jsonData stringByAppendingString:@"|\n"];
	
        [msgStack addObject:jsonData];
    }
    //NSLog(@"json is %@", jsonData);
	//NSLog(@"Message '%@' added, size of msgStack is %i",jsonData, [msgStack count]);
	
	[self tellAboutNewOutMsg];
}

- (void) read {
	
	uint8_t buf[1024];
	long len = 0;
	len = [inputStream read:buf maxLength:1024];
	
	if (len>0) {
		
		NSMutableData* data=[[NSMutableData alloc] initWithLength:0];
		[data appendBytes: (const void *)buf length:len];
		
        NSError *error = nil;
        NSString* command = nil;
        RougeObject* payload = [[RougeObject alloc] init];
		
        if (bEncode) {
            
            NSString *fromNetwork = [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding];
            NSLog(@"%@", fromNetwork);
            
            NSData* dataConverted =[fromNetwork dataUsingEncoding:NSASCIIStringEncoding];
            
            NSDictionary *dictionary = [BEncoding objectFromEncodedData:dataConverted];
            
            command = [dictionary valueForKey:@"command"];
            [payload addNSDictionary:[dictionary valueForKey:@"payload"]];
            
            [msgBuffer release];
            msgBuffer = [[NSMutableString alloc] init];
            [msgBuffer retain];

            NSLog(@"Received command %@ %@" , command, error);
            
            [msgHandler handleMessage:command withPayLoad:payload];
            
        } else {
            
            NSString *fromNetwork = [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding];
            [msgBuffer appendString:fromNetwork];
            
            NSArray *chunks = [msgBuffer componentsSeparatedByString: @"|"];					
            socketLoad = [chunks count] - 1;
            
            for (int i = 0; i < socketLoad; i++) {
                
                NSString *chunk = [chunks objectAtIndex:i];
                
                chunk = [chunk stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
                
                NSData *jsonData = [chunk dataUsingEncoding:NSUTF32BigEndianStringEncoding];
                NSDictionary *dictionary = [[CJSONDeserializer deserializer] deserializeAsDictionary:jsonData error:&error];
                
                command = [dictionary valueForKey:@"command"];
                
                [payload addNSDictionary:[dictionary valueForKey:@"payload"]];
                
                NSLog(@"Received command %@ %@" , command, error);
                
                [msgHandler handleMessage:command withPayLoad:payload];
            }
            
            [msgBuffer release];
            msgBuffer = [[NSMutableString alloc] initWithString:[chunks objectAtIndex:socketLoad]];
            [msgBuffer retain];

        }
						
		[data release];
	}
}

- (void)stream:(NSStream *)stream handleEvent:(NSStreamEvent)eventCode {
	
	//NSLog(@"stream:handleEvent: is invoked...");
	
	switch(eventCode) {
			
		case NSStreamEventHasSpaceAvailable:
	
			if (stream == outputStream) {
				
				if ([msgStack count]>0) {
					[self writeToOutput:[msgStack objectAtIndex:0]];
					[msgStack removeObjectAtIndex:0];
				} else {
					outputIdle=YES;
				}
            }
			break;
			
		case NSStreamEventHasBytesAvailable:
			
			if (stream== inputStream) {
				//NSLog(@"inputStream has bytes available"); 
				
					[self read];
			} 
			break;
            
		
		default:
			NSLog(@"Stream is sending an Event: %lu",eventCode);
			break;
		
	}
}

- (void)dealloc {
	
	[msgStack release];
	[msgBuffer release];
	[super dealloc];
}

@end
