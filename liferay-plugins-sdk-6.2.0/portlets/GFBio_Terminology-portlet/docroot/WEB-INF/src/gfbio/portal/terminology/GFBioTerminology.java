package gfbio.portal.terminology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.json.JSONObject;

public class GFBioTerminology extends GenericPortlet {
	protected static final String JSP_VIEW = "/view.jsp";
	static String serverurl = "http://terminologies.gfbio.org/api/terminologies/";

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
			String terminologies = request.getParameter("terminologies");
			String matchType = request.getParameter("matchType");
			String firstHit = request.getParameter("firstHit");
//			String format = request.getParameter("format");
			String hierarchy = request.getParameter("hierarchy");
			String termuri = request.getParameter("termuri");
			
			JSONObject json = new JSONObject();
			String query = "";
//			System.out.println(mode);
//			System.out.println(terminologies);
//			System.out.println(hierarchy);
//			System.out.println(termuri);
			if (mode.equals("search")) {
				query = "search?query=" + keyword;
				if (!terminologies.trim().equals("")) {
					query += "&terminologies=" + terminologies;
					if (firstHit.equals("true")) query+="&first_hit=true";
				}
				if (matchType.equals("exact")) query+="&match_type=exact";
				System.out.println(query);
				json = HttpGet(query);
			} else if (mode.equals("getTerminologiesList")) {
				query = "";
				json = HttpGet(query);
			} else if (mode.equals("listAllTerm")) {
				query = terminologies;
				json.put("terminologyInfo", HttpGet(query));
				query = terminologies + "/allterms";
				json.put("allTerm", HttpGet(query));
			} else if (mode.equals("getTermDetail")) {
				query = terminologies+"/term?uri="+termuri;
//				if (format.equals("csv")) query+="&format=csv";
				json.put("termDetail", HttpGet(query));
				query = terminologies+"/narrower?uri="+termuri;

				json.put("relatedTerms", HttpGet(query));
			} else if (mode.equals("getTermRelated")){
				query = terminologies;
				if (hierarchy.equals("narrower")) {
					query+= "/narrower?uri=";
				}else if(hierarchy.equals("allnarrower")) {
					query+= "/allnarrower?uri=";
				}else if(hierarchy.equals("broader")) {
					query+= "/broader?uri=";
				}else {
					query+= "/allbroader?uri=";
				}
				query += termuri;
//				System.out.println(query);
				json = HttpGet(query);
			}
			System.out.println(json);
			PrintWriter writer = response.getWriter();
			writer.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static JSONObject HttpGet(String query) {
		JSONObject ret = null;
		try {
			URL url = new URL(serverurl + query);
			System.out.println(serverurl + query);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			String out = "";
			while ((output = br.readLine()) != null) {
				// System.out.println(output);
				out += output;
			}

			conn.disconnect();
			ret = new JSONObject(out);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}
}
