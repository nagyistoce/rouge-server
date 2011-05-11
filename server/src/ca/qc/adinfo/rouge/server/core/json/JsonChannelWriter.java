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

package ca.qc.adinfo.rouge.server.core.json;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.ChannelWriter;

public class JsonChannelWriter implements ChannelWriter {

	private static final Logger log = Logger.getLogger(JsonChannelWriter.class);
	
	private Channel channel;
	
	public JsonChannelWriter(Channel channel) {
		
		this.channel = channel;
	}
	
	public void send(String command, RougeObject payload) {
		
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("command", command);
			jsonObj.put("payload", payload.toJSON());

			ChannelFuture future = channel.write(jsonObj.toString() + "|\n");
			
			log.trace("Sent " + jsonObj.toString());					
	}
}
