package ca.qc.adinfo.rouge.server.servlet;

import java.io.PrintWriter;

public abstract class RougeServerPage extends RougePage {

	public RougeServerPage() {

	}

	public void drawHeader(PrintWriter out) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("<!DOCTYPE html>");
		stringBuffer.append("<html lang=\"en\">");
		stringBuffer.append("  <head>");
		stringBuffer.append("    <meta charset=\"utf-8\">");
		stringBuffer.append("    <title>Bootstrap, from Twitter</title>");
		stringBuffer
				.append("   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		stringBuffer.append("   <meta name=\"description\" content=\"\">");
		stringBuffer.append("        <meta name=\"author\" content=\"\">");

		stringBuffer.append("		    <script src=\"/js/jquery.js\"></script>");
		stringBuffer.append("			<script src=\"/js/jquery.flot.js\"></script>");
		
		stringBuffer.append("        <!-- Le styles -->");
		stringBuffer
				.append("        <link href=\"/css/bootstrap.css\" rel=\"stylesheet\">");
		stringBuffer.append("        <style>");
		stringBuffer.append("          body {");
		stringBuffer
				.append("            padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */");
		stringBuffer.append("          }");
		stringBuffer.append("        </style>");

		stringBuffer.append("    <!-- Fav and touch icons -->");
		stringBuffer
				.append("    <link rel=\"shortcut icon\" href=\"/ico/favicon.ico\">");
		stringBuffer
				.append("    <link rel=\"apple-touch-icon-precomposed\" sizes=\"144x144\" href=\"/ico/apple-touch-icon-144-precomposed.png\">");
		stringBuffer
				.append("    <link rel=\"apple-touch-icon-precomposed\" sizes=\"114x114\" href=\"/ico/apple-touch-icon-114-precomposed.png\">");
		stringBuffer
				.append("   <link rel=\"apple-touch-icon-precomposed\" sizes=\"72x72\" href=\"/ico/apple-touch-icon-72-precomposed.png\">");
		stringBuffer
				.append("    <link rel=\"apple-touch-icon-precomposed\" href=\"/ico/apple-touch-icon-57-precomposed.png\">");
		stringBuffer.append("  </head>");

		stringBuffer.append("  <body>");

		stringBuffer
				.append("    <div class=\"navbar navbar-inverse navbar-fixed-top\">");
		stringBuffer.append("      <div class=\"navbar-inner\">");
		stringBuffer.append("        <div class=\"container\">");
		stringBuffer
				.append("          <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">");
		stringBuffer.append("            <span class=\"icon-bar\"></span>");
		stringBuffer.append("            <span class=\"icon-bar\"></span>");
		stringBuffer.append("            <span class=\"icon-bar\"></span>");
		stringBuffer.append("          </a>");
		stringBuffer
				.append("          <a class=\"brand\" href=\"#\">Rouge Server</a>");
		stringBuffer.append("          <div class=\"nav-collapse collapse\">");
		stringBuffer.append("            <ul class=\"nav\">");

		for (String link : menuLinks) {
			stringBuffer.append("              <li><a href=\"/secure?action="
					+ link + "\">" + link + "</a></li>\n");
		}

		stringBuffer.append("            </ul>");
		stringBuffer.append("          </div><!--/.nav-collapse -->");
		stringBuffer.append("        </div>");
		stringBuffer.append("      </div>");
		stringBuffer.append("    </div>");

		stringBuffer.append("    <div class=\"container\">");

		stringBuffer.toString();

		out.println(stringBuffer.toString());
	}

	public void drawFooter(PrintWriter out) {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("		 </div> <!-- /container -->");

		stringBuffer.append("		    <!-- Le javascript");
		stringBuffer
				.append("		    ================================================== -->");
		stringBuffer
				.append("		    <!-- Placed at the end of the document so the pages load faster -->");

		stringBuffer
				.append("		    <script src=\"/js/bootstrap-transition.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-alert.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-modal.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-dropdown.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-scrollspy.js\"></script>");
		stringBuffer
				.append("		     <script src=\"/js/bootstrap-tab.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-tooltip.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-popover.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-button.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-collapse.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-carousel.js\"></script>");
		stringBuffer
				.append("		    <script src=\"/js/bootstrap-typeahead.js\"></script>");

		stringBuffer.append("  </body>\n");
		stringBuffer.append("</html>\n");

		out.println(stringBuffer.toString());
	}

}
