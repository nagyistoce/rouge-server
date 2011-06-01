package ca.qc.adinfo.rouge.achievement.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.achievement.Achievement;
import ca.qc.adinfo.rouge.achievement.db.AchievementDb;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.servlet.RougeServerPage;

public class AchievementAdminPage extends RougeServerPage {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		// Process the input
		
		String key = request.getParameter("key");
		String name = request.getParameter("name");
		String pointValue = request.getParameter("point");
		String total = request.getParameter("total");
		String errMsg = "";
		
		boolean ret = true;
		
		if (request.getParameter("key") != null) { 
			
			if (name == null) { name = ""; }
			
			int iPointValue = 0;
			double dTotal = 0;
			
			try {
				iPointValue = Integer.parseInt(pointValue);
				dTotal = Double.parseDouble(total);
			} catch (Exception e) {
				errMsg = "Error : Total and Point Value must be numeric.";
			}
			
			if (errMsg.equals("")) {
				ret = AchievementDb.createAchievement(dbManager, key, name, iPointValue, dTotal);
			
				if(!ret) {
					errMsg = "Error: Unable to add achievement, most likely because of a duplicate key.";
				}
			}
		}

		// Draw the page
		
		PrintWriter out = response.getWriter();

		HashMap<String, Achievement> achievements = AchievementDb.getAchievementList(dbManager);
		
		this.drawHeader(out);
		
		this.startBox("Achievements", out);		
		this.startList(out);
		
		for(Achievement achievement : achievements.values()) {

			this.listItem(achievement.getKey() + " : " + achievement.getName() + 
					" - Total: " + achievement.getTotal() + " - Point Value: " +
					achievement.getPointValue() , out);
		}
		
		this.endList(out);
		this.endBox(out);
		
		this.startBox("New Achievement", out);
		
		out.println("<form action=\"\" method=\"GET\">");
		
		if (!errMsg.equals("")) {
			out.println("<p>" + errMsg + "</p>");
		}
				
		out.println("<input type=\"hidden\" name=\"action\" value=\"achievements\" /></br>");
		out.println("<label>Key</label><br/><input type=\"text\" name=\"key\" /></br>");
		out.println("<label>Name</label><br/><input type=\"text\" name=\"name\" /></br>");
		out.println("<label>Point Value</label><br/><input type=\"text\" name=\"point\" /></br>");
		out.println("<label>Total</label><br/><input type=\"text\" name=\"total\" /></br>");
		
		out.println("<input type=\"Submit\" /></br>");
		
		out.println("</form>");
		
		this.endBox(out);
		
		this.drawFooter(out);
	}

}
