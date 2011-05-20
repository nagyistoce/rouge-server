package ca.qc.adinfo.rouge.user.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.server.servlet.RougeServerPage;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;

public class UsersPage extends RougeServerPage {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");
		Collection<User> users = userManager.getUsers();

		PrintWriter out = response.getWriter();

		this.drawHeader(out);
		this.startBox("Users", out);
		
		this.startList(out);

		for(User user: users) {
			this.listItem(user.getUsername(), out);
		}

		this.endList(out);

		this.endBox(out);
		this.drawFooter(out);
	}

}
