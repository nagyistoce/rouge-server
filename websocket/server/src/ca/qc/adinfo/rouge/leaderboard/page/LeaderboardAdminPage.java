package ca.qc.adinfo.rouge.leaderboard.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.leaderboard.Leaderboard;
import ca.qc.adinfo.rouge.leaderboard.Score;
import ca.qc.adinfo.rouge.leaderboard.db.LeaderboardDb;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.servlet.RougeServerPage;

public class LeaderboardAdminPage extends RougeServerPage {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DBManager dbManager = RougeServer.getInstance().getDbManager();
		
		// Process the input
		
		String key = request.getParameter("key");
		String name = request.getParameter("name");
		boolean ret = true;
		
		if (request.getParameter("key") != null) { 
			
			if (name == null) { name = ""; }
			
			ret = LeaderboardDb.createLeaderboard(dbManager, key, name);			
		}

		// Draw the page
		
		PrintWriter out = response.getWriter();

		HashMap<String, Leaderboard> leaderboards = LeaderboardDb.getLeaderboards(dbManager);
		
		this.drawHeader(out);
		
		for(Leaderboard leaderboard : leaderboards.values()) {
		
			this.startBox(leaderboard.getKey(), out);		
			this.startList(out);
		
				for(Score score: leaderboard.getScore()) {
					this.listItem(score.getScore() + " - " + score.getUser(), out);
				}
		
			this.endList(out);
			this.endBox(out);
		}
		
		this.startBox("New Leaderboard", out);
		
		out.println("<form action=\"\" method=\"GET\">");
		
		if (ret == false) {
			out.println("<p>Could not create leaderboard. Is the key a duplicate?</p>");
		}
				
		out.println("<input type=\"hidden\" name=\"action\" value=\"leaderboards\" /></br>");
		out.println("<label>Key</label><br/><input type=\"text\" name=\"key\" /></br>");
		out.println("<label>Name</label><br/><input type=\"text\" name=\"name\" /></br>");
		out.println("<input type=\"Submit\" /></br>");
		
		out.println("</form>");
		
		this.endBox(out);
		
		this.drawFooter(out);
	}

}
