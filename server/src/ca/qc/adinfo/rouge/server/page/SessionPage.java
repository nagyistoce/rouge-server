package ca.qc.adinfo.rouge.server.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.server.core.SessionManager;
import ca.qc.adinfo.rouge.server.servlet.RougePage;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;

public class SessionPage extends RougePage {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		SessionManager sessionManager = RougeServer.getInstance().getSessionManager();
		String id = request.getParameter("id");
		
		SessionContext session = sessionManager.getSession(Integer.parseInt(id));

		PrintWriter out = response.getWriter();

		this.drawHeader(out);
		this.startBox("Sessions", out);
		this.endBox(out);
		
		this.startBox("Communication Log", out);
		this.startList(out);
		
		for(String com: session.getCommunicationLog()) {
			this.listItem(com, out);
		}

		this.endList(out);
		this.endBox(out);
		this.drawFooter(out);
	}

}
