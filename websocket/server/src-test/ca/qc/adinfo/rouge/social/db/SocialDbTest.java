package ca.qc.adinfo.rouge.social.db;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.qc.adinfo.rouge.db.DbTest;
import ca.qc.adinfo.rouge.user.User;

public class SocialDbTest extends DbTest {
	
	@Before public void setup() throws Exception {
		super.setup();
	}
	
	@After public void cleanup() throws Exception {
		
		quickUpdate("DELETE FROM rouge_mail WHERE `to` = " + this.user.getId() + ";");
		dbManager.disconnect();
	}
	
	@Test public void testAchievementDbTest() {

		User source = new User(this.user.getId() - 1, "sender");
		
		boolean ret = SocialDb.addFriend(dbManager, source.getId(), this.user.getId());
		
		Assert.assertTrue(ret);

		Collection<Long> friends = SocialDb.getFriends(dbManager, source.getId());
		
		Assert.assertTrue(friends.contains(this.user.getId()));

		ret = SocialDb.deleteFriend(dbManager, source.getId(), this.user.getId());
		
		Assert.assertTrue(ret);

		friends = SocialDb.getFriends(dbManager, source.getId());
		
		Assert.assertFalse(friends.contains(this.user.getId()));
	}

}
