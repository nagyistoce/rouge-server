package ca.qc.adinfo.rouge.leaderboard.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeArray;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.leaderboard.Leaderboard;
import ca.qc.adinfo.rouge.leaderboard.Score;
import ca.qc.adinfo.rouge.leaderboard.db.LeaderboardDb;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class GetLeaderboard extends RougeCommand {

	public GetLeaderboard() {
		super();

	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();

		String key = data.getString("key");

		Leaderboard leaderboard = LeaderboardDb.getLeaderboard(dbManager, key);

		if (leaderboard == null) {

			sendFailure(session);

		} else {	

			RougeArray scores = new RougeArray();

			for (Score score: leaderboard.getScore()) {
				RougeObject rougeScore = new RougeObject();
				rougeScore.putLong("user", score.getUser());
				rougeScore.putLong("score", score.getScore());
				scores.addRougeObject(rougeScore);
			}

			RougeObject payload = new RougeObject();
			payload.put("scores", scores);
			payload.put("key", key);

			sendSuccess(session, payload);
		}
	}

}
