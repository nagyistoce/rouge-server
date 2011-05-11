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

#import "RougeDriver.h"


@implementation RougeDriver



- (id) initWithHandler:(id<MsgHandler>)msgHandler {
    
    self = [super init];
    if (self) {
        
        communicator = [[RougeCommunicator alloc] init];
        [communicator retain];
        [communicator setMsgHandler:msgHandler];
    }
    
    return self;
}

- (void) connect:(NSString *)host toPort:(int)port withBEncoding:(bool)useBEncode {
    
    [communicator setBEncode:useBEncode];
    [communicator setHost:host];
    [communicator setPort:port];
    [communicator setupConnection];
    [communicator open];
    
}

- (void) disconnect {
    
    [communicator close];
}

- (void) login:(NSString *)username withPassword:(NSString *)password {
    
    RougeObject* rougeObject = [[RougeObject alloc] init];
    [rougeObject putString:username withKey:@"username"];
    [rougeObject putString:password withKey:@"password"];
    
    [communicator send:@"login" withPayLoad:rougeObject];
    
}

- (void) createRoom:(NSString *)name {
    
    RougeObject* rougeObject = [[RougeObject alloc] init];
    [rougeObject putString:name withKey:@"name"];
    
    [communicator send:@"room.create" withPayLoad:rougeObject];
    
}

- (void) joinRoom:(NSString *)name {
    
    RougeObject* rougeObject = [[RougeObject alloc] init];
    [rougeObject putString:name withKey:@"name"];
    
    [communicator send:@"room.join" withPayLoad:rougeObject];
    
}

- (void) leaveRoom:(NSString *)name {

    RougeObject* rougeObject = [[RougeObject alloc] init];
    [rougeObject putString:name withKey:@"name"];
    
    [communicator send:@"room.leave" withPayLoad:rougeObject];
    
}

- (void) destroyRoom:(NSString *)name {
    
    RougeObject* rougeObject = [[RougeObject alloc] init];
    [rougeObject putString:name withKey:@"name"];
    
    [communicator send:@"room.destroy" withPayLoad:rougeObject];
    
}

- (void) sayInRoom:(NSString *)name withMessage:(RougeObject *)message {
    
    RougeObject* rougeObject = [[RougeObject alloc] init];
    [rougeObject putString:name withKey:@"name"];
    [rougeObject putRougeObject:message withKey:@"message"];
    
    [communicator send:@"room.say" withPayLoad:rougeObject];
}

- (unsigned long) getLoad {
    
    return [communicator socketLoad];
}

- (void)dealloc
{
    [communicator release];
    [super dealloc];
}

@end
