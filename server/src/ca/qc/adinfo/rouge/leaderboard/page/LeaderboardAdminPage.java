package ca.qc.adinfo.rouge.leaderboard.page;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.server.servlet.RougeServerPage;

public class LeaderboardAdminPage extends RougeServerPage {

	// TODO Build LeaderboardAdmin Page
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//LeaderboardManager leaderboardManager = (LeaderboardManager)RougeServer.getInstance().getModule("leaderboard.manager");

		PrintWriter out = response.getWriter();

		this.drawHeader(out);
		this.startBox("Leaderboard", out);
		
		

		this.endBox(out);
		this.drawFooter(out);
	}

}
