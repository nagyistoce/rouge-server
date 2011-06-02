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

#import "RougeDriverAppDelegate.h"

@implementation RougeDriverAppDelegate

@synthesize window;

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
    driver = [[RougeDriver alloc] initWithHandler:self];
    
    [driver connect:@"localhost" toPort:6612 withBEncoding:true];
    
}


- (void) onConnect {
                    
    NSLog(@"On Connect");
    
    RougeObject *payload = [[RougeObject alloc] init]; 
    [payload putString:@"bob" withKey:@"username"];
    [payload putString:@"password" withKey:@"password"];
    
    [driver send:@"login" withPayLoad:payload];
    
    payload = [[RougeObject alloc] init]; 
    [payload putString:@"main" withKey:@"name"];
    
    [driver send:@"room.create" withPayLoad:payload];
    
    [payload putString:@"message" withKey:@"hello!"];
    
    [driver send:@"room.say" withPayLoad:payload];   
}
                    
- (void) onDisconnect {

    NSLog(@"On Disconnect");
}
                    

- (void) onMessage:(NSString *)command withPayLoad:(RougeObject *)rougeObject {
    
    NSLog(@"Message received! %@ %@", command,  [rougeObject toDictionary]);
}

@end
