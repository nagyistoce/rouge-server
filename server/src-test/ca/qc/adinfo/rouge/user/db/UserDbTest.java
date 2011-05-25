package ca.qc.adinfo.rouge.user.db;

import java.util.Collection;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.log.Log;

import ca.qc.adinfo.rouge.achievement.Achievement;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.db.DbTest;
import ca.qc.adinfo.rouge.mail.Mail;
import ca.qc.adinfo.rouge.mail.db.MailDb;
import ca.qc.adinfo.rouge.user.User;

public class UserDbTest extends DbTest {
	
	@Before public void setup() throws Exception {
		super.setup();
	}
	
	@After public void cleanup() throws Exception {
		
		quickUpdate("DELETE FROM rouge_users WHERE `username` = 'UNITTEST';");
		dbManager.disconnect();
	}
	
	@Test public void testAchievementDbTest() {

		User bob = UserDb.createUser(this.dbManager, "UNITTEST", 
				"123456", "Bob", "Rob", "bob@unittest.com");
		
		Assert.assertNotNull(bob);
		
		User bob2 = UserDb.getUser(dbManager, bob.getUsername());
		
		Assert.assertNotNull(bob2);
		
		Assert.assertEquals(bob.getId(), bob2.getId());
		Assert.assertEquals(bob.getUsername(), bob2.getUsername());
		Assert.assertEquals(bob.getPasswordHash(), bob2.getPasswordHash());
		Assert.assertEquals(bob.getFirstName(), bob2.getFirstName());
		Assert.assertEquals(bob.getLastName(), bob2.getLastName());
		Assert.assertEquals(bob.getEmail(), bob2.getEmail());
		
		bob2 = UserDb.getUser(dbManager, bob.getId());
		
		Assert.assertNotNull(bob2);
		
		Assert.assertEquals(bob.getId(), bob2.getId());
		Assert.assertEquals(bob.getUsername(), bob2.getUsername());
		Assert.assertEquals(bob.getPasswordHash(), bob2.getPasswordHash());
		Assert.assertEquals(bob.getFirstName(), bob2.getFirstName());
		Assert.assertEquals(bob.getLastName(), bob2.getLastName());
		Assert.assertEquals(bob.getEmail(), bob2.getEmail());
		
		Assert.assertTrue(UserDb.isEmailInUse(dbManager, bob.getEmail()));
		Assert.assertTrue(UserDb.isUsernameInUse(dbManager, bob.getUsername()));
		
		Assert.assertFalse(UserDb.isEmailInUse(dbManager, bob.getEmail() + "..."));
		Assert.assertFalse(UserDb.isUsernameInUse(dbManager, bob.getUsername() + "..."));
		
		
		
		
		
	}

}
