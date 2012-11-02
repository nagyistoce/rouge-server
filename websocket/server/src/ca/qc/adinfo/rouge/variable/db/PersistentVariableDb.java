/*
 * Copyright [2011] [ADInfo, Alexandre Denault]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.qc.adinfo.rouge.variable.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.variable.Variable;

public class PersistentVariableDb {
	
	private static Logger log = Logger.getLogger(PersistentVariableDb.class);
	
	public static boolean updatePersitentVariable(DBManager dbManager, User user, Variable variable) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		if (variable.getVersion() == 0) {
			sql = "INSERT INTO rouge_persistant_variable (`key`, `value`, `version`, `creator_user_id`) " +
					" VALUES (?, ?, ?, ?);";
		} else {
			sql = "UPDATE rouge_persistant_variable SET `value` = ?, `version` = ? " +
				" WHERE `key` = ? AND `version` = ?";
		}
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			if (variable.getVersion() == 0) {
				stmt.setString(1, variable.getKey());
				stmt.setString(2, variable.getValue().toJSON().toString());
				stmt.setLong(3, 1);
				stmt.setLong(4, user.getId());
				
				variable.setVersion(1);
			} else {
				stmt.setString(1, variable.getValue().toJSON().toString());
				stmt.setLong(2, variable.getVersion()+1);
				stmt.setString(3, variable.getKey());
				stmt.setLong(4, variable.getVersion());
				
				variable.setVersion(variable.getVersion()+1);
			}
			
			int ret = stmt.executeUpdate();
			
			return (ret > 0);
			
		} catch (SQLException e) {
			log.error(stmt);
			log.error(e);
			return false;
			
		} finally {
		
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(connection);
		}
	}
	
	public static Variable getPersitentVariable(DBManager dbManager, String key) {
	
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT value, version FROM rouge_persistant_variable WHERE `key` = ?";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, key);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				JSONObject jSonObject = JSONObject.fromObject(rs.getString("value"));
			
				return new Variable(key, 
					new RougeObject(jSonObject), rs.getLong("version"));
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			
			log.error(e);
			return null;
			
		} finally {
		
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(connection);
		}
	}

}
