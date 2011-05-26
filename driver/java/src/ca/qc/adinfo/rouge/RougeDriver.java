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

package ca.qc.adinfo.rouge;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import ca.qc.adinfo.rouge.bencode.BEncodeChannelHandler;
import ca.qc.adinfo.rouge.bencode.BEncodeChannelWriter;
import ca.qc.adinfo.rouge.bencode.BEncodePipelineFactory;
import ca.qc.adinfo.rouge.data.RougeAchievement;
import ca.qc.adinfo.rouge.data.RougeFriend;
import ca.qc.adinfo.rouge.data.RougeLeaderboard;
import ca.qc.adinfo.rouge.data.RougeMail;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.data.RougeVariable;
import ca.qc.adinfo.rouge.json.JSonChannelHandler;
import ca.qc.adinfo.rouge.json.JSonPipelineFactory;
import ca.qc.adinfo.rouge.json.JsonChannelWriter;

public class RougeDriver {

	private static final Logger log = Logger.getLogger(
			RougeDriver.class);

	private String host;
	private int port;

	private ClientBootstrap bootstrap;

	private SimpleChannelUpstreamHandler handler;

	public RougeListener listener;
	public Channel channel;
	
	public ChannelWriter channelWriter;
	
	private boolean bEncode;

	public RougeDriver(String host, int port, RougeListener listener, boolean bEncode) {
		super();
		this.host = host;
		this.port = port;
		this.listener = listener;
		this.bEncode = bEncode;

		// Configure the client.
		bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		
		if (this.bEncode) {
			channelWriter = new BEncodeChannelWriter();
			handler = new BEncodeChannelHandler(this);
			bootstrap.setPipelineFactory(new BEncodePipelineFactory(handler));		
		} else {
			channelWriter = new JsonChannelWriter();
			handler = new JSonChannelHandler(this);
			bootstrap.setPipelineFactory(new JSonPipelineFactory(handler));	
		}

	}

	public void connect() throws RougeConnectionFailure {
		// Start the connection attempt.
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host,
				port));

		this.channel = future.awaitUninterruptibly().getChannel();

		if (!future.isSuccess()) {			
			throw new RougeConnectionFailure(future.getCause());
		} else {
			if (this.listener != null) {
				this.listener.onConnect();
			}	
		}
	}

	public void disconnect() {

		ChannelFuture future = this.channel.close();
		future.awaitUninterruptibly();

		// Shut down thread pools to exit.
		bootstrap.releaseExternalResources();
	}

	public void registerListener(RougeListener listener) {

		this.listener = listener;
	}
	
	public void createRoom(String name) {
		
		RougeObject payload = new RougeObject();
		payload.putString("name", name);
		
		this.send("room.create", payload);
	}
	
	public void joinRoom(String name) {
		
		RougeObject payload = new RougeObject();
		payload.putString("name", name);
		
		this.send("room.join", payload);
	}
	
	public void leaveRoom(String name) {
		
		RougeObject payload = new RougeObject();
		payload.putString("name", name);
		
		this.send("room.leave", payload);
	}
	
	public void destroyRoom(String name) {
		
		RougeObject payload = new RougeObject();
		payload.putString("name", name);
		
		this.send("room.destroy", payload);
	}
	
	public void sayInRoom(String name, RougeObject message) {
		
		RougeObject payload = new RougeObject();
		payload.putString("name", name);
		payload.putRougeObject("message", message);
		
		this.send("room.say", payload);
	}
	
	public RougeVariable getVariable(String key) {
		
		return null;
	}
	
	public void setVariable(String key, RougeVariable variable) {
		
	}
	
	public RougeVariable getPersistentVariable(String key) {
		
		return null;
	}
	
	public void setPersistentVariable(String key, RougeVariable variable) {
		
	}
	
	public void addFriend(String username) {
		
	}
	
	public void removeFriend(String username) {
		
	}
	
	public Collection<RougeFriend> getFriendList() {
		
		return null;
	}
	
	public String getFriendStatus(String username) {
		
		return null;
	}
	
	public void sayToFriends(RougeObject message) {
		
	}
	
	public void sayToUser(String username, RougeObject message) {
		
	}
	
	public void sendMail(String username, RougeMail mail) {
		
	}
	
	public Collection<RougeObject> getAllMail() {
	
		return null;
	}
	
	public Collection<RougeMail> getUnreadMail() {
		
		return null;
	}
	
	public void markMailAsRead(RougeMail mail) {
		
		
	}
	
	public void deleteMail(RougeMail mail) {
		
	}
	
	public RougeLeaderboard getLeaderboard(String key) {
		
		return null;
	}
	
	public void submitScore(String key, long score) {
		
	}
	
	public Collection<RougeAchievement> getAchievements() {
		
		return null;
	}

	public void updateAchivementProgress(String key, float value) {
		
	}
	
	public void send(String command, RougeObject payload) {

		if (channel != null) {
			
			this.channelWriter.send(channel, command, payload);		
			log.trace("Sent " + command);			
		}
	}



}
