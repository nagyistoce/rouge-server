package ca.qc.adinfo.rouge.variable.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import junit.framework.Assert;

import org.apache.commons.dbutils.DbUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.db.DbTest;
import ca.qc.adinfo.rouge.variable.Variable;

public class PersistentVariableDbTest extends DbTest {

	@Before public void setup() throws Exception {
		super.setup();
	}
	
	@After public void cleanup() throws Exception {
		
		quickUpdate("DELETE FROM rouge_persistant_variable WHERE `key` = 'UNITTEST';");
		dbManager.disconnect();
	}
	
	@Test public void testPersistentVariableDb() {

		RougeObject rougeObject = new RougeObject();
		rougeObject.putString("UNITTEST", "TEST");
		
		Variable initialVariable = new Variable("UNITTEST");
		initialVariable.setValue(rougeObject);
		
		boolean ret = PersistentVariableDb.updatePersitentVariable(dbManager, user, initialVariable);
		
		Assert.assertTrue(ret);
		
		Variable retrievedValue = PersistentVariableDb.getPersitentVariable(dbManager, "UNITTEST");
		
		Assert.assertNotNull(retrievedValue);

		Assert.assertEquals("TEST", retrievedValue.getValue().getString("UNITTEST"));
		
		retrievedValue.getValue().putString("UNITTEST", "TEST2");
		
		ret = PersistentVariableDb.updatePersitentVariable(dbManager, user, retrievedValue);
		
		Assert.assertTrue(ret);
		
		ret = PersistentVariableDb.updatePersitentVariable(dbManager, user, initialVariable);
		
		Assert.assertFalse(ret);
		
		retrievedValue = PersistentVariableDb.getPersitentVariable(dbManager, "UNITTEST");
		
		Assert.assertEquals("TEST2", retrievedValue.getValue().getString("UNITTEST"));
		
		retrievedValue = PersistentVariableDb.getPersitentVariable(dbManager, "UNITTEST_2222");
		
		Assert.assertNull(retrievedValue);
		
	}

}
