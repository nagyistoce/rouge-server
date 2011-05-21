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
        private Dictionary<String, RougeDataWrapper> content;

        public RougeObject()
        {
            this.content = new Dictionary<String, RougeDataWrapper>();
        }

        public RougeObject(String jSon) {
			
            this.content = new Dictionary<String, RougeDataWrapper>();

			IDictionary<object, object> o = JsonConvert.DeserializeObject<IDictionary<object, object>>(jSon, new JsonSerializerSettings{
					TypeNameHandling = TypeNameHandling.All,
					TypeNameAssemblyFormat = System.Runtime.Serialization.Formatters.FormatterAssemblyStyle.Simple});
			
            foreach (KeyValuePair<object, object> keyValue in o)
            {
                this.content.Add((string)keyValue.Key, new RougeDataWrapper(keyValue.Value));
            }
        }
		
		public RougeObject(IDictionary<object, object> dict) {
			
            foreach (KeyValuePair<object, object> keyValue in dict)
            {
                this.content.Add((String)keyValue.Key, new RougeDataWrapper(keyValue.Value));
            }
        }

        public int getInt(String key)
        {
            return (int)this.content[key].getValue();
        }

        public long getLong(String key)
        {
            return (long)this.content[key].getValue();
        }

        public String getString(String key)
        {
            return (String)this.content[key].getValue();
        }

        public bool getBoolean(String key)
        {
            int b = (int)this.content[key].getValue();
            return (b == 1);
        }

        public float getFloat(String key)
        {
            return (float)this.content[key].getValue();
        }

        public double getDouble(String key)
        {
            return (double)this.content[key].getValue();
        }

        public RougeArray getRougeArray(String key)
        {
            return (RougeArray)this.content[key].getValue();
        }

        public RougeObject getRougeObject(String key)
        {
            return (RougeObject)this.content[key].getValue();
        }

        public void put(String key, Object value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putInt(String key, int value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putLong(String key, long value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putString(String key, String value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putBoolean(String key, bool value)
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

        public void putFloat(String key, float value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putDouble(String key, double value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putRougeArray(String key, RougeArray value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public void putRougeObject(String key, RougeObject value)
        {
            this.content.Add(key, new RougeDataWrapper(value));
        }

        public Dictionary<String, Object> toDictionary()
        {

            Dictionary<String, Object> toJson = new Dictionary<String, Object>();

            foreach(KeyValuePair<String, RougeDataWrapper> keyValue in this.content) {
				
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
		
		public String toJson() {
			
			return JsonConvert.SerializeObject(this.toDictionary());
		}

    }
}
