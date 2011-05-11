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
#import "RougeCommunicator.h"
#import "RougeArray.h"
#import "RougeObject.h"

@interface RougeDriver : NSObject {
@private
    RougeCommunicator* communicator;
    
}

- (id) initWithHandler:(id<MsgHandler>)msgHandler;

- (void) connect:(NSString *)host toPort:(int)port withBEncoding:(bool)useBEncode;
- (void) disconnect;

- (void) login:(NSString *)username withPassword:(NSString *)password;

- (void) createRoom:(NSString *)name;
- (void) joinRoom:(NSString *)name;
- (void) leaveRoom:(NSString *)name;
- (void) destroyRoom:(NSString *)name;
- (void) sayInRoom:(NSString *)name withMessage:(RougeObject *)message;

- (unsigned long) getLoad;

@end
