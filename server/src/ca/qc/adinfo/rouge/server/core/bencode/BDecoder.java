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

package ca.qc.adinfo.rouge.server.core.bencode;

import java.io.ByteArrayInputStream;

import ca.qc.adinfo.rouge.data.RougeArray;
import ca.qc.adinfo.rouge.data.RougeObject;

public class BDecoder {

	//private static Logger log = Logger.getLogger(BDecoder.class);
	
	public static RougeObject bDecode(ByteArrayInputStream bytes) {
		
		Object o = bDecodeSomething(bytes);
		
		return (RougeObject)o;	
	}
	
	private static Object bDecodeSomething(ByteArrayInputStream bytes) {
	
		return bDecodeSomething(bytes, bytes.read());
	}
	
	private static Object bDecodeSomething(ByteArrayInputStream bytes, int i) {
		
		if (i == BConstant.DICTIONARY_START) {
			return bDecodeDictionary(bytes);
		} else if (i == BConstant.LIST_START) {
			return bDecodeArray(bytes);
		} else if (i == BConstant.NUMBER_START) {
			return bDecodeNumber(bytes);
		} else if ( Character.isDigit((char)i) ){
			return bDecodeString(bytes, (char)i);
		} else {
			return null;
		}
	}
	
	private static RougeObject bDecodeDictionary(ByteArrayInputStream bytes) {
		
		RougeObject rougeObject = new RougeObject();
		
		int i = bytes.read();
		
		while(i != BConstant.DICTIONARY_END) {
			
			Object key = bDecodeSomething(bytes, i);
			Object value = bDecodeSomething(bytes);
			
			rougeObject.put((String)key, value);
			
			i = bytes.read();
		}
		
		return rougeObject;
	}
	
	private static RougeArray bDecodeArray(ByteArrayInputStream bytes) {
		
		RougeArray rougeArray = new RougeArray();
		
		int i = bytes.read();
		
		while(i != BConstant.LIST_END) {
			
			Object value = bDecodeSomething(bytes, i);
			
			rougeArray.add(value);
			
			i = bytes.read();
		}
		
		return rougeArray;
	}
	
	private static long bDecodeNumber(ByteArrayInputStream bytes) {
		
		StringBuffer stringBuffer = new StringBuffer();
		
		int i = bytes.read();
		
		while(i != BConstant.NUMBER_END) {
			
			stringBuffer.append(new Character((char)i));
			i = bytes.read();
		}
		
		return Long.parseLong(stringBuffer.toString());
	}
	
	private static String bDecodeString(ByteArrayInputStream bytes, int firstCharacter) {
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(new Character((char)firstCharacter));
		
		int i = bytes.read();
		
		while( i != BConstant.STRING_SEPERATOR) {
			
			stringBuffer.append(new Character((char)i));
			i = bytes.read();
		}
		
		int strLen = Integer.parseInt(stringBuffer.toString());
		
		stringBuffer = new StringBuffer();
		 
		for(int j = 0; j < strLen; j++) {
			char c = (char)bytes.read();
			stringBuffer.append(c);
		}
		
		return stringBuffer.toString();
	}

	
	
}
