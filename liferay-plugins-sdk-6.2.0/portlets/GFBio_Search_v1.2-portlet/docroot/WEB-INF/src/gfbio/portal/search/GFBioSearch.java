package gfbio.portal.search;

//import java.io.*;
import java.io.IOException;
import javax.portlet.*;

//import org.json.JSONObject;
public class GFBioSearch extends GenericPortlet {
	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {

		try {
			response.setContentType("text/html");
//			String keyword = request.getParameter("queryString");
//			String mode = request.getParameter("mode");
//			System.out.println("Keyword: " + keyword);
//			String filter = request.getParameter("filter");

			// System.out.println("Filter: "+filter);

//			JSONObject queryJSON = new JSONObject();
//
//			if (mode.equals("getResult")) {
//				String from = request.getParameter("from");
//				String size = request.getParameter("size");
//				queryJSON = PangeaeSearch.createJSONRequest(keyword, from, size, filter);
//
//				// System.out.println(queryJSON);
//
//			} else if (mode.equals("getFacet")) {
//				queryJSON = PangeaeSearch.createJSONFacet();
//			}
//
//			JSONObject searchResult = PangeaeSearch.HttpPost(queryJSON
//					.toString());
//			JSONObject json = new JSONObject();
//
//			// System.out.println(searchResult);
//
//			if (mode.equals("getResult")) {
//				json = PangeaeSearch.parsedResult(searchResult);
//			} else if (mode.equals("getFacet")) {
//				json = PangeaeSearch.parseFacet(searchResult);
//			}
//
//			System.out.println(json);
//			PrintWriter writer = response.getWriter();
//			writer.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		response.setContentType(request.getResponseContentType());
		getPortletContext().getRequestDispatcher(JSP_VIEW).include(request,
				response);
	}

	protected static final String JSP_VIEW = "/view.jsp";

}