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

package ca.qc.adinfo.rouge.bencode;

import ca.qc.adinfo.rouge.data.RougeArray;
import ca.qc.adinfo.rouge.data.RougeObject;

public class BEncoder {

	 public static byte[] bencode(RougeObject o) {
	
		 return bencodeSomething(o).getBytes();
	 }
	 
	 public static String bencodeSomething(Object toEncode) {
		 
		 if (toEncode instanceof Long) {
			 return bencodeNumber((Long)toEncode);
		 } else if (toEncode instanceof Integer) {
			 return bencodeNumber(((Integer)toEncode).longValue());
		 } else if (toEncode instanceof String) {
			 return bencodeString((String)toEncode);
		 } else if (toEncode instanceof RougeArray) {
			 return ((RougeArray)toEncode).toBEncode();
		 } else if (toEncode instanceof RougeObject) {
			 return ((RougeObject)toEncode).toBEncode();
		 } 
		 
		 System.out.println(toEncode.getClass());
		 
		 return null;
	 }
	 
	 public static String bencodeString(String toEncode) {
		 return toEncode.length() + (BConstant.STRING_SEPERATOR + toEncode);
	 }
	 
	 public static String bencodeNumber(Long l) {
		 return BConstant.NUMBER_START + Long.toString(l) + BConstant.NUMBER_END;
	 }

}
