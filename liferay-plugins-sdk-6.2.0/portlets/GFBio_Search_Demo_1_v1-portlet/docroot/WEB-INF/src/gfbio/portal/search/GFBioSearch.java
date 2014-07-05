package gfbio.portal.search;

import java.io.*;

import javax.portlet.*;

import org.json.JSONObject;
import java.io.IOException;

public class GFBioSearch extends GenericPortlet {
	protected static final String JSP_VIEW = "/view.jsp";
//	static String queryString = "{\"size\" : %d,\"query\": {\"simple_query_string\": {\"query\": \"%s\"}}%s}";
	static String queryString = "{\"fields\" : [\"_score\",\"region\",\"investigator\",\"citation.date\","
			+"\"xml\",\"format\",\"dataCenter\",\"type\",\"maxLongitude\",\"citation.authors\","
			+"\"project\",\"parameter\",\"internal-source\",\"internal-datestamp\",\"datalink\",\"metadatalink\",\"minLatitude\","
			+"\"description\",\"citation.source\",\"citation.title\",\"maxLatitude\",\"feature\",\"minLongitude\",\"citation.publisher\"]"
			+",\"query\": {\"simple_query_string\": {\"query\": \"%s\"}},\"from\":%s,\"size\":%s}";
	static String facetString = "{\"facets\": {"
			+ "\"datacenter\": {\"terms\": {\"field\": \"dataCenterFacet\",\"size\": 10}},"
			+ "\"region\": {\"terms\": {\"field\": \"regionFacet\",\"size\": 10}},"
			+ "\"project\": {\"terms\": {\"field\": \"projectFacet\",\"size\": 10}},"
			+ "\"parameter\": {\"terms\": {\"field\": \"parameterFacet\",\"size\": 10}},"
			+ "\"taxonomy\": {\"terms\": {\"field\": \"taxonomyFacet\",\"size\": 10}},"
			+ "\"investigator\": {\"terms\": {\"field\": \"investigatorFacet\",\"size\": 10}}}}";
//	static int maxResult = 50;

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
//			System.out.println(request.getAttributeNames().toString());

			String queryJSON = "";
			if (mode.equals("getResult")){
//				queryJSON = String.format(queryString, maxResult, keyword,facetString);
				String from = request.getParameter("from");
				String size = request.getParameter("size");
				queryJSON = String.format(queryString, keyword,from,size);
//				System.out.println(queryJSON);
			}
			else if(mode.equals("getFacet")){
				queryJSON = facetString;
			}

			JSONObject searchResult = PangeaeSearch.HttpPost(queryJSON);
			JSONObject json = new JSONObject();
//			System.out.println(searchResult);
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
			e.printStackTrace();
		}
	}

}
