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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import ca.qc.adinfo.rouge.server.core.bencode.BConstant;
import ca.qc.adinfo.rouge.server.core.bencode.BEncoder;

public class RougeArray {

	public LinkedList<RougeDataWrapper> content;
	
	public RougeArray() {
		
		this.content = new LinkedList<RougeDataWrapper>();
	}
	
	public RougeArray(JSONArray jArray) {
		
		this.content = new LinkedList<RougeDataWrapper>();
		
		for (int i = 0; i < jArray.size(); i++) {
			this.content.add(new RougeDataWrapper(jArray.get(i)));
		}
	}
	
	public RougeArray(Collection<Object> col) {
		
		this.content = new LinkedList<RougeDataWrapper>();
		
		for(Object o: col) {
			
			this.content.add(new RougeDataWrapper(o));
		}
	}
	
	public int getInt(int index) {
		return (Integer)this.content.get(index).getValue();
	}
	
	public long getLong(int index) {
		return (Long)this.content.get(index).getValue();
	}
	
	public String getString(int index) {
		return (String)this.content.get(index).getValue();
	}
	
	public boolean getBoolean(int index) {
		int b = (Integer)this.content.get(index).getValue();
		return (b == 1);
	}
	
	public float getFloat(int index) {
		return (Float)this.content.get(index).getValue();
	}
	
	public double getDouble(int index) {
		return (Double)this.content.get(index).getValue();
	}
	
	public RougeArray getRougeArray(int index) {
		return (RougeArray)this.content.get(index).getValue();
	}
	
	public RougeObject getRougeObject(int index) {
		return (RougeObject)this.content.get(index).getValue();
	}
	
	public void add(Object value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public void addInt(int value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public void addLong(Long value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public void addString(String value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public void addBoolean(boolean value) {
		if (value) {
			this.content.add(new RougeDataWrapper(1));
		} else {
			this.content.add(new RougeDataWrapper(0));
		}
		
	}
	
	public void addFloat(float value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public void addDouble(double value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public void addRougeArray(RougeArray value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public void addRougeObject(RougeObject value) {
		this.content.add(new RougeDataWrapper(value));
	}
	
	public JSONArray toJSON() {
		
		JSONArray jArray = new JSONArray();
		
		for(RougeDataWrapper data: this.content) {

			Object obj = data.getValue();
			
			if (obj instanceof RougeObject) {
				jArray.add( ((RougeObject)obj).toJSON() );
			} else if (obj instanceof RougeArray) {
				jArray.add( ((RougeArray)obj).toJSON() );
			} else {
				jArray.add(obj);
			}
		}
		
		return jArray;
	}
	
	public String toBEncode() {
		
		StringBuffer stringBuffer = new StringBuffer();
		 stringBuffer.append(BConstant.LIST_START);
		 
		 for(RougeDataWrapper value: content) {
			 stringBuffer.append(BEncoder.bencodeSomething(value.getValue()));
		 }
		 stringBuffer.append(BConstant.LIST_END);
		 
		 return stringBuffer.toString();
	}
	
	public List<Object> toList() {
	
		List<Object> list = new ArrayList<Object>();
		
		for(RougeDataWrapper data: this.content) {

			Object obj = data.getValue();
			
			if (obj instanceof RougeObject) {
				list.add( ((RougeObject)obj).toMap() );
			} else if (obj instanceof RougeArray) {
				list.add( ((RougeArray)obj).toList() );
			} else {
				list.add(obj);
			}
		}
		
		return list;
		
	}
	
}
