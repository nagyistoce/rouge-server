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
