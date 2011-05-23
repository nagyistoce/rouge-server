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

package ca.qc.adinfo.rouge.social.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.json.JSONObject;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.mail.Mail;
import ca.qc.adinfo.rouge.server.DBManager;

public class SocialDb {
	
	private static Logger log = Logger.getLogger(SocialDb.class);
	
	public static boolean addFriend(DBManager dbManager, long userId, long friendId) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		sql = "INSERT INTO rouge_social_friends (`user_id`, `friend_user_id`) VALUES (?, ?)";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, userId);
			stmt.setLong(2, friendId);
									
			int ret = stmt.executeUpdate();
			
			if (ret < 1) {
				return false;
			}
			
			stmt.setLong(1, friendId);
			stmt.setLong(2, userId);
												
			ret = stmt.executeUpdate();
			
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
	
	public static boolean deleteFriend(DBManager dbManager, long userId, long friendId) { 
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		sql = "DELETE FROM rouge_social_friends " +
				"WHERE (`user_id` = ? AND `friend_user_id` = ?) " +
				"OR (`user_id` = ? AND `friend_user_id` = ?)";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, userId);
			stmt.setLong(2, friendId);
			stmt.setLong(3, friendId);
			stmt.setLong(4, userId);
									
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
	
	public static Collection<Long> getFriends(DBManager dbManager, long userId) {
		
		Collection<Long> friends = new ArrayList<Long>();
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT `friend_user_id` FROM rouge_social_friends WHERE `user_id` = ? ";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, userId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				friends.add(rs.getLong("friend_user_id"));				
			}
			
			return friends;
			
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

	
	
}
