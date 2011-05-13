package ca.qc.adinfo.rouge.server.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.monitor.ResourceTimePoint;
import ca.qc.adinfo.rouge.room.Room;
import ca.qc.adinfo.rouge.room.RoomManager;
import ca.qc.adinfo.rouge.server.core.SessionManager;
import ca.qc.adinfo.rouge.server.servlet.RougePage;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;

public class DashboardPage extends RougePage {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		SessionManager sessionManager = RougeServer.getInstance().getSessionManager();
		RoomManager roomManager = (RoomManager)RougeServer.getInstance().getModule("room.manager");
		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");

		PrintWriter out = response.getWriter();

		this.drawHeader(out);
		this.startBox("Dashboard", out);

		this.startList(out);
		out.println("<ul>");

		this.listItem("Number of active sessions: " + sessionManager.getSessions().size(), out);
		this.listItem("Number of active users: " + userManager.getUsers().size(), out);
		this.listItem("Number of active rooms: " + roomManager.getRooms().size(), out);

		this.endList(out);

		this.endBox(out);
		
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

		this.drawFooter(out);
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

}
