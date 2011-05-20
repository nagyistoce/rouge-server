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

package ca.qc.adinfo.rouge.user;

import ca.qc.adinfo.rouge.server.core.SessionContext;

public class User {
	
	private long id;
	private String username;
	
	private SessionContext sessionContext;
	
	public User(long id, String username) {
		super();
		
		this.id = id;
		this.username = username;
	}
	
	public long getId() {
		
		return this.id;
	}

	public String getUsername() {
		
		return this.username;
	}

	public SessionContext getSessionContext() {
		return sessionContext;
	}

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
	
	

}
