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

package ca.qc.adinfo.rouge.room;

import java.util.Collection;
import java.util.HashMap;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class Room {
	
	private String name;
	
	private HashMap<Long, User> usersInRoom;
	private HashMap<Long, User> usersAdmin;
	
	protected Room(String name) {
		
		this.name = name;
		
		this.usersInRoom = new HashMap<Long, User>();
		this.usersAdmin = new HashMap<Long, User>();	
	}
	
	protected Room(String name, User creator) {
		
		this(name);
		
		synchronized (this.usersAdmin) {
			this.usersAdmin.put(creator.getId(), creator);
		}
		
		synchronized (this.usersInRoom) {
			this.usersInRoom.put(creator.getId(), creator);
		}
	}
	
	public void addToRoom(User user) {
		
		synchronized(this.usersInRoom) {
			if (!this.usersInRoom.containsKey(user.getId())) {
				this.usersInRoom.put(user.getId(), user);
			}
		}
	}
	
	public void removeFromRoom(User user) {
		
		synchronized(this.usersInRoom) {
			if (this.usersInRoom.containsKey(user.getId())) {
				this.usersInRoom.remove(user.getId());
			}
		}
	}
	
	public void sendToUsersInRool (String command, RougeObject payload, User sender){
		
		synchronized(this.usersInRoom) {
			for(User user: this.usersInRoom.values()) {
				if (user.getId() != sender.getId()) {
					user.getSessionContext().send(command, payload);
				}
			}
		}		
	}
	
	public void promoteToAdmin(User user) {
		
		synchronized (this.usersAdmin) {
			this.usersAdmin.put(user.getId(), user);
			this.usersInRoom.put(user.getId(), user);
		}
	}
	
	public boolean isAdmin(User user) {
		
		synchronized (this.usersAdmin) {
			return this.usersInRoom.containsKey(user.getId());
		}
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public Collection<User> getPeopleInRoom() {
		
		synchronized(this.usersInRoom) {
			return this.usersInRoom.values();
		}
	}

}
