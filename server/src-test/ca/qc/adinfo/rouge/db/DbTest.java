package ca.qc.adinfo.rouge.db;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.user.User;

public class DbTest {
	
	protected DBManager dbManager;
	protected User user;
	
	public void setup() throws Exception {
		
		Properties serverProperties = new Properties();
		serverProperties.load(new FileReader(new File("./conf/config.properties")));
		
		dbManager = new DBManager(serverProperties);
		dbManager.connect();
		
		user  = new User(-1, "unittest");
	}
	
	public void quickUpdate(String sql) throws Exception {
		
		Connection connection =  dbManager.getConnection();
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();
		
		DbUtils.closeQuietly(stmt);
		DbUtils.closeQuietly(connection);
	}
	
}
