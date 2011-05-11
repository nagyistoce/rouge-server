package ca.qc.adinfo.rouge.variable.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.variable.VariableManager;

public class GetVariable extends RougeCommand {
	
	public GetVariable() {
		super("var.get");
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		VariableManager variableManager = (VariableManager)RougeServer.getInstance().getModule("variable.manager");
		
		
		sendSuccess(session);
	}

}
