package gfbio.portal.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class PangeaeSearch {
	static String region = "";
	static String title = "";
	static String authors = "";
	static String citationDate = "";
	static String investigator = "";
	static String description = "";
	static String dataCenter = "";
	static String project = "";
	static String parameter = "";
//	static String taxonomy = "N/A";
	static int dataCount = 0;
	static String timeStamp ="";
	static String dataLink ="";
	static String metadataLink ="";
	static Double maxLongitude =0.0;
	static Double minLongitude =0.0;
	static Double maxLatitude =0.0;
	static Double minLatitude =0.0;
//	static Document doc;
	static String url = "http://ws.pangaea.de/es/dataportal-gfbio/pansimple/_search";

	public static JSONObject HttpPost(String query) {
		JSONObject ret = null;
		try {
			HttpClient client = new DefaultHttpClient();
			String restURL = url;
			HttpPost post = new HttpPost(restURL);
			StringEntity input;
//			System.out.println(query);
			input = new StringEntity(query);
			post.setEntity(input);
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			String out = "";
			while ((line = rd.readLine()) != null) {
				out += line;
			}
			ret = new JSONObject(out);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	protected static String getValue(JSONObject source, String name) {
		String ret = "";
		try {
			if (source.has(name)) {
				Object obj = source.get(name);
				if (obj instanceof JSONArray) {
					JSONArray array = (JSONArray) obj;
					for (int i = 0; i < array.length(); i++) {
						if (i > 0)
							ret += "; ";
						ret += array.get(i).toString();
					}
				} else {
					ret = (String) obj;
				}
			}
		} catch (JSONException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	protected static int getInt(JSONObject source, String name) {
		int ret = 0;
		try {
			if (source.has(name)) {
				Object obj = source.get(name);
				ret = Integer.parseInt(obj.toString());
			}
		} catch (JSONException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}
	protected static Double getDouble(JSONObject source, String name) {
		Double ret = 0.0;
		try {
			if (source.has(name)) {
				Object obj = source.get(name);
				if (obj instanceof JSONArray) {
					JSONArray array = (JSONArray) obj;
					for (int i = 0; i < array.length(); i++) {
						ret = (Double) array.get(i);
						break;
					}
				} else {
				ret = (Double) obj;
				}
			}
		} catch (JSONException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}
	public static JSONObject parsedResult(JSONObject rawResult) {
		JSONObject ret = new JSONObject();
		try {
			JSONObject hits = rawResult.getJSONObject("hits");
			JSONArray arrayHits = hits.getJSONArray("hits");
			JSONArray arrayResult = new JSONArray();
			dataCount = getInt(hits, "total");
			for (int iHits = 0; iHits < arrayHits.length(); iHits++) {
				JSONObject hit = (JSONObject) arrayHits.get(iHits);
				// String id = hit.getString("_id");
				Double score = hit.getDouble("_score");
				JSONObject source = hit.getJSONObject("fields");
//				JSONObject source = (JSONObject) arrayHits.get(iHits);
				region = getValue(source, "region");
				if (region.trim().isEmpty() || region.trim().length()==0)
					region = "N/A";
				title = getValue(source, "citation.title");
				if (title.trim().isEmpty() || title.trim().length()==0)
					title = "N/A";
				authors = getValue(source, "citation.authors");
				if (authors.trim().isEmpty() || authors.trim().length()==0)
					authors = "N/A";
				citationDate = getValue(source, "citation.date");
				if (citationDate.trim().isEmpty() || citationDate.trim().length()==0)
					citationDate = "N/A";
//				setXMLfromJSON(source, "xml");
				investigator = getValue(source, "investigator");
				if (investigator.trim().isEmpty() || investigator.trim().length()==0)
					investigator = "N/A";
				description = getValue(source, "description");
				if (description.trim().isEmpty() || description.trim().length()==0)
					description = "N/A";
				dataCenter = getValue(source, "dataCenter");
				if (dataCenter.trim().isEmpty() || dataCenter.trim().length()==0)
					dataCenter = "N/A";
				project = getValue(source, "project");
				if (project.trim().isEmpty() || project.trim().length()==0)
					project = "N/A";
				parameter = getValue(source, "parameter");
				if (parameter.trim().isEmpty() || parameter.trim().length()==0)
					parameter = "N/A";
				timeStamp = getValue(source, "internal-datestamp");
				if (timeStamp.trim().isEmpty() || timeStamp.trim().length()==0)
					timeStamp = "N/A";
				dataLink = getValue(source, "datalink");
				if (dataLink.trim().isEmpty() || dataLink.trim().length()==0)
					dataLink = "N/A";
				metadataLink = getValue(source, "metadatalink");
				if (metadataLink.trim().isEmpty() || metadataLink.trim().length()==0)
					metadataLink = "N/A";
				maxLatitude = getDouble(source, "maxLatitude");
				minLatitude = getDouble(source, "minLatitude");
				maxLongitude = getDouble(source, "maxLongitude");
				minLongitude = getDouble(source, "minLongitude");
//				taxonomy = "N/A";
//				if (dataCount.trim().isEmpty() || dataCount.trim().length()==0)
//					dataCount = "N/A";

				JSONObject result = new JSONObject();
				result.put("title", title.trim());
				result.put("authors", authors.trim());
				result.put("description", description.trim());
				result.put("dataCenter", dataCenter.trim());
				result.put("region", region.trim());
				result.put("project", project.trim());
				result.put("citationDate", citationDate.trim());
				result.put("parameter", parameter.trim());
//				result.put("taxonomy", taxonomy.trim());
				result.put("investigator", investigator.trim());
				result.put("score", score);
				result.put("timeStamp", timeStamp.trim());
				result.put("dataLink", dataLink.trim());
				result.put("metadataLink", metadataLink.trim());
				result.put("maxLatitude", maxLatitude);
				result.put("minLatitude", minLatitude);
				result.put("maxLongitude", maxLongitude);
				result.put("minLongitude", minLongitude);
				arrayResult.put(result);
			}
			ret.put("dataset", arrayResult);
			ret.put("recordsFiltered", dataCount);
			ret.put("iTotalRecords", dataCount);
//			ret.put("facet", parseFacet(rawResult));

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	public static JSONObject parseFacet(JSONObject rawResult) {
		JSONObject ret = new JSONObject();
		JSONObject result = new JSONObject();
		try {
			JSONObject facets = rawResult.getJSONObject("facets");
			result.put("datacenter",getFacetTerm("datacenter",facets));
			result.put("region",getFacetTerm("region",facets));
			result.put("project",getFacetTerm("project",facets));
			result.put("parameter",getFacetTerm("parameter",facets));
			result.put("taxonomy",getFacetTerm("taxonomy",facets));
			result.put("investigator",getFacetTerm("investigator",facets));
			ret.put("facet", result);
//			System.out.println(ret);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}
	protected static JSONArray getFacetTerm(String facetName, JSONObject facets){
		JSONArray ret = new JSONArray();
		try {
			JSONObject facet = facets.getJSONObject(facetName);
			JSONArray terms = facet.getJSONArray("terms");
			for (int i=0; i<terms.length(); i++){
				JSONObject term = (JSONObject) terms.get(i);
				String name = term.getString("term");
				String count = term.getString("count");
				JSONObject obj = new JSONObject();
				obj.put("name",name);
				obj.put("count",count);
				ret.put(obj);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}
}
