package ca.qc.adinfo.rouge.server.servlet;

import java.io.PrintWriter;


public abstract class RougeClientPage extends RougePage {
	
	public RougeClientPage() {
		
		
	}
	
	public void drawHeader( PrintWriter out) {
		
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
		stringBuffer.append("<html>\n");
		stringBuffer.append("<head>\n");
		stringBuffer.append("<title>Rouge Server Monitoring</title>\n");
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
		stringBuffer.append("<div id=\"Header\">Rouge Server Administration");
		stringBuffer.append("<div id=\"HeaderUserMenu\">Logged in as Admin</div>");
		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("<div class=\"span-24 last\" id=\"HeaderContainer\">");
		stringBuffer.append("<div id=\"MainMenu\">");
		stringBuffer.append("<ul id=\"MainMenuList\">\n");
		
		for(String link: menuLinks) {
			stringBuffer.append("<li><a href=\"/secure?action=" + link + "\">" + link + "</a></li>\n");
		}
		
		stringBuffer.append("</ul>\n");
		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("<div class=\"span-24 last\" id=\"MainContentContainer\">");
		stringBuffer.append("<div id=\"MainContent\">");

		stringBuffer.toString();
		
		out.println(stringBuffer.toString());
	}
	
	public void drawFooter(PrintWriter out) {
	
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("<div class=\"span-24 last\" id=\"PageFooterContainer\">\n");
		stringBuffer.append("<div id=\"Footer\">\n");

		stringBuffer.append("<div class=\"span-12\" id=\"CopyrightStatement\">\n");
		stringBuffer.append("RougeServer &copy 2011 ADInfo\n");
		stringBuffer.append("</div>\n");
		stringBuffer.append("<div class=\"span-12 last\">\n");
		stringBuffer.append("<a href=\"/secure?action=logoff\">Logout</a>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("</div>\n");
		stringBuffer.append("</div>\n");

		stringBuffer.append("</div>\n");

		stringBuffer.append("</body>\n");
		stringBuffer.append("</html>\n");
		
		out.println(stringBuffer.toString());
	}

	
}
