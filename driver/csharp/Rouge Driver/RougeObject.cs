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
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Rouge
{
    public class RougeObject
    {
        private Dictionary<string, RougeDataWrapper> content;

        public RougeObject()
        {
            this.content = new Dictionary<string, RougeDataWrapper>();
        }

        public RougeObject(string jSon) {
			
            this.content = new Dictionary<string, RougeDataWrapper>();

			IDictionary<object, object> o = JsonConvert.DeserializeObject<IDictionary<object, object>>(jSon, new JsonSerializerSettings{
					TypeNameHandling = TypeNameHandling.All,
					TypeNameAssemblyFormat = System.Runtime.Serialization.Formatters.FormatterAssemblyStyle.Simple});
			
            foreach (KeyValuePair<object, object> keyValue in o)
            {
                this.content.Add((string)keyValue.Key, new RougeDataWrapper(keyValue.Value));
            }
        }
		
		public RougeObject(IDictionary<object, object> dict) {
			
			this.content = new Dictionary<string, RougeDataWrapper>();
			
            foreach (KeyValuePair<object, object> keyValue in dict)
            {
                this.content.Add((string)keyValue.Key, new RougeDataWrapper(keyValue.Value));
            }
        }
		
		public RougeObject(IDictionary<string, object> dict) {
			
			 this.content = new Dictionary<string, RougeDataWrapper>();
			
            foreach (KeyValuePair<string, object> keyValue in dict)
            {
				this.content.Add(keyValue.Key, new RougeDataWrapper(keyValue.Value));
            }
        }

        public int getInt(string key)
        {
            return (int)this.content[key].getValue();
        }

        public long getLong(string key)
        {
            return (long)this.content[key].getValue();
        }

        public string getString(string key)
        {
            return (string)this.content[key].getValue();
        }

        public bool getBoolean(string key)
        {
            int b = (int)this.content[key].getValue();
            return (b == 1);
        }

        public float getFloat(string key)
        {
            return (float)this.content[key].getValue();
        }

        public double getDouble(string key)
        {
            return (double)this.content[key].getValue();
        }

        public RougeArray getRougeArray(string key)
        {
            return (RougeArray)this.content[key].getValue();
        }

        public RougeObject getRougeObject(string key)
        {
			return (RougeObject)this.content[key].getValue();
        }

        public void put(string key, Object value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putInt(string key, int value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putLong(string key, long value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putString(string key, string value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putBoolean(string key, bool value)
        {
            if (value)
            {
                this.content.Add(key, new RougeDataWrapper(1));
            }
            else
            {
                this.content.Add(key, new RougeDataWrapper(0));
            }
        }

        public void putFloat(string key, float value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putDouble(string key, double value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putRougeArray(string key, RougeArray value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putRougeObject(string key, RougeObject value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public Dictionary<string, Object> toDictionary()
        {

            Dictionary<string, Object> toJson = new Dictionary<string, Object>();

            foreach(KeyValuePair<string, RougeDataWrapper> keyValue in this.content) {
				
				Object value = keyValue.Value.getValue();
				
				if (value is RougeObject) {
					toJson.Add(keyValue.Key, ((RougeObject)value).toDictionary());
				} else if (value is RougeArray) {
					toJson.Add(keyValue.Key, ((RougeArray)value).toList());
				} else {
					toJson.Add(keyValue.Key, keyValue.Value.getValue());
				}
            }
			
			return toJson;
			
        }
		
		public string toJson() {
			
			return JsonConvert.SerializeObject(this.toDictionary());
		}

    }
}
