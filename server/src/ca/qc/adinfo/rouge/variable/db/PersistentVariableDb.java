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
import ca.qc.adinfo.rouge.variable.Variable;

public class PersistentVariableDb {
	
	private static Logger log = Logger.getLogger(PersistentVariableDb.class);
	
	public static void updatePersitentVariable(DBManager dbManager, Variable variable) {
		
	}
	
	public static Variable getPersitentVariable(DBManager dbManager, String key) {
	
		Connection connection = null;
		PreparedStatement stmt = null;
		
		String sql = "SELECT value, version FROM rouge_persistant_variable WHERE key = ?";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, key);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.next();
			
			JSONObject jSonObject = JSONObject.fromObject(rs.getString("value"));
			
			return new Variable(key, 
					new RougeObject(jSonObject), rs.getLong("version"));
			
			
		} catch (SQLException e) {
			
			log.error(e);
			return null;
			
		} finally {
		
			DbUtils.closeQuietly(connection);
		}
		
	}

}
