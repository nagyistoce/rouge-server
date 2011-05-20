package ca.qc.adinfo.rouge.server.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.qc.adinfo.rouge.server.servlet.RougeServerPage;

public class LogoutPage extends RougeServerPage {


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);

		session.setAttribute("auth", null);

		response.sendRedirect(response.encodeRedirectURL("/"));
	}

}
