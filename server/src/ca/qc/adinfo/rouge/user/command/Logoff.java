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

package ca.qc.adinfo.rouge.user.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.room.Room;
import ca.qc.adinfo.rouge.room.RoomManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;

public class Logoff extends RougeCommand {
	
	//private static final Logger log = Logger.getLogger(Logoff.class);
	
	public Logoff() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {
		
		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");
		
		userManager.unregisterUser(user);
		
		RoomManager roomManager = (RoomManager)RougeServer.getInstance().getModule("room.manager");
		
		for(Room room: roomManager.getRooms()) {
			room.removeFromRoom(user);
		}
	}
}
