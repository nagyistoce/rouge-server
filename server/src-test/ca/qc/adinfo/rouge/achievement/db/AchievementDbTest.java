package ca.qc.adinfo.rouge.achievement.db;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.qc.adinfo.rouge.achievement.Achievement;
import ca.qc.adinfo.rouge.db.DbTest;

public class AchievementDbTest extends DbTest {
	
	@Before public void setup() throws Exception {
		super.setup();
	}
	
	@After public void cleanup() throws Exception {
		
		quickUpdate("DELETE FROM rouge_achievement_progress WHERE `achievement_key` = 'UNITTEST';");
		quickUpdate("DELETE FROM rouge_achievements WHERE `key` = 'UNITTEST';");
		dbManager.disconnect();
	}
	
	@Test public void testAchievementDbTest() {

		final long USERID = -999;

		boolean ret = AchievementDb.createAchievement(dbManager, 
				"UNITTEST", "Achievement for unit test", 20, 100);
		
		Assert.assertTrue(ret);
		
		ret = AchievementDb.updateAchievement(dbManager, 
				"UNITTEST", USERID, 10);
		
		Assert.assertTrue(ret);
		
		HashMap<String, Achievement> achievements = AchievementDb.getAchievements(dbManager, USERID);
		
		Assert.assertNotNull(achievements);
		
		Achievement testAchievement = achievements.get("UNITTEST");
		
		Assert.assertEquals(10.0, testAchievement.getProgress());
		Assert.assertEquals(20, testAchievement.getPointValue());
		Assert.assertEquals(100.0, testAchievement.getTotal());
		

		ret = AchievementDb.updateAchievement(dbManager, 
				"UNITTEST", USERID, 20);
		
		Assert.assertTrue(ret);
		
		achievements = AchievementDb.getAchievements(dbManager, USERID);
		
		Assert.assertNotNull(achievements);
		
		testAchievement = achievements.get("UNITTEST");
		
		Assert.assertEquals(20.0, testAchievement.getProgress());
	}

}
