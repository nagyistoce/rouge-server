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
	
	private final static RougeObject successRougeObject = new RougeObject();
	private final static RougeObject failureRougeObject = new RougeObject();
	
	static {
		successRougeObject.putBoolean("ret", true);
		failureRougeObject.putBoolean("ret", false);
	}
	
	public RougeCommand() {

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
	
	public void setKey(String key) {
		
		this.key = key;
	}
	
	public void sendSuccess(SessionContext session) {

		this.sendSuccess(session, new RougeObject());
	}
	
	public void sendSuccess(SessionContext session, RougeObject payload) {
		
		payload.putBoolean("ret", true);
		session.send(this.key, payload);
	}
		
	public void sendFailure(SessionContext session) {
		
		this.sendFailure(session, new RougeObject());
	}
	
	public void sendFailure(SessionContext session, RougeObject payload) {
		
		payload.putBoolean("ret", false);
		
		session.send(this.key, payload);
	}
	
}
