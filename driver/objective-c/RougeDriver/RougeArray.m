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

#import "RougeArray.h"
#import "RougeObject.h"
#import "RougeDataWrapper.h"

@implementation RougeArray

- (id)init
{
    self = [super init];
    if (self) {
        content = [[NSMutableArray alloc] init];
        [content retain];
    }
    
    return self;
}

- (void) addNSArray:(NSArray*) fromArray {
    
    self = [super init];
    if (self) {
        content = [[NSMutableArray alloc] init];
        [content retain];
        
        for (NSObject* value in fromArray) {
            
            if ([value isKindOfClass:[NSString class]]) {
                
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kString];
                [content addObject:dataWrapper];
                
            } else if ([value isKindOfClass:[NSNumber class]]) {
                
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kInteger];
                [content addObject:dataWrapper];
            
            } else if ([value isKindOfClass:[NSDictionary class]]) {
                
                RougeObject *rougeObject = [[RougeObject alloc] init];
                [rougeObject addNSDictionary:(NSDictionary *)value];
                
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:rougeObject withType:kRougeObject];
                [content addObject:dataWrapper];
                
            } else if ([value isKindOfClass:[NSArray class]]) {
                
                RougeArray *rougeArray = [[RougeArray alloc] init];
                [rougeArray addNSArray:(NSArray *)value];
                
                RougeDataWrapper *dataWrapper = [[RougeDataWrapper alloc] initWithObject:rougeArray withType:kRougeArray];
                [content addObject:dataWrapper];
            }
            
        }

    }
}

- (bool) getBoolean:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    NSNumber* number = (NSNumber *)[dataWrapper data];
    
    return [number boolValue];
}

- (int) getInt:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    NSNumber* number = (NSNumber *)[dataWrapper data];
    
    return [number intValue];
}

- (long) getLong:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    NSNumber* number = (NSNumber *)[dataWrapper data];
    
    return [number longValue];
}

- (float) getFloat:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    NSNumber* number = (NSNumber *)[dataWrapper data];
    
    return [number floatValue];
}

- (double) getDouble:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    NSNumber* number = (NSNumber *)[dataWrapper data];
    
    return [number doubleValue];
}

- (NSString *) getString:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    
    return (NSString *)[dataWrapper data];
}

- (RougeObject *) getRougeObject:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    
    return (RougeObject *)[dataWrapper data];
}

- (RougeArray *) getRougeArray:(NSUInteger) index {
    
    RougeDataWrapper* dataWrapper = [content objectAtIndex:index];
    
    return (RougeArray *)[dataWrapper data];
}

- (void) addBoolean:(bool) value {
    
    NSNumber* number = [NSNumber numberWithBool:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kBoolean];
    
    [content addObject:dataWrapper];
}

- (void) addInt:(int) value {
    
    NSNumber* number = [NSNumber numberWithInt:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kInteger];
    
    [content addObject:dataWrapper];
}

- (void) addLong:(long) value {
    
    NSNumber* number = [NSNumber numberWithLong:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kLong];
    
    [content addObject:dataWrapper];
}

- (void) addFloat:(float) value {
    
    NSNumber* number = [NSNumber numberWithFloat:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kFloat];
    
    [content addObject:dataWrapper];
}

- (void) addDouble:(double) value {
    
    NSNumber* number = [NSNumber numberWithDouble:value];
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:number withType:kDouble];
    
    [content addObject:dataWrapper];
}

- (void) addString:(NSString *) value {
    
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kString];
    
    [content addObject:dataWrapper];
}

- (void) addRougeObject:(RougeObject *) value {
    
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kRougeObject];
    
    [content addObject:dataWrapper];
}

- (void) addRougeArray:(RougeArray *) value {
    
    RougeDataWrapper* dataWrapper = [[RougeDataWrapper alloc] initWithObject:value withType:kRougeArray];
    
    [content addObject:dataWrapper];
}

- (NSUInteger) count {
    
    return [content count];
}

- (NSMutableArray *) toArray {
    
    NSMutableArray* array = [[NSMutableArray alloc] init]; 
    
    for (RougeDataWrapper* dataWrapper in content) {
                
        if ([dataWrapper type] == kRougeArray) {
            
            RougeArray* rougeArray = (RougeArray *)[dataWrapper data];
            [array addObject:[rougeArray toArray]];
            
        } else if ([dataWrapper type] == kRougeObject) {
            
            RougeObject* rougeObject = (RougeObject *)[dataWrapper data];
            [array addObject:[rougeObject toDictionary]];
            
        } else {
            
            [array addObject:[dataWrapper data]];
        }
    }
    
    return array;
}

- (void)dealloc
{
    [content release];
    [super dealloc];
}

@end
