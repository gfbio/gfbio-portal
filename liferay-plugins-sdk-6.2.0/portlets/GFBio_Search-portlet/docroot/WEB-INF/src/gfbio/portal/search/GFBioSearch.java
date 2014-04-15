package gfbio.portal.search;

import java.net.*;
import java.io.*;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

public class GFBioSearch extends GenericPortlet {
	protected static final String JSP_VIEW = "/view.jsp";

	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		response.setContentType(request.getResponseContentType());
		getPortletContext().getRequestDispatcher(JSP_VIEW).include(request,
				response);
	}

	public static String readURL(String url) throws Exception {
		URL oracle = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				oracle.openStream()));

		String inputLine;
		String out = "";
		while ((inputLine = in.readLine()) != null) {
			// System.out.println(inputLine);
			out += inputLine;
		}
		in.close();
		return out;
	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws PortletException, IOException {

		try {
			response.setContentType("text/html");
			String retStr = "";
			String keyword = request.getParameter("queryString");
			String linkURL = request.getParameter("linkURL");

//			String cxtPath = request.getContextPath();
			if (keyword !=null) {
				String url = "http://www.gfbio.org/data-portal.html?q_="
						+ keyword;
				retStr = readURL(url);

			} else if (linkURL !=null) {
				retStr = readURL(linkURL);
			}

			if (!retStr.isEmpty()) {
				// crop only results part
				int startResult = retStr.indexOf("Search Results:");
				if (startResult >= 0) {
					startResult = retStr.substring(0, startResult).lastIndexOf(
							"<div");
				}
				int endResult = retStr.indexOf("<!--RESULT LIST END-->");
				retStr = retStr.substring(startResult, endResult);

				// change the link to javascript
				retStr = retStr.replaceAll("href=\"data-portal.html",
						"href=\"javascript:getLink('http://www.gfbio.org/data-portal.html");
				int ind = retStr.indexOf("http://www.gfbio.org/");
				while (ind >= 0) {
					ind = retStr.indexOf("\"", ind);
					retStr = replaceFirstFrom(retStr, ind, "\"", "')\"");
					ind = retStr.indexOf("http://www.gfbio.org/", ind);
				}

			}
			// System.out.println(retStr);
			PrintWriter writer = response.getWriter();
			writer.print(retStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String replaceFirstFrom(String str, int from, String regex,
			String replacement) {
		String prefix = str.substring(0, from);
		String rest = str.substring(from);
		rest = rest.replaceFirst(regex, replacement);
		return prefix + rest;
	}
}
