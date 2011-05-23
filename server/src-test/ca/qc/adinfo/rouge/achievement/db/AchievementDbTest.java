package ca.qc.adinfo.rouge.achievement.db;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.db.DbTest;
import ca.qc.adinfo.rouge.variable.Variable;
import ca.qc.adinfo.rouge.variable.db.PersistentVariableDb;

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


		boolean ret = AchievementDb.createAchievement(dbManager, 
				"UNITTEST", "Achievement for unit test", 20, 100);
		
		Assert.assertTrue(ret);
		
		ret = AchievementDb.updateAchievement(dbManager, 
				"UNITTEST", -999, 10);
		
		Assert.assertTrue(ret);
		
	}

}
