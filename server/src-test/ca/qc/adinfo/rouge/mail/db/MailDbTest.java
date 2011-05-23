package ca.qc.adinfo.rouge.mail.db;

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

public class MailDbTest extends DbTest {
	
	@Before public void setup() throws Exception {
		super.setup();
	}
	
	@After public void cleanup() throws Exception {
		
		quickUpdate("DELETE FROM rouge_mail WHERE `to` = " + this.user.getId() + ";");
		dbManager.disconnect();
	}
	
	@Test public void testAchievementDbTest() {

		RougeObject msgContent = new RougeObject();
		msgContent.putString("subject", "test");
		msgContent.putString("content", "this is a test message!");

		User source = new User(this.user.getId() - 1, "sender");
		
		boolean ret = MailDb.sendMail(
				dbManager, source.getId(), this.user.getId(), msgContent);
		
		Assert.assertTrue(ret);
		
		Collection<Mail> mailbox = MailDb.getMails(dbManager, this.user.getId(), false);
		
		Assert.assertEquals(1, mailbox.size());
		
		Mail mail = mailbox.iterator().next();
		
		Assert.assertEquals("test", mail.getContent().getString("subject"));
		Assert.assertEquals("this is a test message!", mail.getContent().getString("content"));
		Assert.assertEquals(this.user.getId(), mail.getToId());
		Assert.assertEquals(source.getId(), mail.getFromId());
		
		// Check the mark as read flag
				
		ret = MailDb.setMailAsRead(dbManager, mail.getId());
		
		Assert.assertTrue(ret);
		
		mailbox = MailDb.getMails(dbManager, this.user.getId(), false);
		
		Assert.assertEquals(1, mailbox.size());
		
		mailbox = MailDb.getMails(dbManager, this.user.getId(), true);
		
		Assert.assertEquals(0, mailbox.size());
		
		// Check the delete flag
		
		ret = MailDb.deleteMail(dbManager, mail.getId());
		
		Assert.assertTrue(ret);
		
		mailbox = MailDb.getMails(dbManager, this.user.getId(), false);
		
		Assert.assertEquals(0, mailbox.size());
		
		mailbox = MailDb.getMails(dbManager, this.user.getId(), true);
		
		Assert.assertEquals(0, mailbox.size());
		
	}

}
