//
//  BData.m
//  RougeDriver
//
//  Created by Alexandre Denault on 11-06-01.
//  Copyright 2011 ADInfo. All rights reserved.
//

#import "BData.h"

@implementation BData

- (id)initWithData:(NSData*)sourceData
{
    self = [super init];
    if (self) {
        
        index = 0;
        length = [sourceData length];
        
        data = malloc(length);
        [sourceData getBytes:data length:length];
    }
    
    return self;
}


-(NSUInteger)getLength {
    
    return length;
}

-(bool) isFinished {
    
    return (index >= length);
}

-(char) getNext {
    
    char c = data[index];
    index++;
    
    return c;
}

-(char) peekNext {
        
    return data[index];
}


- (void)dealloc
{
    free(data);
    [super dealloc];
}

@end
