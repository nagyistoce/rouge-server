package ca.qc.adinfo.rouge.achievement.command;

import java.util.HashMap;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.achievement.Achievement;
import ca.qc.adinfo.rouge.achievement.db.AchievementDb;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeArray;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class GetAchievements extends RougeCommand {

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		HashMap<String, Achievement> achievements = 
			AchievementDb.getAchievements(dbManager, user.getId());
		
		if (achievements == null) {
			
			sendFailure(session);
		
		} else {
			
			RougeArray rougeAchievements = new RougeArray();
			
			for(Achievement achievement: achievements.values()) {
				
				RougeObject rougeAchievement = new RougeObject();
				rougeAchievement.putString("key", achievement.getKey());
				rougeAchievement.putInt("point", achievement.getPointValue());
				rougeAchievement.putDouble("progress", achievement.getProgress());
				rougeAchievement.putDouble("total", achievement.getTotal());
				
				rougeAchievements.addRougeObject(rougeAchievement);
			}
	
			RougeObject payload = new RougeObject();
			payload.putRougeArray("ach", rougeAchievements);
			
			sendSuccess(session, payload);
		}
	}

}
