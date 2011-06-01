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

namespace Rouge
{
    public class RougeArray
    {
        public List<RougeDataWrapper> content;

        public RougeArray()
        {

            this.content = new List<RougeDataWrapper>();
        }
		
		public RougeArray(IList<object> list) {
			
            foreach (object value in list)
            {
                this.content.Add(new RougeDataWrapper(value));
            }
        }

        public int getInt(int index)
        {
            return (int)this.content[index].getValue();
        }

        public long getLong(int index)
        {
            return (long)this.content[index].getValue();
        }

        public string getString(int index)
        {
            return (string)this.content[index].getValue();
        }

        public bool getBoolean(int index)
        {
            int b = (int)this.content[index].getValue();
            return (b == 1);
        }

        public float getFloat(int index)
        {
            return (float)this.content[index].getValue();
        }

        public double getDouble(int index)
        {
            return (double)this.content[index].getValue();
        }

        public RougeArray getRougeArray(int index)
        {
            return (RougeArray)this.content[index].getValue();
        }

        public RougeObject getRougeObject(int index)
        {
            return (RougeObject)this.content[index].getValue();
        }

        public void add(Object value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }

        public void addInt(int value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }

        public void addLong(long value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }

        public void addString(string value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }

        public void addBoolean(bool value)
        {
            if (value)
            {
                this.content.Add(new RougeDataWrapper(1));
            }
            else
            {
                this.content.Add(new RougeDataWrapper(0));
            }

        }

        public void addFloat(float value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }

        public void addDouble(double value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }

        public void addRougeArray(RougeArray value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }

        public void addRougeObject(RougeObject value)
        {
            this.content.Add(new RougeDataWrapper(value));
        }
		
		public List<Object> toList()
        {

            List<Object> toJson = new List<Object>();

            foreach(RougeDataWrapper wrapper in this.content) {
				
				Object value = wrapper.getValue();
				
				if (value is RougeObject) {
					toJson.Add(((RougeObject)value).toDictionary());
				} else if (value is RougeArray) {
					toJson.Add(((RougeArray)value).toList());
				} else {
					toJson.Add(value);
				}
				
                
            }
			
			return toJson;
			
            //return JsonConvert.SerializeObject(toJson);
        }
    }
}
