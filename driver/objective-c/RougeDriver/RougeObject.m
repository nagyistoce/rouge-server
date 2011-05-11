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

#import "RougeObject.h"
#import "RougeArray.h"
#import "RougeDataWrapper.h"

@implementation RougeObject

- (id)init
{
    self = [super init];
    if (self) {
        content = [[NSMutableDictionary alloc] init]; 
        [content retain];
    }
    
    return self;
}



- (id)init:(NSString*) jsonString {
    
    self = [super init];
    if (self) {
        content = [[NSMutableDictionary alloc] init]; 
        [content retain];
        
        NSError *error = nil;
        NSData *jsonData = [jsonString dataUsingEncoding:NSUTF32BigEndianStringEncoding];
        NSDictionary *dictionary = [[CJSONDeserializer deserializer] deserializeAsDictionary:jsonData error:&error];
        
        [self addNSDictionary:dictionary];
    }
    
    return self;
}
        
- (void) addNSDictionary:(NSDictionary *) fromDict {        
        
        for (NSString* key in fromDict) {
            
            id value = [fromDict objectForKey:key];
                        
            if ([value isKindOfClass:[NSString class]]) {
            
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kString];
                [content setObject:dataWrapper forKey:key];
            
            } else if ([value isKindOfClass:[NSNumber class]]) {
            
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kInteger];
                [content setObject:dataWrapper forKey:key];
            
            } else if ([value isKindOfClass:[NSDictionary class]]) {
                
                RougeObject *rougeObject = [[RougeObject alloc] init];
                [rougeObject addNSDictionary:value];
                
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:rougeObject withType:kRougeObject];
                [content setObject:dataWrapper forKey:key];
                
            } else if ([value isKindOfClass:[NSArray class]]) {
                
                RougeArray *rougeArray = [[RougeArray alloc] init];
                [rougeArray addNSArray:value];
                
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:rougeArray withType:kRougeArray];
                [content setObject:dataWrapper forKey:key];
            }
        }
}

- (bool) getBoolean:(NSString *) key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    NSNumber* number = (NSNumber*)[dataWrapper data];
    
    return [number boolValue];
}

- (int) getInt:(NSString *)key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    NSNumber* number = (NSNumber*)[dataWrapper data];
    
    return [number intValue];
}

- (long) getLong:(NSString *) key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    NSNumber* number = (NSNumber*)[dataWrapper data];
    
    return [number longValue];
}

- (float) getFloat:(NSString *) key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    NSNumber* number = (NSNumber*)[dataWrapper data];
    
    return [number floatValue];
}

- (double) getDouble:(NSString *) key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    NSNumber* number = (NSNumber*)[dataWrapper data];
    
    return [number doubleValue];
}

- (NSString *) getString:(NSString *) key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    
    return (NSString *)[dataWrapper data];
}

- (RougeObject *) getRougeObject:(NSString *) key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    
    return (RougeObject *)[dataWrapper data];
}

- (RougeArray *) getRougeArray:(NSString *) key {
    
    RougeDataWrapper *dataWrapper = [content objectForKey:key];
    
    return (RougeArray *)[dataWrapper data];
}

- (void) putBoolean:(bool)value withKey:(NSString *)key {
    
    NSNumber* number = [NSNumber numberWithBool:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kBoolean];
    
    [content setObject:dataWrapper forKey:key];
    
}

- (void) putInt:(int)value withKey:(NSString *)key {
    
    NSNumber* number = [NSNumber numberWithInt:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kInteger];
    
    [content setObject:dataWrapper forKey:key];
}

- (void) putLong:(long)value withKey:(NSString *)key{
    
    NSNumber* number = [NSNumber numberWithLong:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kLong];
    
    [content setObject:dataWrapper forKey:key];
}

- (void) putFloat:(float)value withKey:(NSString *)key {
    
    NSNumber* number = [NSNumber numberWithFloat:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kFloat];
    
    [content setObject:dataWrapper forKey:key];
}

- (void) putDouble:(double)value withKey:(NSString *)key {
    
    NSNumber* number = [NSNumber numberWithDouble:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kDouble];
    
    [content setObject:dataWrapper forKey:key];
}

- (void) putString:(NSString *)value withKey:(NSString *)key {
    
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kString];
    
    [content setObject:dataWrapper forKey:key];
}

- (void) putRougeObject:(RougeObject *)value withKey:(NSString *)key {
    
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kRougeObject];
    
    [content setObject:dataWrapper forKey:key];
}

- (void) putRougeArray:(RougeArray *)value withKey:(NSString *)key {
    
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kRougeArray];
    
    [content setObject:dataWrapper forKey:key];
}

- (NSMutableDictionary *) toDictionary {
    
   NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    
   for (NSString* key in content) {
       
        RougeDataWrapper* dataWrapper = [content objectForKey:key];
        
       if ([dataWrapper type] == kRougeArray) {
            
            RougeArray* rougeArray = (RougeArray *)[dataWrapper data];
            [dict setObject:[rougeArray toArray]  forKey:key];
            
        } else if ([dataWrapper type] == kRougeObject) {
            
            RougeObject* rougeObject = (RougeObject *)[dataWrapper data];
            [dict setObject:[rougeObject toDictionary] forKey:key];
            
        } else {
            
            [dict setObject:[dataWrapper data] forKey:key];
        }
    }
    
    return dict;
}

- (void)dealloc
{
    
    [content release];
    
    [super dealloc];
}

@end
