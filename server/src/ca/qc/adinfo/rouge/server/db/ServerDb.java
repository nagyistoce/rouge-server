package ca.qc.adinfo.rouge.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.server.DBManager;



public class ServerDb {
	
	private Logger log = Logger.getLogger(ServerDb.class);
	
	private DBManager dbManager;

	public ServerDb(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public void updateServerInfo(String instance, String game, String hostname, int load) {
		
		String sql = "INSERT INTO rouge_servers " +
				"(instance, game, hostname, current_load) " +
				"VALUES (?,?,?,?) ON DUPLICATE KEY " +
				"UPDATE game= ?, hostname = ?, current_load = ?, last_updated = NOW();";
		
		
		try {
			Connection connection = dbManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, instance);
			stmt.setString(2, game);
			stmt.setString(3, hostname);
			stmt.setInt(4, load);
			stmt.setString(5, game);
			stmt.setString(6, hostname);
			stmt.setInt(7, load);
			
			log.debug(stmt);
			
			stmt.executeUpdate();
			
 		} catch(SQLException e) {
 			
 			log.error("Could not update server info. " + e.getMessage());
 		}
		
	}

}
