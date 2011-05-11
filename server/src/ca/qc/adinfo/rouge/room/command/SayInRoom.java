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

package ca.qc.adinfo.rouge.room.command;

import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.room.Room;
import ca.qc.adinfo.rouge.room.RoomManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class SayInRoom extends RougeCommand {
	
	private final static Logger log = Logger.getLogger(SayInRoom.class);

	public SayInRoom() {
		super("room.say");
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		RoomManager roomManager = (RoomManager)RougeServer.getInstance().getModule("room.manager");
		String roomName = data.getString("name");
		
		Room room = roomManager.getRoom(roomName);
	
		if (room != null) {
			room.sendToUsersInRool("room.recv", data, user);
			sendSuccess(session);
		} else {
			sendFailure(session);
		}

	}

}
