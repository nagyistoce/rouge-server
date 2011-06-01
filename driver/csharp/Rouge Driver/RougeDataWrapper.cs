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
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json.Linq;

namespace Rouge
{
    public class RougeDataWrapper
    {
        private Object value;
        //private RougeType type;

        public RougeDataWrapper(Object value)
        {

	        if (value is IDictionary<object, object>) 
			{
	            this.value = new RougeObject((IDictionary<object, object>) value);	
	        }
			else if (value is IDictionary<string, object>) 
			{
	            this.value = new RougeObject((IDictionary<string, object>) value);	
	        }
			else if (value is List<object> || value is JArray) 
			{
	            this.value = new RougeArray((IList<object>)value);
	        }
			else if (value is JObject) 
			{
				RougeObject rougeObject = new RougeObject();
				
				foreach ( KeyValuePair<string, JToken> keyPair in ((JObject)value) ) 
				{
					rougeObject.put(keyPair.Key, keyPair.Value);
				}
				
				this.value = rougeObject;
			}
			else if (value is JArray)
			{
				RougeArray rougeArray = new RougeArray();
				
				foreach (JToken jToken in ((JArray)value))
				{
					rougeArray.add(value);	
				}
				
				this.value = rougeArray;
			}
			else 
			{
				this.value = value;
	        }
        
        }


        public Object getValue()
        {
            return value;
        }

        /*public RougeType getType()
        {
            return type;
        }*/

    }
}
