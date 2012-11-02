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

package ca.qc.adinfo.rouge.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

public class MD5 {

	public static String hash(String value) {

		try {
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5hash = new byte[32];
			md.update(value.getBytes("iso-8859-1"), 0, value.length());
			md5hash = md.digest();

			return Hex.encodeHexString(md5hash);

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
