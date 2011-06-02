//
//  BData.h
//  RougeDriver
//
//  Created by Alexandre Denault on 11-06-01.
//  Copyright 2011 ADInfo. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface BData : NSObject {
@private
    char* data;
    NSUInteger index;
    NSUInteger length;
    
}

- (id)initWithData:(NSData*)sourceData;

-(NSUInteger)getLength;
-(bool) isFinished;
-(char) getNext;
-(char) peekNext;

@end
