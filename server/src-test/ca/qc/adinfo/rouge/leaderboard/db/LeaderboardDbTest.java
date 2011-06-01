package ca.qc.adinfo.rouge.leaderboard.db;

import java.util.Collection;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.qc.adinfo.rouge.db.DbTest;
import ca.qc.adinfo.rouge.leaderboard.Leaderboard;
import ca.qc.adinfo.rouge.leaderboard.Score;

public class LeaderboardDbTest extends DbTest {
	
	@Before public void setup() throws Exception {
		super.setup();
	}
	
	@After public void cleanup() throws Exception {
		
		quickUpdate("DELETE FROM rouge_leaderboard_score WHERE `leaderboard_key` = 'UNITTEST';");
		quickUpdate("DELETE FROM rouge_leaderboards WHERE `key` = 'UNITTEST';");
		dbManager.disconnect();
	}
	
	@Test public void testAchievementDbTest() {

		final String LEADERBOARD_NAME = "UNITTEST";

		boolean ret = LeaderboardDb.createLeaderboard(dbManager, LEADERBOARD_NAME, "Leaderboard for test");
		
		Assert.assertTrue(ret);

		// Test the first insert
		
		ret = LeaderboardDb.submitScore(dbManager, LEADERBOARD_NAME, this.user.getId(), 10000L);
		
		Assert.assertTrue(ret);
		
		HashMap<String, Leaderboard> leaderboards = LeaderboardDb.getLeaderboards(dbManager);
		
		Assert.assertNotNull(leaderboards);
		Assert.assertTrue(leaderboards.containsKey(LEADERBOARD_NAME));
		
		Leaderboard testLeaderboard = LeaderboardDb.getLeaderboard(dbManager, LEADERBOARD_NAME);
		
		Collection<Score> scores = testLeaderboard.getScore();
		Score score = scores.iterator().next();
		
		Assert.assertEquals(10000L, score.getScore());
		Assert.assertEquals(this.user.getId(), score.getUser());		
		
		// Test the update
		
		ret = LeaderboardDb.submitScore(dbManager, LEADERBOARD_NAME, this.user.getId(), 20000L);
		
		Assert.assertTrue(ret);
		
		testLeaderboard = LeaderboardDb.getLeaderboard(dbManager, LEADERBOARD_NAME);
		
		scores = testLeaderboard.getScore();
		score = scores.iterator().next();
		
		Assert.assertEquals(20000L, score.getScore());
		
		// Updating with a lower value should not update the score
		
		ret = LeaderboardDb.submitScore(dbManager, LEADERBOARD_NAME, this.user.getId(), 15000L);
		
		Assert.assertTrue(ret);
		
		testLeaderboard = LeaderboardDb.getLeaderboard(dbManager, LEADERBOARD_NAME);
		
		scores = testLeaderboard.getScore();
		score = scores.iterator().next();
		
		Assert.assertEquals(20000L, score.getScore());
	}

}
