package ca.qc.adinfo.rouge.variable.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.variable.Variable;
import ca.qc.adinfo.rouge.variable.db.PersistentVariableDb;

public class SetPersistentVariable extends RougeCommand {
	
	public SetPersistentVariable() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		RougeObject value = data.getNovaObject("value");
		String key = data.getString("key");
		long version = data.getLong("version");
		
		Variable variable = new Variable(key, value, version);
		
		boolean ret = PersistentVariableDb.updatePersitentVariable(
				dbManager, user, variable);
		
		if (ret) {
			sendSuccess(session);
		} else {
			sendFailure(session);
		}
	}

}
