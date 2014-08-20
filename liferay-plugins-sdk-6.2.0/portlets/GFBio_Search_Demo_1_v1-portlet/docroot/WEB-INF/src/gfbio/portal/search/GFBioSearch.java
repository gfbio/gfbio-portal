package gfbio.portal.search;

import java.io.*;

import javax.portlet.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GFBioSearch extends GenericPortlet {
	protected static final String JSP_VIEW = "/view.jsp";

	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		response.setContentType(request.getResponseContentType());
		getPortletContext().getRequestDispatcher(JSP_VIEW).include(request,
				response);
	}

	public static JSONArray createFieldArray(){
		JSONArray jArr = new JSONArray();
		try {
			jArr.put("_score");
			jArr.put("region");
			jArr.put("investigator");
			jArr.put("citation.date");
			jArr.put("xml");
			jArr.put("format");
			jArr.put("dataCenter");
			jArr.put("type");
			jArr.put("maxLongitude");
			jArr.put("citation.authors");
			jArr.put("project");
			jArr.put("parameter");
			jArr.put("internal-source");
			jArr.put("internal-datestamp");
			jArr.put("datalink");
			jArr.put("metadatalink");
			jArr.put("minLatitude");
			jArr.put("description");
			jArr.put("citation.source");
			jArr.put("citation.title");
			jArr.put("maxLatitude");
			jArr.put("feature");
			jArr.put("minLongitude");
			jArr.put("citation.publisher");
			jArr.put("taxonomy");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jArr;
	}
	
	public static JSONObject createJSONQuery(String keyword){
		JSONObject qJSON = new JSONObject();
		try {
			JSONObject obj = new JSONObject();
			obj.put("query", keyword);
			qJSON.put("simple_query_string",obj);
			} catch (Exception e) {
			e.printStackTrace();
		}
		return qJSON;
	}
	
	public static JSONObject createJSONRequest(String keyword,
			String from, String size, String facet) {
		JSONObject res = new JSONObject();
		try {
			res.put("fields", createFieldArray());
			res.put("query", createJSONQuery(keyword));
			res.put("from", from);
			res.put("size", size);
			JSONObject filter =  PangeaeSearch.parseFacetFilter(facet);
			res.put("filter",filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static JSONObject createJSONFacet(){
		JSONObject res = new JSONObject();
		JSONObject agg = new JSONObject();
		try {
			JSONObject term = new JSONObject();
			term.put("field","dataCenterFacet");
			term.put("size",10);
			agg.put("datacenter", new JSONObject().put("terms",term));
			
			term = new JSONObject();
			term.put("field","regionFacet");
			term.put("size",10);
			agg.put("region", new JSONObject().put("terms",term));
			
			term = new JSONObject();
			term.put("field","projectFacet");
			term.put("size",10);
			agg.put("project", new JSONObject().put("terms",term));
			
			term = new JSONObject();
			term.put("field","parameterFacet");
			term.put("size",10);
			agg.put("parameter", new JSONObject().put("terms",term));
			
			term = new JSONObject();
			term.put("field","taxonomyFacet");
			term.put("size",10);
			agg.put("taxonomy", new JSONObject().put("terms",term));
			
			term = new JSONObject();
			term.put("field","investigatorFacet");
			term.put("size",10);
			agg.put("investigator", new JSONObject().put("terms",term));
			
			res.put("aggs",agg);
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws PortletException, IOException {

		try {
			response.setContentType("text/html");
			String keyword = request.getParameter("queryString");
			String mode = request.getParameter("mode");
			System.out.println("Keyword: " + keyword);
			String filter = request.getParameter("filter");
			// System.out.println("Filter: "+filter);

			JSONObject queryJSON = new JSONObject();
			if (mode.equals("getResult")) {
				String from = request.getParameter("from");
				String size = request.getParameter("size");
				queryJSON = createJSONRequest(keyword, from, size, filter);
				// System.out.println(queryJSON);
			} else if (mode.equals("getFacet")) {
				queryJSON = createJSONFacet();
			}

			JSONObject searchResult = PangeaeSearch.HttpPost(queryJSON
					.toString());
			JSONObject json = new JSONObject();
			// System.out.println(searchResult);
			if (mode.equals("getResult")) {
				json = PangeaeSearch.parsedResult(searchResult);
			} else if (mode.equals("getFacet")) {
				json = PangeaeSearch.parseFacet(searchResult);
			}
			System.out.println(json);
			PrintWriter writer = response.getWriter();
			writer.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
