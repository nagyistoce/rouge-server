package ca.qc.adinfo.rouge;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ca.qc.adinfo.rouge.achievement.db.AchievementDbTest;
import ca.qc.adinfo.rouge.leaderboard.db.LeaderboardDbTest;
import ca.qc.adinfo.rouge.variable.db.PersistentVariableDbTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AchievementDbTest.class,
    LeaderboardDbTest.class,
    PersistentVariableDbTest.class
})

public class AllTests {

	
	
}
