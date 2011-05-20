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
import java.util.Hashtable;
import java.util.Properties;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.module.RougeModule;
import ca.qc.adinfo.rouge.user.User;

public class RoomManager extends RougeModule {

	private Hashtable<String, Room> rooms;
	
	public RoomManager() {
		
		this.rooms = new Hashtable<String, Room>();
		
		Properties props = RougeServer.getInstance().getProperties();
		
		String[] roomsToCreates = props.getProperty("room.precreate").split(",");
		
		for(String roomToCreate: roomsToCreates) {
			Room room = new Room(roomToCreate);
			
			synchronized (this.rooms) {
					this.rooms.put(roomToCreate, room);	
			}
		}
	}
	
	public Room getRoom(String name) {
		
		synchronized (this.rooms) {
			return this.rooms.get(name);
		}
	}
	
	public void createRoom(String name, User user) {
		
		Room room = new Room(name, user);
		
		synchronized (this.rooms) {
			if (!this.rooms.contains(name)) {
				this.rooms.put(name, room);	
			}
		}
	}
	
	public void deleteRoom(String name, User user) {
		
		synchronized (this.rooms) {
			Room room = this.rooms.get(name);
			
			if (room != null && room.isAdmin(user)) {
				this.rooms.remove(name);
				
				RougeObject novaObject = new RougeObject();
				novaObject.putString("name", name);
				
				// Send a message to everyone announcing the room was destroyed.
				room.sendToUsersInRool("room.destroy", novaObject, user);
			}			
		}
	}
	
	public boolean roomExists(String name) {
		
		synchronized (this.rooms) {
			return this.rooms.containsKey(name);
		}		
	}
	
	public Collection<Room> getRooms() {
		
		synchronized(this.rooms) {
			return this.rooms.values();
		}
	}

	@Override
	public void tick(long time) {
		
	}
	
}
