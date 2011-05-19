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

package ca.qc.adinfo.rouge.json;

import java.util.concurrent.atomic.AtomicLong;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.data.RougeObject;

public class JSonChannelHandler extends SimpleChannelUpstreamHandler {

	private static final Logger log = Logger.getLogger(
			JSonChannelHandler.class);

	private final AtomicLong transferredBytes = new AtomicLong();

	private RougeDriver driver;
	
	private String msgBuffer;
	
	public JSonChannelHandler(RougeDriver driver) {
		this.driver = driver;
		this.msgBuffer = "";
	}
	
	public long getTransferredBytes() {
		return transferredBytes.get();
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {

		log.trace("Channel connected " + e.getChannel().getId());
		
		driver.channel = e.getChannel();
		if (this.driver.listener != null) {
			driver.listener.onConnect();
		}
		
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		
		log.trace("Message received from channel " + e.getChannel().getId());
		
		this.msgBuffer += e.getMessage();
		int seperatorPos = this.msgBuffer.indexOf("|");
		
		if (seperatorPos != -1) {
			
			String message = this.msgBuffer.substring(0, seperatorPos);
			
			if (this.msgBuffer.length() == seperatorPos + 1) {
				this.msgBuffer = "";
			} else {
				this.msgBuffer = this.msgBuffer.substring(seperatorPos + 1);
			}
			
			JSONObject jsonObject = JSONObject.fromObject(message);
			String command = jsonObject.getString("command");
			RougeObject payload = new RougeObject(jsonObject.getJSONObject("payload"));

			if (this.driver.listener != null) {
				this.driver.listener.onOtherMessage(command, payload);
			}
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		// Close the connection when an exception is raised.
		log.error("Unexpected exception from downstream.");
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
}
