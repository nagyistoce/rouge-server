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

@class RougeObject;
@class RougeArray;

@interface RougeArray : NSObject {
@private
    
    NSMutableArray *content;
}

- (id)init;

- (void) addNSArray:(NSArray*) fromArray;

- (bool) getBoolean:(NSUInteger) index;
- (int) getInt:(NSUInteger) index;
- (long) getLong:(NSUInteger) index;
- (float) getFloat:(NSUInteger) index;
- (double) getDouble:(NSUInteger) index;
- (NSString *) getString:(NSUInteger) index;
- (RougeObject *) getRougeObject:(NSUInteger) index;
- (RougeArray *) getRougeArray:(NSUInteger) index;

- (void) addBoolean:(bool) value;
- (void) addInt:(int) value;
- (void) addLong:(long) value;
- (void) addFloat:(float) value;
- (void) addDouble:(double) value;
- (void) addString:(NSString *) value;
- (void) addRougeObject:(RougeObject *) value;
- (void) addRougeArray:(RougeArray *) value;

- (NSUInteger) count;
- (NSMutableArray *) toArray;

@end
