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

package ca.qc.adinfo.rouge.server.core;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

public class SessionManager {
	
	private static final Logger log = Logger.getLogger(SessionManager.class);
	
	private HashMap<Integer, SessionContext> sessions;
	
	public SessionManager() {
		
		this.sessions = new HashMap<Integer, SessionContext>();
	}
	
	public void registerSession(SessionContext session) {
		
		synchronized (this.sessions) {
			this.sessions.put(session.getChannel().getId(), session);
		}
	}
	
	public void unregisterSession(SessionContext session) {
		
		synchronized (this.sessions) {
			this.sessions.remove(session.getChannel().getId());
		}
	}
	
	public SessionContext getSession(Channel channel) {
		
		synchronized (this.sessions) {
			return this.sessions.get(channel.getId());
		}
	}
	
	public SessionContext getSession(int id) {
		
		synchronized (this.sessions) {
			return this.sessions.get(id);
		}
	}
	
	public Collection<SessionContext> getSessions() {
		
		synchronized (this.sessions) {
			return this.sessions.values();
		}
	}
	
	public int getNumberSession() {
		
		return this.sessions.size();
	}

}
