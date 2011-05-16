using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Rouge
{
    public class RougeDataWrapper
    {
        private Object value;
        //private RougeType type;

        public RougeDataWrapper(Object value)
        {

            this.value = value;

        /*
        if (value instanceof Map) {
            this.value = new RougeObject((Map) value);
            this.type = RougeType.NovaObject;
        } else if (value instanceof Collection) {
            this.value = new RougeArray((Collection)value);
            this.type = RougeType.NovaArray;
        } if (value instanceof JSONObject) {
            this.value = new RougeObject((JSONObject) value);
            this.type = RougeType.NovaObject;
        } else if (value instanceof JSONArray) {
            this.value = new RougeArray((JSONArray)value);
            this.type = RougeType.NovaArray;
        } else if (value instanceof RougeObject) {
            this.value = value;
            this.type = RougeType.NovaObject;
        } else if (value instanceof RougeArray) {
            this.value = value;
            this.type = RougeType.NovaArray;
        } else if (value instanceof Integer) {
            this.value = value;
            this.type = RougeType.Integer;
        } else if (value instanceof Long) {
            this.value = value;
            this.type = RougeType.Long;
        } else if (value instanceof String) {
            this.value = value;
            this.type = RougeType.String;
        } else if (value instanceof Float) {
            this.value = value;
            this.type = RougeType.Float;
        } else if (value instanceof Double) {
            this.value = value;
            this.type = RougeType.Double;
        } else if (value instanceof Boolean) {
            this.value = value;
            this.type = RougeType.Boolean;
        } else {
            //throw new NovaUnsupportedType()
        }
        */
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
