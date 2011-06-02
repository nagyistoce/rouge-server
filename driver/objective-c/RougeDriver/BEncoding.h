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

#import "BData.h"

@interface BEncoding : NSObject { }

+(NSData *)encodeObject:(NSDictionary *)dict;
+(NSArray *)decodeObject:(NSData *)sourceData;

+ (NSNumber *)bdecodeInt:(BData *)bData withSeperator:(char)seperator;
+ (NSNumber *)bdecodeInt:(BData *)bData;
+ (NSString *) bdecodeString:(BData *)bData;
+ (NSArray *) bdecodeArray:(BData *)bData;
+ (NSDictionary *) bdecodeDict:(BData *)bData;
+ (NSObject *)bdecode:(BData *) bData;

+(void)bencode:(NSObject *)obj toBuffer:(NSMutableString *)buffer;


@end
