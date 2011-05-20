package ca.qc.adinfo.rouge.achievement.page;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.server.servlet.RougeServerPage;

public class AchievementAdminPage extends RougeServerPage {

	// TODO Build Achievement Admin Page
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();

		this.drawHeader(out);
		this.startBox("Achievements", out);
		
		

		this.endBox(out);
		this.drawFooter(out);
	}

}
