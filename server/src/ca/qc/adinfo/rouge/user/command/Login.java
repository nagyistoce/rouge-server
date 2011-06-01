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
import ca.qc.adinfo.rouge.user.InvalidLoginException;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;
import ca.qc.adinfo.rouge.user.db.UserDb;

public class Login extends RougeCommand {
	
	private static final Logger log = Logger.getLogger(Login.class);
	
	public Login() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User notUsed) {
		
		DBManager dbManager = RougeServer.getInstance().getDbManager();
		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");
		
		if (!data.hasKey("username") || !data.hasKey("password")) {
			log.error("Missing parameters");
			
			// Fail silent, don't bother giving an error
			return;
		}
		
		String username = data.getString("username");
		String password = data.getString("password");
		
		log.debug("Received login request for u: " + username);
		
		User user = UserDb.getUser(dbManager, username);
		
		if (user == null) {
			sendFailure(session);
			throw new InvalidLoginException();
		}
		
		if (password.equals(user.getPasswordHash())) {
		
			log.debug("Login for " + username + " is good.");
			
			userManager.registerUser(user);
			session.setUser(user);
			user.setSessionContext(session);
		
			sendSuccess(session);
			
		} else {
		
			sendFailure(session);
			throw new InvalidLoginException();	
		}
	}
}
