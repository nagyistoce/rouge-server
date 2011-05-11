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

package ca.qc.adinfo.rouge.user;

import java.util.Collection;
import java.util.HashMap;

public class UserManager {
	
	private HashMap<String, User> users;
	
	public UserManager() {
		
		this.users = new HashMap<String, User>();
	}
	
	public void registerUser(User user) {
		
		synchronized (this.users) {
			this.users.put(user.getUsername(), user);
		}
	}
	
	public void unregisterUser(User user) {
		
		synchronized (this.users) {
			this.users.remove(user.getUsername());
		}
	}
	
	public User getUser(String username) {
		
		synchronized (this.users) {
			return this.users.get(username);
		}
	}
	
	public Collection<User> getUsers() {
		
		synchronized (users) {
			return this.users.values();
		}
	}

}
