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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import ca.qc.adinfo.rouge.bencode.BConstant;
import ca.qc.adinfo.rouge.bencode.BEncoder;

public class RougeObject {

	// Boolean, String, Integer, Long, Float, Double, NovaObject, NovaArray
	
	public HashMap<String, RougeDataWrapper> content;
	
	public int version;
	
	
	public RougeObject() {
		
		this.content = new HashMap<String, RougeDataWrapper>();
	}
	
	public RougeObject(JSONObject jSonObject) {
		
		this.content = new HashMap<String, RougeDataWrapper>();
		
		Iterator<?> iterator = jSonObject.keys();
		
		while(iterator.hasNext()) {
			
			String key = (String)iterator.next();
			
			try {
				this.content.put(key, new RougeDataWrapper(jSonObject.get(key)));
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public RougeObject(Map<String,?> map) {
		
		this.content = new HashMap<String, RougeDataWrapper>();
		
		for(String key: (Set<String>)map.keySet()) {
			
			try {
				this.content.put(key, new RougeDataWrapper(map.get(key)));
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getInt(String key) {
		return (Integer)this.content.get(key).getValue();
	}
	
	public long getLong(String key) {
		return (Long)this.content.get(key).getValue();
	}
	
	public String getString(String key) {
		return (String)this.content.get(key).getValue();
	}
	
	public boolean getBoolean(String key) {
		int b = (Integer)this.content.get(key).getValue();
		return (b == 1);
	}
	
	public float getFloat(String key) {
		return (Float)this.content.get(key).getValue();
	}
	
	public double getDouble(String key) {
		return (Double)this.content.get(key).getValue();
	}
	
	public RougeArray getRougeArray(String key) {
		return (RougeArray)this.content.get(key).getValue();
	}
	
	public RougeObject getNovaObject(String key) {
		return (RougeObject)this.content.get(key).getValue();
	}
	
	public void put(String key, Object value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public void putInt(String key, int value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public void putLong(String key, Long value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public void putString(String key, String value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public void putBoolean(String key, boolean value) {
		if (value) {
			this.content.put(key, new RougeDataWrapper(1));
		} else {
			this.content.put(key, new RougeDataWrapper(0));
		}
	}
	
	public void putFloat(String key, float value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public void putDouble(String key, double value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public void putRougeArray(String key, RougeArray value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public void putRougeObject(String key, RougeObject value) {
		this.content.put(key, new RougeDataWrapper(value));
	}
	
	public JSONObject toJSON() {
		
		JSONObject jObject = new JSONObject();
		
		for(String key: this.content.keySet()) {
			Object obj = this.content.get(key).getValue();
			
			if (obj instanceof RougeObject) {
				jObject.put(key, ((RougeObject)obj).toJSON() );
			} else if (obj instanceof RougeArray) {
				jObject.put(key, ((RougeArray)obj).toJSON() );
			} else {
				jObject.put(key, obj);
			}
		}
		
		return jObject;
	}
	
	public String toBEncode() {
		
		StringBuffer stringBuffer = new StringBuffer();
		 stringBuffer.append(BConstant.DICTIONARY_START);
		 
		 for(String key: content.keySet()) {
			 RougeDataWrapper value = content.get(key);
			 stringBuffer.append(BEncoder.bencodeString(key));
			 stringBuffer.append(BEncoder.bencodeSomething(value.getValue()));
		 }
		 stringBuffer.append(BConstant.DICTIONARY_END);
		 
		 return stringBuffer.toString();
	}
	
	public Map<String, Object> toMap() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(String key: this.content.keySet()) {
			Object obj = this.content.get(key).getValue();
			
			if (obj instanceof RougeObject) {
				map.put(key, ((RougeObject)obj).toMap());
			} else if (obj instanceof RougeArray) {
				map.put(key, ((RougeArray)obj).toList() );
			} else {
				map.put(key, obj);
			}
		}
		
		return map;
	}
	
}
