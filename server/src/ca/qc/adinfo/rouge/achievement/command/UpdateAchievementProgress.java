package ca.qc.adinfo.rouge.achievement.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.achievement.db.AchievementDb;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class UpdateAchievementProgress extends RougeCommand {

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		String key = data.getString("key");
		Double progress = data.getDouble("progress");
		
		boolean ret = AchievementDb.updateAchievement(
				dbManager, key, user.getId(), progress);
		
		if (ret) {
			
			RougeObject payload = new RougeObject();
			payload.putString("key", key);
			sendSuccess(session, payload);
			
		} else {
			sendFailure(session);
		}
	}

}
