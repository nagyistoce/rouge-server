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

import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import ca.qc.adinfo.rouge.command.RougeCommandProcessor;
import ca.qc.adinfo.rouge.server.core.SessionManager;
import ca.qc.adinfo.rouge.server.core.bencode.BEncodeChannelHandler;
import ca.qc.adinfo.rouge.server.core.json.JsonChannelHandler;
import ca.qc.adinfo.rouge.server.core.json.JsonPipelineFactory;
import ca.qc.adinfo.rouge.server.core.websocket.WebSocketChannelHandler;
import ca.qc.adinfo.rouge.server.core.websocket.WebSocketPipelineFactory;

public class CoreServer {

	private static Logger log = Logger.getLogger(CoreServer.class);
	
	static final ChannelGroup allChannels = new DefaultChannelGroup(
			"Rouge-Server");

	private Properties properties;
	private RougeCommandProcessor commandProcessor;
	private SessionManager sessionManager;

	private ChannelFactory factory;
	private Channel channelJSon;
	private Channel channelBinary;
	private Channel channelWebSocket;

	public CoreServer(Properties properties, RougeCommandProcessor commandProcessor, SessionManager sessionManager) {

		this.properties = properties;
		
		this.commandProcessor = commandProcessor;
		this.sessionManager = sessionManager;
	}

	public void start() {

		factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		boolean jsonServerEnabled = Boolean.parseBoolean((String)this.properties
				.get("server.core.json.enabled"));
		boolean binaryServerEnabled = Boolean.parseBoolean((String)this.properties
				.get("server.core.binary.enabled"));
		boolean webSocketServerEnabled = Boolean.parseBoolean((String)this.properties
				.get("server.core.websocket.enabled"));

		int jsonServerPort = Integer.parseInt((String)this.properties
				.get("server.core.json.port"));
		int binaryServerPort = Integer.parseInt((String)this.properties
				.get("server.core.binary.port"));
		int webSocketServerPort = Integer.parseInt((String)this.properties
				.get("server.core.websocket.port"));

		if (jsonServerEnabled) {
			log.info("Starting JSon Listener on port " + jsonServerPort);
			ServerBootstrap bootstrap = new ServerBootstrap(factory);

			JsonChannelHandler handler = new JsonChannelHandler(
					commandProcessor, sessionManager);
			
			bootstrap.setPipelineFactory(new JsonPipelineFactory(handler));
			
			bootstrap.setOption("child.tcpNoDelay", true);
			bootstrap.setOption("child.keepAlive", true);
			
			channelJSon = bootstrap.bind(new InetSocketAddress(jsonServerPort));

			allChannels.add(channelJSon);
		}

		if (binaryServerEnabled) {
			log.info("Starting BEncode Listener on port " + binaryServerPort);
			ServerBootstrap bootstrap = new ServerBootstrap(factory);

			bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				public ChannelPipeline getPipeline() {
					return Channels.pipeline(new BEncodeChannelHandler(
							commandProcessor, sessionManager));
				}
			});

			bootstrap.setOption("child.tcpNoDelay", true);
			bootstrap.setOption("child.keepAlive", true);

			channelBinary = bootstrap.bind(new InetSocketAddress(
					binaryServerPort));

			allChannels.add(channelBinary);

		}
		
		if (webSocketServerEnabled) {
			log.info("Starting WebSocket Listener on port " + webSocketServerPort);
			ServerBootstrap bootstrap = new ServerBootstrap(factory);

			WebSocketChannelHandler handler = new WebSocketChannelHandler(
					commandProcessor, sessionManager);
			
			bootstrap.setPipelineFactory(new WebSocketPipelineFactory(handler));
			
			bootstrap.setOption("child.tcpNoDelay", true);
			bootstrap.setOption("child.keepAlive", true);
			
			channelWebSocket = bootstrap.bind(new InetSocketAddress(webSocketServerPort));

			allChannels.add(channelWebSocket);
		}
	}

	public void stop() {

		log.trace("Stopping server.");
		
		ChannelGroupFuture future = allChannels.close();
		future.awaitUninterruptibly();
		factory.releaseExternalResources();

	}
	
}
