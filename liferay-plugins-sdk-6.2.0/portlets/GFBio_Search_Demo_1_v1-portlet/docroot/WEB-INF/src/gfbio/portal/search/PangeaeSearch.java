package gfbio.portal.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@SuppressWarnings("deprecation")
public class PangeaeSearch {
	static String region = "";
	static String title = "";
	static String authors = "";
	static String citedDate = "";
	static String investigator = "";
	static String description = "";
	static String dataCenter = "";
	static String project = "";
	static String parameter = "";
	static String taxonomy = "N/A";
	static String dataCount = "";
	static Document doc;
	static String url = "http://ws.pangaea.de/es/dataportal-gfbio/pansimple/_search";
	static String queryString = "{\"size\" : %d,\"query\": {\"simple_query_string\": {\"query\": \"%s\"}},%s}";
	static String facetString = "\"facets\": "
			+ "{\"datacenter\": {\"terms\": {\"field\": \"dataCenterFacet\",\"size\": 10}},"
			+ "\"region\": {\"terms\": {\"field\": \"regionFacet\",\"size\": 10}},"
			+ "\"project\": {\"terms\": {\"field\": \"projectFacet\",\"size\": 10}},"
			+ "\"parameter\": {\"terms\": {\"field\": \"parameterFacet\",\"size\": 10}},"
			+ "\"taxonomy\": {\"terms\": {\"field\": \"taxonomyFacet\",\"size\": 10}},"
			+ "\"investigator\": {\"terms\": {\"field\": \"investigatorFacet\",\"size\": 10}}}";
	static int maxResult = 100;

	public static JSONObject HttpPost(String query) {
		JSONObject ret = null;
		try {
			HttpClient client = new DefaultHttpClient();
			String restURL = url;
			HttpPost post = new HttpPost(restURL);
			StringEntity input;
			String queryJSON = String.format(queryString, maxResult, query,
					facetString);
			System.out.println(queryJSON);
			input = new StringEntity(queryJSON);
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

	protected static String[] getCitation(JSONObject source) {
		String[] ret = new String[3];
		try {
			JSONArray array = null;
			if (source.has("citation")) {
				array = source.getJSONArray("citation");
				if (array.length() > 0) {
					ret[0] = (String) array.get(0);

					String authors = "";
					for (int i = 1; i < array.length() - 3; i++) {
						if (i > 1)
							authors += "; ";
						authors += array.get(i).toString();
					}
					ret[1] = authors;

					ret[2] = (String) array.get(array.length() - 1);
				}
			}
		} catch (JSONException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	protected static String getDescription(JSONObject source) {
		String ret = "";
		try {
			if (doc != null) {
				NodeList nl = doc.getElementsByTagName("dc:description");
				if (nl.getLength() > 0) {
					ret = nl.item(0).getTextContent();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	protected static String getDataCount(JSONObject source) {
		String ret = "";
		try {
			if (doc != null) {
				NodeList nl = doc.getElementsByTagName("dc:format");
				if (nl.getLength() > 0) {
					String format = nl.item(0).getTextContent();
					if (format != "") {
						int start = format.indexOf(",");
						int end = format.indexOf("data points");
						if (start >= 0 && end > start) {
							ret = format.substring(start + 1, end);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;

	}

	protected static void setXMLfromJSON(JSONObject source, String name) {
		try {
			if (source.has(name)) {
				String xml = source.getString("xml");
				doc = getXMLDocument(xml);
			}
		} catch (JSONException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	protected static Document getXMLDocument(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xml)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static JSONObject parsedResult(JSONObject rawResult) {
		JSONObject ret = new JSONObject();
		try {
			JSONObject hits = rawResult.getJSONObject("hits");
			JSONArray arrayHits = hits.getJSONArray("hits");
			JSONArray arrayResult = new JSONArray();
			for (int iHits = 0; iHits < arrayHits.length(); iHits++) {
				JSONObject hit = (JSONObject) arrayHits.get(iHits);
				// String id = hit.getString("_id");
				Double score = hit.getDouble("_score");
				JSONObject source = hit.getJSONObject("_source");

				region = getValue(source, "region");
				String[] citation = getCitation(source);
				title = citation[0];
				if (title.isEmpty())
					title = "N/A";
				authors = citation[1];
				if (authors.isEmpty())
					authors = "N/A";
				citedDate = citation[2];
				if (citedDate.isEmpty())
					citedDate = "N/A";
				setXMLfromJSON(source, "xml");
				investigator = getValue(source, "investigator");
				if (investigator.isEmpty())
					investigator = "N/A";
				description = getDescription(source);
				if (description.isEmpty())
					description = "N/A";
				dataCenter = getValue(source, "dataCenter");
				if (dataCenter.isEmpty())
					dataCenter = "N/A";
				project = getValue(source, "project");
				if (project.isEmpty())
					project = "N/A";
				parameter = getValue(source, "parameter");
				if (parameter.isEmpty())
					parameter = "N/A";
				taxonomy = "N/A";
				dataCount = getDataCount(source);
				if (dataCount.isEmpty())
					dataCount = "N/A";

				JSONObject result = new JSONObject();
				result.put("title", title);
				result.put("authors", authors);
				result.put("description", description);
				result.put("dataCenter", dataCenter);
				result.put("region", region);
				result.put("project", project);
				result.put("citedDate", citedDate);
				result.put("parameter", parameter);
				result.put("taxonomy", taxonomy);
				result.put("investigator", investigator);
				result.put("score", score);
				result.put("dataCount", dataCount);
				arrayResult.put(result);
			}
			ret.put("dataset", arrayResult);
			ret.put("facet", parseFacet(rawResult));

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	public static JSONObject parseFacet(JSONObject rawResult) {
		JSONObject ret = new JSONObject();
		try {
			JSONObject facets = rawResult.getJSONObject("facets");
			ret.put("datacenter",getFacetTerm("datacenter",facets));
			ret.put("region",getFacetTerm("region",facets));
			ret.put("project",getFacetTerm("project",facets));
			ret.put("parameter",getFacetTerm("parameter",facets));
			ret.put("taxonomy",getFacetTerm("taxonomy",facets));
			ret.put("investigator",getFacetTerm("investigator",facets));
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
