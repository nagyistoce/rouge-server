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

#import <Foundation/Foundation.h>
#import "CJSONDeserializer.h"
#import "CJSONSerializer.h"

@class RougeObject;

@protocol RougeListener

- (void) onConnect;
- (void) onDisconnect;
- (void) onMessage:(NSString *)command withPayLoad:(RougeObject *)RougeObject;

@end

@interface RougeCommunicator : NSObject <NSStreamDelegate> {
	
	id<RougeListener> listener;
    
    NSString *host;
    int port;
	
	NSInputStream *inputStream;
	NSOutputStream *outputStream;
	
	NSMutableArray *msgStack;
	NSMutableString *msgBuffer;
    	
	unsigned long socketLoad;
	
	bool outputIdle;

    bool bEncode;
}

@property(nonatomic, retain) NSString *host;
@property(nonatomic) int port;

@property(nonatomic, retain) id<RougeListener> listener;
@property(nonatomic) unsigned long socketLoad;

@property(nonatomic) bool bEncode;

- (void) setupConnection;
- (void) open;
- (void) close;

- (void) send:(NSString *)command withPayLoad:(RougeObject *)RougeObject;

@end


