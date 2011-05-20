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

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.SimpleChannelHandler;

import ca.qc.adinfo.rouge.command.RougeCommandProcessor;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.user.User;

public abstract class ServerHandler extends SimpleChannelHandler {
	
	private final static Logger log = Logger.getLogger(ServerHandler.class);

	private RougeCommandProcessor commandProcessor;
	private SessionManager sessionManager;
	
	public ServerHandler(RougeCommandProcessor commandProcessor, SessionManager sessionManager) {
		
		this.commandProcessor = commandProcessor;
		this.sessionManager = sessionManager;
	}
	
    public void onChannelConnected(Channel ch, SessionContext session) {

    	this.sessionManager.registerSession(session);
    	
    	
    }
	
    public void onChannelDisconnected(Channel ch) {
    	
    	SessionContext session = this.sessionManager.getSession(ch);
    	User user = session.getUser();
    	
    	if(user != null) {
    		
    		this.commandProcessor.processCommand("logoff", new RougeObject(), session, user);
    	}
    	
    	this.sessionManager.unregisterSession(session);
    }
    
    public void onMessageReceived(Channel ch, String command, RougeObject payload) {

    	
    	SessionContext session = this.sessionManager.getSession(ch);
    	User user = session.getUser();
    	
    	session.received(command, payload);
    	
    	if (user == null) {
    	// User is not authenticated	
    		
    		log.debug("Received command " + command + " from anonymous connection.");
    	
    		if (command.equals("login")) {
    			// Only login commands are allowed
    			try {
    				this.commandProcessor.processCommand("login", payload, session, session.getUser());
    			
    			} catch(Exception e) {
    				// Any exception thrown for a login command results in a failure    		
    				ch.disconnect();
    			}
    			
    		} else {
    			log.debug("Command not allowed, disconnecting.");
    			ch.disconnect();
    		}
    		
    	} else {

    		this.commandProcessor.processCommand(command, payload, session, session.getUser());    		
    	}    					
    }

   
    public void onExceptionCaught(Channel ch, Throwable e) {
    	
        //e.printStackTrace();
        ch.close();
    }
}
