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

package ca.qc.adinfo.rouge.achievement.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.achievement.Achievement;
import ca.qc.adinfo.rouge.server.DBManager;

public class AchievementDb {
	
	private static Logger log = Logger.getLogger(AchievementDbTest.class);
	
	public static HashMap<String, Achievement> getAchievements(DBManager dbManager, long userId) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<String, Achievement> returnValue = new HashMap<String, Achievement>();
		
		String sql = "SELECT ach.`key` as `key`, " +
				"ach.point_value as point_value, " +
				"prg.progress as progress, ach.total as total " +
				"FROM rouge_achievement_progress as prg, rouge_achievements as ach W" +
				"HERE ach.key = prg.achievement_key and prg.user_id = ?; ";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String key = rs.getString("key");
				
				Achievement achievement = new Achievement(key, 
						rs.getInt("point_value"), 
						rs.getDouble("total"), 
						rs.getDouble("progress"));
				

				returnValue.put(key, achievement);
			}
			
			return returnValue;
			
		} catch (SQLException e) {
			log.error(stmt);
			log.error(e);
			return null;
			
		} finally {
		
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(connection);
		}
	}
	
	public static boolean createAchievement(DBManager dbManager, String key, String name, int pointValue, double total) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		sql = "INSERT INTO rouge_achievements (`key`, `name`, `point_value`, `total`) " +
				" VALUES (?, ?, ?, ?);";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, key);
			stmt.setString(2, name);
			stmt.setInt(3, pointValue);
			stmt.setDouble(4, total);
						
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
	
	public static boolean updateAchievement(DBManager dbManager, String key, long userId, double progress) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		sql = "INSERT INTO rouge_achievement_progress (`achievement_key`, `user_id`, `progress`) " +
				"VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE progress = MAX(?, progress);";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, key);
			stmt.setLong(2, userId);
			stmt.setDouble(3, progress);
			stmt.setDouble(4, progress);
						
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

}
