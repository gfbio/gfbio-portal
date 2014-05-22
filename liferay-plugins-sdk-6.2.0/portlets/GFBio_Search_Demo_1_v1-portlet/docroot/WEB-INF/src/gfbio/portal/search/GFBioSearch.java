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


	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws PortletException, IOException {

		try {
			response.setContentType("text/html");
			String keyword = request.getParameter("queryString");

			JSONObject searchResult = PangeaeSearch.HttpPost(keyword);
			JSONObject json = PangeaeSearch.parsedResult(searchResult);
			
			PrintWriter writer = response.getWriter();
			writer.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
