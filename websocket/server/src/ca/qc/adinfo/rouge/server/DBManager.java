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

package ca.qc.adinfo.rouge.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;

public class DBManager {
	
	private BasicDataSource dataSource;
	private Properties properties;
	
	public DBManager(Properties properties) {
		
		this.properties = properties;
		
	}
	
	public void connect() {
		
		this.dataSource = new BasicDataSource();
		
		this.dataSource.setDriverClassName(this.properties.getProperty("db.driver"));
		this.dataSource.setUsername(this.properties.getProperty("db.user"));
		this.dataSource.setPassword(this.properties.getProperty("db.pwd"));
		this.dataSource.setUrl(this.properties.getProperty("db.url"));
		
	}
	
	public void disconnect() {
		
		try {
			this.dataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Create a DataSource based on database properties
	 */
	public Connection getConnection() throws SQLException{
		
		return dataSource.getConnection();
	}
	

}
