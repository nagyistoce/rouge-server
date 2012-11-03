package ca.qc.adinfo.rouge.variable.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.variable.Variable;
import ca.qc.adinfo.rouge.variable.db.PersistentVariableDb;

public class GetPersistentVariable extends RougeCommand {
	
	public GetPersistentVariable() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();
		String key = data.getString("key");
		
		Variable var = PersistentVariableDb.getPersitentVariable(dbManager, key);
		
		RougeObject payload = new RougeObject();
		
		payload.putString("key", key);
		payload.putRougeObject("value", var.getValue());
		payload.putLong("version", var.getVersion());
		
		sendSuccess(session, payload);
	}

}
