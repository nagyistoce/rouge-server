package ca.qc.adinfo.rouge.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class RougePage {

	protected static Collection<String> menuLinks = new ArrayList<String>();

	public static void addToMenu(String link) {
		
		menuLinks.add(link);
	}

	public RougePage() {
		super();
	}

	protected void startBox(String header, PrintWriter out) {
		
		StringBuffer stringBuffer = new StringBuffer();
	
		stringBuffer.append("<div class=\"row\">\n");
		stringBuffer.append("<div class=\"span12\">\n");
	
		stringBuffer.append("<h1>\n");
		stringBuffer.append(header);
		stringBuffer.append("</h1>\n");
	
		out.println(stringBuffer.toString());
	}

	protected void endBox(PrintWriter out) {
	
		StringBuffer stringBuffer = new StringBuffer();
	
		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");
	
		out.println(stringBuffer.toString());
	}

	protected void startList(PrintWriter out) {
	
		out.println("<ul>");
	}

	protected void endList(PrintWriter out) {
	
		out.println("</ul>");
	}

	protected void listItem(String item, PrintWriter out) {
	
		out.println("<li>" + item + "</li>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
				
				this.doGet(request, response);
			}

	public abstract void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

}