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

package ca.qc.adinfo.rouge.variable.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.variable.Variable;
import ca.qc.adinfo.rouge.variable.VariableManager;

public class SubscribeVariable extends RougeCommand {
	
	public SubscribeVariable() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		VariableManager variableManager = (VariableManager)RougeServer.getInstance().getModule("variable.manager");
		
		String key = data.getString("key");
		
		if(variableManager.isVariableExist(key)) {
			
			Variable var = variableManager.getVariable(key);
			var.subscribe(user);
			
			sendSuccess(session);
		} else {
			sendFailure(session);
		}
	}

}
