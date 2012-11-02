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

package ca.qc.adinfo.rouge.variable;

import java.util.ArrayList;
import java.util.Collection;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.user.User;

public class Variable {
	
	private String key;
	private RougeObject value;
	private long version;
	
	private Collection<User> subscribers;
	
	public Variable(String key) {
		
		this.key = key;
		this.subscribers = new ArrayList<User>();
		this.version = 0;
	}
	
	public Variable(String key, RougeObject value, long version) {
		
		this.key = key;
		this.subscribers = new ArrayList<User>();
		this.version = version;
		this.value = value;
	}
	
	public void subscribe(User user) {
		
		synchronized (this.subscribers) {
			this.subscribers.add(user);
		}
	}
	
	public void unsubscribe(User user) {
		
		synchronized (this.subscribers) {
			this.subscribers.remove(user);
		}
	}
	
	public void setValue(RougeObject value) {
		
		this.value = value;
		
		synchronized (this.subscribers) {
			RougeObject rougeObject = new RougeObject();
			rougeObject.putString("key", this.key);
			rougeObject.putRougeObject(key, value);
			
			for(User user: this.subscribers) {
				user.getSessionContext().send("var.up", rougeObject);
			}
		}
	}
	
	public RougeObject getValue() {
		
		return this.value;
	}
	
	public long getVersion() {
		
		return this.version;
	}
	
	public void setVersion(long value) {
		
		this.version = value;
	}
	
	public String getKey() {
		
		return this.key;
	}

}
