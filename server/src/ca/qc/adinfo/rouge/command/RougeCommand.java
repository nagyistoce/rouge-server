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

package ca.qc.adinfo.rouge.command;

import java.sql.Connection;
import java.sql.SQLException;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;


public abstract class RougeCommand {

	private RougeCommandProcessor commandProcessor;
	private String key;
	
	private final static RougeObject successNovaObject = new RougeObject();
	private final static RougeObject failureNovaObject = new RougeObject();
	
	static {
		successNovaObject.putBoolean("ret", true);
		failureNovaObject.putBoolean("ret", false);
	}
	
	public RougeCommand(String key) {
		
		this.key = key; 
	}
	
	public abstract void execute(RougeObject data, SessionContext session, User user);
	
	public Connection getConnection() throws SQLException {
		
		return this.commandProcessor.getDBManager().getConnection();

	}
	
	public void setCommandProcessor(RougeCommandProcessor commandProcessor) {
		
		this.commandProcessor = commandProcessor;
	}
	
	public String getKey() {
		
		return this.key;
	}
	
	public void sendSuccess(SessionContext session) {

		session.send(this.key, successNovaObject);
	}
	
	public void sendFailure(SessionContext session) {
		
		session.send(this.key, failureNovaObject);
	}
	
}
