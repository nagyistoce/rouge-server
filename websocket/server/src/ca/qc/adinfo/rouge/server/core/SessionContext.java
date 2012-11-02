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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import org.jboss.netty.channel.Channel;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.user.User;

public class SessionContext {

	private Channel channel;
	private User user;
	private ChannelWriter channelWriter;
	
	private long startTime;
	
	private HashMap<String, Object> attachments;
	
	private LinkedList<String> communcationLog; 
	
	public SessionContext(Channel channel, ChannelWriter channelWriter) {
		
		this.channel = channel;
		this.channelWriter = channelWriter;
		
		this.startTime = System.currentTimeMillis();
		this.attachments = new HashMap<String, Object>();
		this.communcationLog = new LinkedList<String>();
	}
	
	public Channel getChannel() {
		
		return this.channel;
	}
	
	public User getUser() {
		
		return this.user;
	}
	
	public void setUser(User user) {
		
		this.user = user;
	}
	
	public void setAttachment(String key, Object value) {
		
		this.attachments.put(key, value);
	}
	
	public Object getAttachment(String key) {
		
		return this.attachments.get(key);
	}
	
	public boolean hasAttachment(String key) {
		
		return this.attachments.containsKey(key);
	}
	
	public int getId() {
		
		return this.channel.getId();
	}
	
	public void send(String command, RougeObject payload) {
		
		this.channelWriter.send(command, payload);
		
		this.addToCommunicationLog("Out: " + command + " -> " + payload.toJSON());
	}
	
	public void received(String command, RougeObject payload) {
		
		this.addToCommunicationLog("In: " + command + " -> " + payload.toJSON());
	}
	
	public Collection<String> getCommunicationLog() {
		
		ArrayList<String> toReturn = new ArrayList<String>();
		
		synchronized(this.communcationLog) {
			toReturn.addAll(this.communcationLog);
		}
		
		return toReturn;
	}
	
	private void addToCommunicationLog(String communication) {
		
		synchronized (this.communcationLog) {
			
			this.communcationLog.addLast(communication);
			
			if (this.communcationLog.size() > 20) {
				this.communcationLog.removeFirst();
			}
		}
	}
	
	public long getStartTime() {
		
		return this.startTime;
	}
}
