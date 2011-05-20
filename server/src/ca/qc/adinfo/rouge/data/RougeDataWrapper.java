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

package ca.qc.adinfo.rouge.data;

import java.util.Collection;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RougeDataWrapper {
	
	//private final static Logger log = Logger.getLogger(RougeDataWrapper.class);
	
	private Object value;
	private RougeType type;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RougeDataWrapper(Object value) {
		this.value = value;
		
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
	}
	

	public Object getValue() {
		return value;
	}

	public RougeType getType() {
		return type;
	}
	
	
	
}
