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
@class RougeArray;

@interface RougeObject : NSObject {
@private
    
    NSMutableDictionary *content;
}

- (id)init;
- (id)init:(NSString*) jsonString;

- (void) addNSDictionary:(NSDictionary *) fromDict;

- (bool) getBoolean:(NSString *) key;
- (int) getInt:(NSString *)key;
- (long) getLong:(NSString *) key;
- (float) getFloat:(NSString *) key;
- (double) getDouble:(NSString *) key;
- (NSString *) getString:(NSString *) key;
- (RougeObject *) getRougeObject:(NSString *) key;
- (RougeArray *) getRougeArray:(NSString *) key;

- (void) putBoolean:(bool)value withKey:(NSString *)key;
- (void) putInt:(int)value withKey:(NSString *)key;
- (void) putLong:(long)value withKey:(NSString *)key;
- (void) putFloat:(float)value withKey:(NSString *)key;
- (void) putDouble:(double)value withKey:(NSString *)key;
- (void) putString:(NSString *)value withKey:(NSString *)key;
- (void) putRougeObject:(RougeObject *)value withKey:(NSString *)key;
- (void) putRougeArray:(RougeArray *)value withKey:(NSString *)key;

- (NSMutableDictionary *) toDictionary;

@end
