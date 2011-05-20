package ca.qc.adinfo.rouge.variable.command;

import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class GetPersistentVariable extends RougeCommand {
	
	public GetPersistentVariable() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		// TODO Implement GetPersistentVariable 
		
		sendSuccess(session);
	}

}
