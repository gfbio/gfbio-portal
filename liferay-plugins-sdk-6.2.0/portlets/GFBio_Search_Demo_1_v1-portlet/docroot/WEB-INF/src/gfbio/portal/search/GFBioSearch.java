package gfbio.portal.search;

import java.io.*;

import javax.portlet.*;

import org.json.JSONObject;
import java.io.IOException;

public class GFBioSearch extends GenericPortlet {
	protected static final String JSP_VIEW = "/view.jsp";
	static String queryString = "{\"size\" : %d,\"query\": {\"simple_query_string\": {\"query\": \"%s\"}}%s}";
	static String facetString = ",\"facets\": "
			+ "{\"datacenter\": {\"terms\": {\"field\": \"dataCenterFacet\",\"size\": 10}},"
			+ "\"region\": {\"terms\": {\"field\": \"regionFacet\",\"size\": 10}},"
			+ "\"project\": {\"terms\": {\"field\": \"projectFacet\",\"size\": 10}},"
			+ "\"parameter\": {\"terms\": {\"field\": \"parameterFacet\",\"size\": 10}},"
			+ "\"taxonomy\": {\"terms\": {\"field\": \"taxonomyFacet\",\"size\": 10}},"
			+ "\"investigator\": {\"terms\": {\"field\": \"investigatorFacet\",\"size\": 10}}}";
	static int maxResult = 100;

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
			String mode = request.getParameter("mode");

			System.out.println(mode);
			String queryJSON = "";
			if (mode.equals("getResult")){
				queryJSON = String.format(queryString, maxResult, keyword,facetString);
			}
			else if(mode.equals("getFacet")){
				queryJSON = String.format(queryString, maxResult, keyword,facetString);
			}
//			System.out.println(queryJSON);
			JSONObject searchResult = PangeaeSearch.HttpPost(queryJSON);
			JSONObject json = new JSONObject();
			System.out.println(searchResult);
			if (mode.equals("getResult")){
				json=	PangeaeSearch.parsedResult(searchResult);
			}
			else if (mode.equals("getFacet")){
				json= PangeaeSearch.parseFacet(searchResult);
			}
			System.out.println(json);
			PrintWriter writer = response.getWriter();
			writer.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
