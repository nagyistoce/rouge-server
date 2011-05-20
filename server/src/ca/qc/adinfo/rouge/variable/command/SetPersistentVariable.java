package ca.qc.adinfo.rouge.variable.command;

import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class SetPersistentVariable extends RougeCommand {
	
	public SetPersistentVariable() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		// TODO Implement SetPersistentVariable 
		
		sendSuccess(session);
	}

}
