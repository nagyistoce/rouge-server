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

package ca.qc.adinfo.rouge.server.core.bencode;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.ChannelWriter;

public class BEncodeChannelWriter implements ChannelWriter {

	//private static final Logger log = Logger.getLogger(BEncodeChannelWriter.class);
	
	private Channel channel;
	
	public BEncodeChannelWriter(Channel channel) {
		
		this.channel = channel;
	}
	
	public void send(String command, RougeObject payload) {
			
		RougeObject message = new RougeObject();
		
		message.putString("command", command);
		message.putRougeObject("payload", payload);
		
		byte[] msg = BEncoder.bencode(message);
		
		ChannelBuffer buffer = ChannelBuffers.buffer(msg.length);
		buffer.writeBytes(msg);
		
		channel.write(buffer);
		
		//log.debug ("Sent: " + new String(msg));
	}
}
