package ca.qc.adinfo.rouge.mail.command;

import java.util.Collection;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeArray;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.mail.Mail;
import ca.qc.adinfo.rouge.mail.db.MailDb;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class GetUnreadlMails extends RougeCommand {

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		Collection<Mail> mails = MailDb.getMails(dbManager, user.getId(), true);
		
		if (mails == null) {
			sendFailure(session);
		} else {
			
			RougeArray rougeMails = new RougeArray();
			
			for(Mail mail: mails) {
				RougeObject rougeMail = new RougeObject();
				rougeMail.putLong("id", mail.getId());
				rougeMail.putLong("to", mail.getToId());
				rougeMail.putLong("from", mail.getFromId());
				rougeMail.putRougeObject("content", mail.getContent());
				
				rougeMails.addRougeObject(rougeMail);
			}
			
			RougeObject payload = new RougeObject();
			payload.putRougeArray("mails", rougeMails);
			
			sendSuccess(session, payload);
		}
		
	}

}
