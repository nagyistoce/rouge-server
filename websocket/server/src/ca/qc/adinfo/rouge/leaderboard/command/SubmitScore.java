package ca.qc.adinfo.rouge.leaderboard.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.leaderboard.db.LeaderboardDb;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class SubmitScore extends RougeCommand {

	public SubmitScore() {
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {
		
		DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		String key = data.getString("key");
		long score = data.getLong("score");
		
		boolean ret = LeaderboardDb.submitScore(dbManager, key, user.getId(), score);
		
		if (ret) {
			RougeObject payload = new RougeObject();
			payload.put("key", key);
			sendSuccess(session, payload);
		} else {
			sendFailure(session);
		}
		
	}

}
