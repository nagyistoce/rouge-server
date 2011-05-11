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

package ca.qc.adinfo.servlet.rouge;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.monitor.ResourceTimePoint;
import ca.qc.adinfo.rouge.room.Room;
import ca.qc.adinfo.rouge.room.RoomManager;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.server.core.SessionManager;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;

public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		this.doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		if (request.getParameter("username") != null) {

			if (request.getParameter("username").equals("admin") && 
					request.getParameter("password").equals("admin")) {

				session.setAttribute("auth", true);
			}
		}

		if(session.getAttribute("auth") == null) {
			response.sendRedirect(response.encodeRedirectURL("/"));
		}

		String action = request.getParameter("action");

		if (action == null || action.isEmpty()) {
			action = "dashboard";
		}

		if ("dashboard".equals(action)) {

			this.dashboardPage(request, response);

		} else if (("sessions").equals(action)) {

			this.sessionsPage(request, response);

		} else if (("session").equals(action)) {

			this.sessionPage(request, response);

		} else if (("users").equals(action)) {

			this.usersPage(request, response);

		} else if (("rooms").equals(action)) {

			this.roomsPage(request, response);

		} else if (("logout").equals(action)) {

			this.logout(request, response);

		}
	}


	public void dashboardPage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		SessionManager sessionManager = RougeServer.getInstance().getSessionManager();
		RoomManager roomManager = (RoomManager)RougeServer.getInstance().getModule("room.manager");
		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");

		PrintWriter out = response.getWriter();

		out.println(this.createHeader(request));

		out.println(this.startBox(request, "Dashboard"));

		//out.println("<p>Welcome to the NovaServer Dashboard</p>");

		out.println("<ul>");

		out.println("<li> Number of active sessions: " + sessionManager.getSessions().size() + "</li>");
		out.println("<li> Number of active users: " + userManager.getUsers().size() + "</li>");
		out.println("<li> Number of active rooms: " + roomManager.getRooms().size() + "</li>");

		out.println("</ul>");

		out.println(this.endBox(request));
		
		out.println("<br/><br/>");
		
		out.println("<div class=\"span-12\">\n");
		
		out.println("<h4>CPU Usage</h4>");
		
		out.println("<div id=\"cpugraph\" style=\"width:400px;height:200px;\"></div>");
		out.println("<div id=\"cpugraphlegend\" style=\"width:400px;height:100px;\"></div>");
		
		out.println("</div>");
		out.println("<div class=\"span-12 last\">\n");
		
		out.println("<h4>Network Usage</h4>");
		
		out.println("<div id=\"netgraph\" style=\"width:400px;height:200px;\"></div>");		
		out.println("<div id=\"netgraphlegend\" style=\"width:400px;height:100px;\"></div>");
		
		out.println("</div>");

		out.println("<script type=\"text/javascript\">");
		out.println("$(function () {");
		out.println(plotResourceData());		    
		out.println("    $.plot($(\"#cpugraph\"), [ { data: varcpu, label: \"CPU\" } ]," +
				" { legend: { container: \"#cpugraphlegend\"} }  );");
		out.println("});");
		out.println("</script>");
		
		out.println("<script type=\"text/javascript\">");
		out.println("$(function () {");
		out.println(plotResourceData());		    
		out.println("    $.plot($(\"#netgraph\"), [ { data: varnetin, label: \"Network In\" }, { data: varnetout, label: \"Network Out\" }, { data: varnettot, label: \"Network Total\" } ]," +
				" { legend: { container: \"#netgraphlegend\"} }  );");
		out.println("});");
		out.println("</script>");
		out.println("");



		out.println(this.createFooter(request));

	}
	
	private String plotResourceData() {
		
		StringBuffer stringBuffer = new StringBuffer();
		
		Collection<ResourceTimePoint> timePoints = 
			RougeServer.getInstance().getResourceMonitor().getTimePoints();
		
		stringBuffer.append("\n varcpu = [");
		
		boolean comma = false;
		int count = 0;
		
		for(ResourceTimePoint timePoint: timePoints) {
			
			if (!comma) {
				comma = true;
			} else {
				stringBuffer.append(", ");
			}
			
			stringBuffer.append("[" + count + "," + timePoint.getCpuUsage() + "]");
			
			count++;
		}
		
		stringBuffer.append("];");
		
		stringBuffer.append("\n varnetin = [");
		
		comma = false;
		count = 0;
		
		for(ResourceTimePoint timePoint: timePoints) {
			
			if (!comma) {
				comma = true;
			} else {
				stringBuffer.append(", ");
			}
			
			stringBuffer.append("[" + count + "," + timePoint.getNetworkInbound()+ "]");
			
			count++;
		}
		
		stringBuffer.append("];");
		
		stringBuffer.append("\n varnetout = [");
		
		comma = false;
		count = 0;
		
		for(ResourceTimePoint timePoint: timePoints) {
			
			if (!comma) {
				comma = true;
			} else {
				stringBuffer.append(", ");
			}
			
			stringBuffer.append("[" + count + "," + timePoint.getNetworkOutbound() + "]");
			
			count++;
		}
		
		stringBuffer.append("];");
		
		stringBuffer.append("\n varnettot = [");
		
		comma = false;
		count = 0;
		
		for(ResourceTimePoint timePoint: timePoints) {
			
			if (!comma) {
				comma = true;
			} else {
				stringBuffer.append(", ");
			}
			
			stringBuffer.append("[" + count + "," + 
					(timePoint.getNetworkInbound() + timePoint.getNetworkOutbound()) + "]");
			
			count++;
		}
		
		stringBuffer.append("];");
		
		return stringBuffer.toString();
	}

	public void sessionsPage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		SessionManager sessionManager = RougeServer.getInstance().getSessionManager();

		PrintWriter out = response.getWriter();

		out.println(this.createHeader(request));

		out.println(this.startBox(request, "Sessions"));

		out.println("<ul>");

		for(SessionContext session: sessionManager.getSessions()) {

			if (session.getUser() == null) {
				out.println("<li><a href=\"/secure?action=session&id=" +session.getId() + "\">" + 
						session.getId() + "</a>: </li>");
			} else {
				out.println("<li><a href=\"/secure?action=session&id=" +session.getId() + "\">" + 
						session.getId() + "</a>: " + session.getUser().getUsername() + "</li>");
			}
		}

		out.println("</ul>");

		out.println(this.endBox(request));

		out.println(this.createFooter(request));

	}

	public void sessionPage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		SessionManager sessionManager = RougeServer.getInstance().getSessionManager();
		String id = request.getParameter("id");
		
		SessionContext session = sessionManager.getSession(Integer.parseInt(id));

		PrintWriter out = response.getWriter();

		out.println(this.createHeader(request));

		out.println(this.startBox(request, "Session " + id));

		out.println(this.endBox(request));
		
		out.println(this.startBox(request, "Communication Log"));
		
		out.println("<ul>");

		for(String com: session.getCommunicationLog()) {

				out.println("<li>" + com + ": </li>");
		}

		out.println("</ul>");

		out.println(this.endBox(request));

		out.println(this.createFooter(request));

	}

	public void roomsPage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		RoomManager roomManager = (RoomManager)RougeServer.getInstance().getModule("room.manager");
		Collection<Room> rooms = roomManager.getRooms();

		PrintWriter out = response.getWriter();

		out.println(this.createHeader(request));

		out.println(this.startBox(request, "Rooms"));

		out.println("<ul>");

		for(Room room: rooms) {
			out.println("<li>" + room.getName() + "</li>");

			out.println("<ul>");

			for(User user: room.getPeopleInRoom()) {
				out.println("<li>" + user.getUsername() + "</li>");
			}

			out.println("</ul>");
		}

		out.println("</ul>");

		out.println(this.endBox(request));

		out.println(this.createFooter(request));

	}

	public void usersPage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");
		Collection<User> users = userManager.getUsers();

		PrintWriter out = response.getWriter();

		out.println(this.createHeader(request));

		out.println(this.startBox(request, "Users"));

		out.println("<ul>");

		for(User user: users) {
			out.println("<li>" + user.getUsername() + "</li>");
		}

		out.println("</ul>");

		out.println(this.endBox(request));

		out.println(this.createFooter(request));
	}

	public void logout(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		session.setAttribute("auth", null);

		response.sendRedirect(response.encodeRedirectURL("/"));
	}

	public String startBox(HttpServletRequest request, String header) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("<div class=\"span-24 last\">\n");
		stringBuffer.append("<div class=\"Section\">\n");

		stringBuffer.append("<div class=\"SectionHeading\">\n");
		stringBuffer.append(header);
		stringBuffer.append("</div>\n");

		stringBuffer.append("<div class=\"SectionContent\">\n");

		return stringBuffer.toString();
	}

	public String endBox(HttpServletRequest request) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		return stringBuffer.toString();
	}

	public String createHeader(HttpServletRequest request) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
		stringBuffer.append("<html>\n");
		stringBuffer.append("<head>\n");
		stringBuffer.append("<title>NovaServer Monitoring</title>\n");
		stringBuffer.append("<link rel=\"stylesheet\" href=\"/css/screen.css\" type=\"text/css\" />");
		stringBuffer.append("<link rel=\"stylesheet\" href=\"/css/ie.css\" type=\"text/css\" />");
		stringBuffer.append("<link rel=\"stylesheet\" href=\"/css/nova.css\" type=\"text/css\" />");
		stringBuffer.append("<link rel=\"stylesheet\" href=\"/css/BenevolentDictator.css\" type=\"text/css\" />");	
		stringBuffer.append("<!--[if lte IE 8]><script language=\"javascript\" type=\"text/javascript\" src=\"/js/excanvas.min.js\"></script><![endif]-->");
		stringBuffer.append("<script src=\"/js/jquery-1.4.2.min.js\"></script>");
		stringBuffer.append("<script src=\"/js/jquery.flot.js\"></script>");
		stringBuffer.append("</head>\n");

		stringBuffer.append("<body>\n");
		stringBuffer.append("<div class=\"container\" id=\"BodyContainer\">\n");

		stringBuffer.append("<div class=\"span-24 last\" id=\"HeaderContainer\">");
		stringBuffer.append("<div id=\"Header\">NovaServer Administration");
		stringBuffer.append("<div id=\"HeaderUserMenu\">Logged in as Admin</div>");
		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("<div class=\"span-24 last\" id=\"HeaderContainer\">");
		stringBuffer.append("<div id=\"MainMenu\">");
		stringBuffer.append("<ul id=\"MainMenuList\">\n");
		stringBuffer.append("<li><a href=\"/secure?action=dashboard\">Dashboard</a></li>\n");
		stringBuffer.append("<li><a href=\"/secure?action=sessions\">Session</a></li>\n");
		stringBuffer.append("<li><a href=\"/secure?action=users\">Users</a></li>\n");
		stringBuffer.append("<li><a href=\"/secure?action=rooms\">Rooms</a></li>\n");
		stringBuffer.append("</ul>\n");
		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("<div class=\"span-24 last\" id=\"MainContentContainer\">");
		stringBuffer.append("<div id=\"MainContent\">");

		return stringBuffer.toString();
	}

	public String createFooter(HttpServletRequest request) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("<div class=\"span-24 last\" id=\"PageFooterContainer\">\n");
		stringBuffer.append("<div id=\"Footer\">\n");

		stringBuffer.append("<div class=\"span-12\" id=\"CopyrightStatement\">\n");
		stringBuffer.append("NovaServer &copy 2011 ADInfo\n");
		stringBuffer.append("</div>\n");
		stringBuffer.append("<div class=\"span-12 last\">\n");
		stringBuffer.append("<a href=\"/secure?action=logout\">Logout</a>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("</div>\n");

		stringBuffer.append("</body>\n");
		stringBuffer.append("</html>\n");

		return stringBuffer.toString();
	}

}