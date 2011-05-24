package ca.qc.adinfo.rouge.leaderboard.command;

import java.util.HashMap;

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

public class GetLeaderboards extends RougeCommand {

	public GetLeaderboards() {
		super();

	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();

		HashMap<String, Leaderboard> leaderboards = LeaderboardDb.getLeaderboards(dbManager);

		if (leaderboards == null) {

			sendFailure(session);

		} else {	
			
			RougeArray rougeLeaderboards = new RougeArray();
			
			for(Leaderboard leaderboard: leaderboards.values()) {

				RougeArray scores = new RougeArray();

				for (Score score: leaderboard.getScore()) {
					RougeObject rougeScore = new RougeObject();
					rougeScore.putLong("user", score.getUser());
					rougeScore.putLong("score", score.getScore());
					scores.addRougeObject(rougeScore);
				}
				
				RougeObject rougeLeaderboard = new RougeObject();
				rougeLeaderboard.put("scores", scores);
				rougeLeaderboard.put("key", leaderboard.getKey());
				
				rougeLeaderboards.addRougeObject(rougeLeaderboard);
			}
			
			RougeObject payload = new RougeObject();
			payload.putRougeArray("leaderboard", rougeLeaderboards);

			sendSuccess(session, payload);
		}
	}

}
