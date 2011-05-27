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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.apache.log4j.Logger;

import ca.qc.adinfo.rouge.RougeConnectionFailure;
import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.RougeListener;
import ca.qc.adinfo.rouge.data.RougeObject;

public class RougeBot extends RougeListener implements Runnable  {

	private final static Logger log = Logger.getLogger(RougeTest.class);

	private RougeDriver driver;
	private String roomName;
	private boolean ready;
	

	public RougeBot() {

		try {
		    InetAddress addr = InetAddress.getLocalHost();

		    // Get hostname
		    this.roomName = addr.getHostName();
		} catch (UnknownHostException e) {
			
			this.roomName = "Unknown";
		}
	}

	@Override
	public void onOtherMessage(String command, RougeObject payload) {

		System.out.println(command + " " + payload.toJSON().toString());

		if (command.equals("login")) {

			RougeObject newPayload = new RougeObject();
			newPayload.putString("name", roomName);
			this.driver.send("room.create", newPayload);
			this.driver.send("room.join", newPayload);
			
		} else if (command.equals("room.join")) {
			
			this.ready = true;
		}
	}

	@Override
	public void onConnect() {

		log.trace("Connected ...");

		RougeObject payload = new RougeObject();
		payload.putString("username", roomName + System.currentTimeMillis());
		payload.putString("password", "password");

		this.driver.send("login", payload);
	}

	@Override
	public void run() {

		Random random = new Random();

		while(true) {

			this.ready = false;

			this.driver = new RougeDriver("localhost", 6612, this, true);
			
			try {
				this.driver.connect();
			} catch (RougeConnectionFailure e) {
				System.exit(-1);
			}

			int rounds = random.nextInt(200) + 5;

			for(int i = 0; i < rounds; i++) {

				try {
					Thread.sleep(2000 + random.nextInt(8000));
				} catch (InterruptedException e) {

				}

				if (this.ready) {

					RougeObject payload = new RougeObject();
					payload.putString("name", this.roomName);
					payload.putString("message", "hello");
					this.driver.send("room.say", payload);
				}
			}

			this.driver.disconnect();
		}
	}

	@Override
	public void onDisconnect() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random random = new Random();
		
		for(int i = 0; i < 20; i++) {
			
			Thread t = new Thread(new RougeBot());
			t.start();
			
			try {
				Thread.sleep(random.nextInt(5000));
			} catch (InterruptedException e) {

			}
		}

	}

	@Override
	public void onError(String command, RougeObject error) {
		
		log.error("Error on " + command + " " + error.toJSON().toString());
		
	}



}
