package ca.qc.adinfo.rouge.mail.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.mail.db.MailDb;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class SendMail extends RougeCommand {

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		long toId = data.getLong("to");
		RougeObject content = data.getRougeObject("content");
		
		boolean ret = MailDb.sendMail(dbManager, user.getId(), toId, content);
		
		if (ret) {
			sendSuccess(session);
		} else {
			sendFailure(session);
		}
	}

}
