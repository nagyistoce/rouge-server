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

		final String ACHIEVEMENT_NAME = "UNITTEST";

		boolean ret = AchievementDb.createAchievement(dbManager, 
				ACHIEVEMENT_NAME, "Achievement for unit test", 20, 100);
		
		Assert.assertTrue(ret);
		
		// Check if the achievement exists
		
		HashMap<String, Achievement> achievements = AchievementDb.getAchievementList(dbManager);
		
		Assert.assertNotNull(achievements);
		Assert.assertTrue(achievements.containsKey("UNITTEST"));		
		
		ret = AchievementDb.updateAchievement(dbManager, 
				ACHIEVEMENT_NAME, this.user.getId(), 10);
		
		Assert.assertTrue(ret);
		
		// Test the first insert
		
		achievements = AchievementDb.getAchievements(dbManager, this.user.getId());
		
		Assert.assertNotNull(achievements);
		
		Achievement testAchievement = achievements.get("UNITTEST");
		
		Assert.assertEquals(10.0, testAchievement.getProgress());
		Assert.assertEquals(20, testAchievement.getPointValue());
		Assert.assertEquals(100.0, testAchievement.getTotal());
		
		// Test the update
		
		ret = AchievementDb.updateAchievement(dbManager, 
				ACHIEVEMENT_NAME, this.user.getId(), 20);
		
		Assert.assertTrue(ret);
		
		achievements = AchievementDb.getAchievements(dbManager, this.user.getId());
		
		Assert.assertNotNull(achievements);
		Assert.assertTrue(achievements.containsKey(ACHIEVEMENT_NAME));
		
		testAchievement = achievements.get(ACHIEVEMENT_NAME);
		
		Assert.assertEquals(20.0, testAchievement.getProgress());
		
		// Updating with a lower value should not update the progress
		
		ret = AchievementDb.updateAchievement(dbManager, 
				ACHIEVEMENT_NAME, this.user.getId(), 15);
		
		Assert.assertTrue(ret);
		
		achievements = AchievementDb.getAchievements(dbManager, this.user.getId());
		
		Assert.assertNotNull(achievements);
		
		testAchievement = achievements.get(ACHIEVEMENT_NAME);
		
		Assert.assertEquals(20.0, testAchievement.getProgress());		
	}

}
