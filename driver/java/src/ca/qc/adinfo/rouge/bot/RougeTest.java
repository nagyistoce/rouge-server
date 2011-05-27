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

package ca.qc.adinfo.rouge.bot;

import java.util.Collection;

import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.RougeConnectionFailure;
import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.RougeListener;
import ca.qc.adinfo.rouge.data.RougeAchievement;
import ca.qc.adinfo.rouge.data.RougeLeaderboard;
import ca.qc.adinfo.rouge.data.RougeMail;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.data.RougeVariable;

public class RougeTest extends RougeListener {

	private final static Logger log = Logger.getLogger(RougeTest.class);
	
	private RougeDriver driver;
	
	public RougeTest() {
		
		log.trace("Starting test program ...");
		this.driver = new RougeDriver("localhost", 6611, this, false);
		log.trace("Connecting ...");
		
		try {
			this.driver.connect();
		} catch (RougeConnectionFailure e) {
			System.exit(-1);
		}		
		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// Nothing to do
			}
		}
	}
	
	@Override
	public void onOtherMessage(String command, RougeObject payload) {
		
		System.out.println(command + " " + payload.toJSON().toString());
		
	}
	
	@Override
	public void onLogin() {
		
		
		this.driver.joinRoom("testRoom");
		RougeObject newPayload = new RougeObject();
		newPayload.putString("name", "testRoom");
		this.driver.send("room.create", newPayload);
		
		newPayload.putString("message", "hello");
		this.driver.send("room.say", newPayload);
	}
	
	@Override
	public void onRoomJoined(String name) {
		
		RougeObject msg = new RougeObject();
		msg.put("content", "hello!");
		this.driver.sayInRoom("testRoom", msg);
	}
	
	@Override
	public void onRoomSay(String name, String from, RougeObject msg) {
		
		System.out.println(
				"Received from room " + name + " : " + msg.toJSON().toString());
	}

	@Override
	public void onConnect() {
		
		log.trace("Connected ...");
		
		RougeObject payload = new RougeObject();
		payload.putString("username", "bob" + System.currentTimeMillis());
		payload.putString("password", "password");
				
		this.driver.send("login", payload);
	}

	@Override
	public void onDisconnect() {
		
		log.debug("Disconnected ...");
	}
	
	@Override
	public void onError(String command, RougeObject error) {
		
		log.error("Error on " + command + " " + error.toJSON().toString());
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 new RougeTest();

	}

	
}
