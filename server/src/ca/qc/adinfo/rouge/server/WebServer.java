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

import java.util.Properties;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {
	
	private Properties properties;
	private Server server;
	
	public WebServer(Properties properties) {
		
		this.properties = properties;
	}
	
	public void start() {
		// assumes that this directory contains .html and .jsp files
		// This is just a directory within your source tree, and can be exported as part of your normal .jar
		String WEBAPPDIR = this.properties.getProperty("server.web.dir");

		int port = Integer.parseInt(this.properties.getProperty("server.web.port"));
		
		server = new Server(port);

		// for localhost:port/admin/index.html and whatever else is in the webapp directory
		server.setHandler(new WebAppContext(WEBAPPDIR, "/"));

		// for localhost:port/servlets/cust, etc.
		//final Context context = new Context(server, "/servlets", Context.SESSIONS);
		//context.addServlet(new ServletHolder(new CustomerServlet(whatever)), "/cust");
		//context.addServlet(new ServletHolder(new UserServlet(whatever)), "/user");

		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop() {
		
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
