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

using System;
using System.IO;
using System.Text;
using System.Collections.Generic;

namespace Rouge
{
	public class BEncode
	{
		
		public static List<IDictionary<string,object>> Decode(string message)	
		{	
			return Decode(System.Text.Encoding.ASCII.GetBytes(message));
		}

		public static List<IDictionary<string,object>> Decode(byte[] message)
		{
			// There might be more than one object in message
			List<IDictionary<string,object>> val = new List<IDictionary<string,object>>();
			int index = 1;
			int sizeMessage = message.Length;
				
			while(index < sizeMessage) {
					
				val.Add(bdecodeDict(message, ref index));
			}
				
			return val;
		}
		
		public static byte[] Encode(object x)
		{
			MemoryStream stream = new MemoryStream();
			bencodeRec(x, stream);
			
			//stream.WriteByte(10);
			return stream.ToArray();
		}
		
		private static int bdecodeInt(byte[] x, ref int index, char limit)
		{
			
			int val = 0;
			string s = "";
			
			while (x[index] != limit)
			{
				s += (char) x[index];
				index++;
			}
			
			index++;
			
			if (!int.TryParse(s, out val))
			{
				throw new BEncodeException("Integer value is invalid");
			}	
			
			return val;
		}
			
		private static int bdecodeInt(byte[] x, ref int index)
		{
			return bdecodeInt(x, ref index, 'e');
		}

		private static byte[] bdecodeBytes(byte[] x, ref int index)
		{
			
			int sizeBytes = bdecodeInt(x, ref index, ':');

			if (sizeBytes > 0)
			{
				byte[] val = new byte[sizeBytes];
				Buffer.BlockCopy(x, index, val, 0, sizeBytes);
				index += sizeBytes;
				return val;
			}
				
			return null;
		}

		public static string bdecodestring(byte[] x, ref int index)
		{
			string val = "";
			
			int sizestring = bdecodeInt(x, ref index, ':');

			if (sizestring > 0) {
					
				StringBuilder sb = new StringBuilder(val);
				int i = index;
				index += sizestring;
					
				while (i < index)
				{
					sb.Append((char) x[i++]);
				}
					
				return sb.ToString();
			}
				
			return val;
		}
			
		public static object bdecodeList(byte[] x, ref int index)
		{
			List<object> val = new List<object>();
				
			while (x[index] != 'e')
			{
				object v = bdecodeRec(x, ref index);
				val.Add(v);
			}
				
			index++;
				
			return val;
		}

		public static Dictionary<string, object> bdecodeDict(byte[] x, ref int index)
		{
			Dictionary<string, object> dict = new Dictionary<string, object>();
			
			while (x[index] != 'e')
			{
				string key = bdecodestring(x, ref index);		
				object val = bdecodeRec(x, ref index);
		
				dict.Add(key,  val);
			}
			index++;
				
			return dict;
		}

		public static object bdecodeRec(byte[] x, ref int index)
		{
			byte type = x[index];

			if (type == 'i')
			{
				index++;
				return bdecodeInt(x, ref index);
			}
			else if (type == 'l')
			{
				index++;
				return bdecodeList(x, ref index);
			}
			else if (type == 'd')
			{
				index++;
				return bdecodeDict(x, ref index);
			}
			else
				return bdecodestring(x, ref index);
		}
		
		public static void bencodeRec(object x, MemoryStream stream)
		{
			if (x is int || x is long || x is ulong || x is uint)
			{
				byte[] op = Encoding.UTF8.GetBytes(string.Format("i{0:d}e", x));
				stream.Write(op, 0, op.Length);
			}
			else if (x is string)
			{
				byte[] op = Encoding.UTF8.GetBytes(string.Format("{0:d}:{1}", ((string) x).Length, x));
				stream.Write(op, 0, op.Length);
			}
			else if (x is byte[])
			{
				byte[] op = Encoding.UTF8.GetBytes(string.Format("{0:d}:", ((byte[]) x).Length));
				stream.Write(op, 0, op.Length);
				op = (byte[]) x;
				stream.Write(op, 0, op.Length);
			}
			else if (x is IList<object>) {
				stream.WriteByte((byte)'l');				
				IList<object> a = (IList<object>) x;
				for (int i=0; i < a.Count; i++)
				{
					bencodeRec(a[i], stream);
				}
				stream.WriteByte((byte)'e');
			}
			else if (x is IDictionary<string, object>)
			{
				stream.WriteByte((byte)'d');

				IDictionary<string, object> dict = (IDictionary<string, object>) x;
				List<object> keys = new List<object>(dict.Keys);

				foreach(string key in keys)
				{
					bencodeRec(key, stream);
					bencodeRec(dict[key], stream);
				}
					
				stream.WriteByte((byte)'e');
			}
			else
				throw new BEncodeException("Unknown Type");
		}
	}
}

