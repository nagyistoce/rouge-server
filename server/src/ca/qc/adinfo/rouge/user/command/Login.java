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
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.InvalidLoginException;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;

public class Login extends RougeCommand {
	
	private static final Logger log = Logger.getLogger(Login.class);

	private static long count = 0;
	
	public Login() {
		super("login");
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User notUsed) {
		
		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");
		
		String username = data.getString("username");
		String password = data.getString("password");
		
		log.debug("Received login request for u: " + username + " p: " + password );
		
		if (password.equals("password")) {
		
			log.debug("Login for " + username + " is good.");
			
			User user = new User(count++, username);
		
			userManager.registerUser(user);
			session.setUser(user);
			user.setSessionContext(session);
			
			RougeObject resp =  new RougeObject();
			resp.putBoolean("result", true);
			
			sendSuccess(session);
			
		} else {
			
			log.debug("Login for " + username + " is bad.");
			
			sendFailure(session);
			
			if (session.hasAttachment("tries")) {
				
				int tries = (Integer)session.getAttachment("tries");
				
				if (tries > 3) {
					throw new InvalidLoginException();
				} else {
					session.setAttachment("tries", tries+1);
				}
				
			} else {
				
				session.setAttachment("tries", 1);
			}
		}
	}
}