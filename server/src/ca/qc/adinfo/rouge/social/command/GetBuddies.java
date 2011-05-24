package ca.qc.adinfo.rouge.social.command;

import java.util.Collection;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeArray;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.social.db.SocialDb;
import ca.qc.adinfo.rouge.user.User;

public class GetBuddies extends RougeCommand {

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();

		RougeArray rougeArray = new RougeArray();

		Collection<Long> friends = SocialDb.getFriends(dbManager, user.getId());

		if (friends == null) {
			
			sendFailure(session);
			
		} else {

			for(Long friendId: friends) {
				rougeArray.addLong(friendId);
			}

			RougeObject payload = new RougeObject();
			payload.putRougeArray("friends", rougeArray);
			sendSuccess(session, payload);
		}
	}

}
