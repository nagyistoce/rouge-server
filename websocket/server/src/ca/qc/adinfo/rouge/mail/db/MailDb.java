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

package ca.qc.adinfo.rouge.mail.db;

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

public class MailDb {
	
	private static Logger log = Logger.getLogger(MailDb.class);
	
	public static boolean sendMail(DBManager dbManager, long fromId, long toId, RougeObject content) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		sql = "INSERT INTO rouge_mail (`from`, `to`, `content`, `status`, `time_sent`) " +
				"VALUES (?, ?, ?, ?, ?)";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, fromId);
			stmt.setLong(2, toId);
			stmt.setString(3, content.toJSON().toString());
			stmt.setString(4, "unr");
			stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
						
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
	
	public static boolean setMailAsRead(DBManager dbManager, long mailId) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		sql = "UPDATE rouge_mail SET `status` = ? WHERE `id` = ? ";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, "rea");
			stmt.setLong(2, mailId);
						
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
	
	public static boolean deleteMail(DBManager dbManager, long mailId) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		sql = "UPDATE rouge_mail SET `status` = ? WHERE `id` = ? ";
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, "del");
			stmt.setLong(2, mailId);
						
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

	public static Collection<Mail> getMails(DBManager dbManager, long userId, boolean unreadOnly) {
		
		Collection<Mail> mailbox = new ArrayList<Mail>();
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT `id`, `from`, `to`, `content`, `status`, `time_sent` " +
				"FROM rouge_mail WHERE `to` = ? ";
		
		if (unreadOnly) {
			sql += " AND `status` = 'unr'";
		} else {
			sql += " AND NOT `status` = 'del'";
		}
		
		try {
			connection = dbManager.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, userId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				mailbox.add(new Mail(rs.getLong("id"), rs.getLong("from"), rs.getLong("to"), 
						new RougeObject(JSONObject.fromObject(rs.getString("content")))));
			}
			
			return mailbox;
			
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
