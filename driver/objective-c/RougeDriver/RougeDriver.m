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



- (id) initWithHandler:(id<RougeListener>)listener {
    
    self = [super init];
    if (self) {
        
        communicator = [[RougeCommunicator alloc] init];
        [communicator retain];
        [communicator setListener:listener];
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

- (void) send:(NSString *)command withPayLoad:(RougeObject *)payload {
        
    [communicator send:command withPayLoad:payload];
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
