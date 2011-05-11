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
    RougeDriver *driver = [[RougeDriver alloc] initWithHandler:self];
    [driver connect:@"localhost" toPort:6611 withBEncoding:false];
    [driver login:@"objective-c" withPassword:@"password"];
    
    [driver joinRoom:@"main"];
    
    RougeObject *obj = [[RougeObject alloc] init];
    [obj retain];
    [obj putString:@"message" withKey:@"hello!"];
    
    [driver sayInRoom:@"main" withMessage:obj];
    
    [obj release];
    
}

- (void) handleMessage:(NSString *)command withPayLoad:(RougeObject *)RougeObject {
    
    NSLog(@"Message received! %@ %i", command, [RougeObject getBoolean:@"ret"]);
}

@end
