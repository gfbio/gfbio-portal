package gfbio.portal.search;

import java.io.*;

import javax.portlet.*;
import org.json.JSONObject;
import java.io.IOException;

public class GFBioSearch extends GenericPortlet {
	protected static final String JSP_VIEW = "/view.jsp";

	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		response.setContentType(request.getResponseContentType());
		getPortletContext().getRequestDispatcher(JSP_VIEW).include(request,
				response);
	}

	protected JSONObject getSearchResult(String query) {
		JSONObject searchResult = PangeaeSearch.HttpPost(query);
		JSONObject ret = PangeaeSearch.parsedResult(searchResult);
		return ret;
	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws PortletException, IOException {

		try {
			response.setContentType("text/html");
			String keyword = request.getParameter("queryString");
			System.out.println("keyword: " + keyword);

			JSONObject json = getSearchResult(keyword);
			System.out.println(json.toString());
			PrintWriter writer = response.getWriter();
			writer.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
