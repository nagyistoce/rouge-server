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

import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;
import ca.qc.adinfo.rouge.user.db.UserDb;

public class CreateUser extends RougeCommand {
	
	private static final Logger log = Logger.getLogger(CreateUser.class);
	
	public CreateUser() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User notUsed) {
		
		DBManager dbManager = RougeServer.getInstance().getDbManager();
		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");
		
		String username = data.getString("username");
		String password = data.getString("password");
		String firstname = data.getString("firstname");
		String lastname = data.getString("lastname");
		String email = data.getString("email");
		
		RougeObject payload = new RougeObject();
		
		log.debug("Received create user request for u: " + username);
		
		if (UserDb.isEmailInUse(dbManager, email)) {
			
			log.debug("Duplicate email detected!");
			
			payload.putString("error", "DUPLICATE_EMAIL");
			sendFailure(session, payload);
			return;
		}
		
		if (UserDb.isUsernameInUse(dbManager, username)) {
			
			log.debug("Duplicate username detected!");
			
			payload.putString("error", "DUPLICATE_USERNAME");
			sendFailure(session, payload);
			return;
		}
		
		User user = UserDb.createUser(dbManager, username, password, firstname, lastname, email);
		
		if (user != null) {

			log.debug("User successfully created!");
			
			userManager.registerUser(user);
			session.setUser(user);
			user.setSessionContext(session);
			
			payload.putLong("id", user.getId());
			sendSuccess(session, payload);
			
		} else {
		
			payload.putString("error", "CANT_CREATE");
			sendFailure(session, payload);
		}
		
		
	}
}
