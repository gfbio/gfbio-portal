package gfbio.portal.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
	static String taxonomy = "";
	static int dataCount = 0;
	static String timeStamp = "";
	static String dataLink = "";
	static String metadataLink = "";
	static Double maxLongitude = 0.0;
	static Double minLongitude = 0.0;
	static Double maxLatitude = 0.0;
	static Double minLatitude = 0.0;
	// static Document doc;
	static String url = "http://ws.pangaea.de/es/dataportal-gfbio/pansimple/_search";

	public static JSONObject HttpPost(String query) {
		JSONObject ret = null;
		try {
			HttpClient client = new DefaultHttpClient();
			String restURL = url;
			HttpPost post = new HttpPost(restURL);
			System.out.println(query);
			post.setHeader("Content-type", "application/json");
			post.setEntity(new StringEntity(query, StandardCharsets.UTF_8));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), StandardCharsets.UTF_8));
			String line = "";
			String out = "";
			while ((line = rd.readLine()) != null) {
				out += line;
			}

			System.out.println("Output: " + out);
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
				// JSONObject source = (JSONObject) arrayHits.get(iHits);
				region = getValue(source, "region");
				if (region.trim().isEmpty() || region.trim().length() == 0)
					region = "N/A";
				title = getValue(source, "citation.title");
				if (title.trim().isEmpty() || title.trim().length() == 0)
					title = "N/A";
				authors = getValue(source, "citation.authors");
				if (authors.trim().isEmpty() || authors.trim().length() == 0)
					authors = "N/A";
				citationDate = getValue(source, "citation.date");
				if (citationDate.trim().isEmpty()
						|| citationDate.trim().length() == 0)
					citationDate = "N/A";
				// setXMLfromJSON(source, "xml");
				investigator = getValue(source, "investigator");
				if (investigator.trim().isEmpty()
						|| investigator.trim().length() == 0)
					investigator = "N/A";
				description = getValue(source, "description");
				if (description.trim().isEmpty()
						|| description.trim().length() == 0)
					description = "N/A";
				dataCenter = getValue(source, "dataCenter");
				if (dataCenter.trim().isEmpty()
						|| dataCenter.trim().length() == 0)
					dataCenter = "N/A";
				project = getValue(source, "project");
				if (project.trim().isEmpty() || project.trim().length() == 0)
					project = "N/A";
				parameter = getValue(source, "parameter");
				if (parameter.trim().isEmpty()
						|| parameter.trim().length() == 0)
					parameter = "N/A";
				timeStamp = getValue(source, "internal-datestamp");
				if (timeStamp.trim().isEmpty()
						|| timeStamp.trim().length() == 0)
					timeStamp = "N/A";
				dataLink = getValue(source, "datalink");
				if (dataLink.trim().isEmpty() || dataLink.trim().length() == 0)
					dataLink = "N/A";
				metadataLink = getValue(source, "metadatalink");
				if (metadataLink.trim().isEmpty()
						|| metadataLink.trim().length() == 0)
					metadataLink = "N/A";
				taxonomy = getValue(source, "taxonomy");
				if (taxonomy.trim().isEmpty() || taxonomy.trim().length() == 0)
					taxonomy = "N/A";
				maxLatitude = getDouble(source, "maxLatitude");
				minLatitude = getDouble(source, "minLatitude");
				maxLongitude = getDouble(source, "maxLongitude");
				minLongitude = getDouble(source, "minLongitude");

				// if (dataCount.trim().isEmpty() ||
				// dataCount.trim().length()==0)
				// dataCount = "N/A";

				JSONObject result = new JSONObject();
				result.put("title", title.trim());
				result.put("authors", authors.trim());
				result.put("description", description.trim());
				result.put("dataCenter", dataCenter.trim());
				result.put("region", region.trim());
				result.put("project", project.trim());
				result.put("citationDate", citationDate.trim());
				result.put("parameter", parameter.trim());
				// result.put("taxonomy", taxonomy.trim());
				result.put("investigator", investigator.trim());
				result.put("score", score);
				result.put("timeStamp", timeStamp.trim());
				result.put("dataLink", dataLink.trim());
				result.put("taxonomy", taxonomy.trim());
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
			// ret.put("facet", parseFacet(rawResult));

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
			JSONObject facets = rawResult.getJSONObject("aggregations");
			result.put("datacenter", getFacetTerm("datacenter", facets));
			result.put("region", getFacetTerm("region", facets));
			result.put("project", getFacetTerm("project", facets));
			result.put("parameter", getFacetTerm("parameter", facets));
			result.put("taxonomy", getFacetTerm("taxonomy", facets));
			result.put("investigator", getFacetTerm("investigator", facets));
			ret.put("facet", result);
			// System.out.println(ret);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	protected static JSONArray getFacetTerm(String facetName, JSONObject facets) {
		JSONArray ret = new JSONArray();
		try {
			JSONObject facet = facets.getJSONObject(facetName);
			JSONArray terms = facet.getJSONArray("buckets");
			for (int i = 0; i < terms.length(); i++) {
				JSONObject term = (JSONObject) terms.get(i);
				String name = term.getString("key");
				String count = term.getString("doc_count");
				JSONObject obj = new JSONObject();
				obj.put("name", name);
				obj.put("count", count);
				ret.put(obj);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ret;
	}

	public static JSONObject parseFacetFilter(String filter) {
		JSONObject facetQuery = new JSONObject();
		try {
			if (filter == null)	filter = "";
			if (filter != "") {
				boolean datacenterAll = false;
				boolean datacenterOthers = false;
				boolean datacenterNone = true;
				boolean regionAll = false;
				boolean regionOthers = false;
				boolean regionNone = true;
				boolean projectAll = false;
				boolean projectOthers = false;
				boolean projectNone = true;
				boolean parameterAll = false;
				boolean parameterOthers = false;
				boolean parameterNone = true;
				boolean investigatorAll = false;
				boolean investigatorOthers = false;
				boolean investigatorNone = true;
				boolean taxonomyAll = false;
				boolean taxonomyOthers = false;
				boolean taxonomyNone = true;

				String[] selectedList = filter.split(",,");
				for (int i = 0; i < selectedList.length; i++) {
					String id = selectedList[i];
					String[] arrayid = id.split("__");
					// if level 1 is checked, then no filter
					if (arrayid[0].indexOf("l1") == 0) {
						if (arrayid[1].indexOf("datacenter") == 0) {
							datacenterAll = true;
							datacenterNone = false;	
						} else if (arrayid[1].indexOf("region") == 0) {
							regionAll = true;
							regionNone = false;
						} else if (arrayid[1].indexOf("project") == 0) {
							projectAll = true;
							projectNone = false;
						} else if (arrayid[1].indexOf("parameter") == 0) {
							parameterAll = true;
							parameterNone = false;
						} else if (arrayid[1].indexOf("investigator") == 0) {
							investigatorAll = true;
							investigatorNone = false;
						} else if (arrayid[1].indexOf("taxonomy") == 0) {
							taxonomyAll = true;
							taxonomyNone = false;
						}
					}
				}

				// if start with l3, means including other terms
				for (int i = 0; i < selectedList.length; i++) {
					String id = selectedList[i];
					String[] arrayid = id.split("__");
					if(arrayid[0].indexOf("l3") == 0) {
						if (arrayid[1].indexOf("datacenter") == 0) {
							datacenterOthers = true;
							datacenterNone = false;
						} else if (arrayid[1].indexOf("region") == 0) {
							regionOthers = true;
							regionNone = false;
						} else if (arrayid[1].indexOf("project") == 0) {
							projectOthers = true;
							projectNone = false;
						} else if (arrayid[1].indexOf("parameter") == 0) {
							parameterOthers = true;
							parameterNone = false;
						} else if (arrayid[1].indexOf("investigator") == 0) {
							investigatorOthers = true;
							investigatorNone = false;
						} else if (arrayid[1].indexOf("taxonomy") == 0) {
							taxonomyOthers = true;
							taxonomyNone = false;
						}
					}
				}// end for loop
				
				// find the include terms
				JSONArray incArray = new JSONArray();
				for (int i = 0; i < selectedList.length; i++) {
					String id = selectedList[i];
					String[] arrayid = id.split("__");
					if (arrayid[0].indexOf("l2") == 0) {
						// check only if "all" is not selected
						String facetVal = arrayid[3];
						if (arrayid[1].indexOf("datacenter") == 0
								&& (!datacenterAll && !datacenterOthers)) {
							incArray.put(new JSONObject().put("term", 
									new JSONObject().put("dataCenterFacet", facetVal)));
							datacenterNone = false;
						} else if (arrayid[1].indexOf("region") == 0
								&& (!regionAll && !regionOthers)) {
							incArray.put(new JSONObject().put("term", 
									new JSONObject().put("regionFacet", facetVal)));
							regionNone = false;
						} else if (arrayid[1].indexOf("project") == 0
								&& (!projectAll && !projectOthers)) {
							incArray.put(new JSONObject().put("term", 
									new JSONObject().put("projectFacet", facetVal)));
							projectNone = false;
						} else if (arrayid[1].indexOf("parameter") == 0
								&& (!parameterAll && !parameterOthers)) {
							incArray.put(new JSONObject().put("term", 
									new JSONObject().put("parameterFacet", facetVal)));
							parameterNone = false;
						} else if (arrayid[1].indexOf("investigator") == 0
								&& (!investigatorAll && !investigatorOthers)) {
							incArray.put(new JSONObject().put("term", 
									new JSONObject().put("investigatorFacet", facetVal)));
							investigatorNone = false;
						} else if (arrayid[1].indexOf("taxonomy") == 0
								&& (!taxonomyAll && !taxonomyOthers)) {
							incArray.put(new JSONObject().put("term", 
									new JSONObject().put("taxonomyFacet", facetVal)));
							taxonomyNone = false;
						}
					}
				}

				JSONArray excArray = new JSONArray();
				// add bool:must_not option from l4
				for (int i = 0; i < selectedList.length; i++) {
					String id = selectedList[i];
					String[] arrayid = id.split("__");
					JSONObject excObj = new JSONObject();
					if (arrayid[0].indexOf("l4") == 0) {
						String facetVal = arrayid[3];
						
						if (arrayid[1].indexOf("datacenter") == 0 && !datacenterNone) {
							excObj = new JSONObject().put("term", 
									new JSONObject().put("dataCenterFacet", facetVal));
							excArray.put(excObj);
						} else if (arrayid[1].indexOf("region") == 0 && !regionNone) {
							excObj = new JSONObject().put("term", 
									new JSONObject().put("regionFacet", facetVal));
							excArray.put(excObj);
						} else if (arrayid[1].indexOf("project") == 0 && !projectNone) {
							excObj = new JSONObject().put("term", 
									new JSONObject().put("projectFacet", facetVal));
							excArray.put(excObj);
						} else if (arrayid[1].indexOf("parameter") == 0 && !parameterNone) {
							excObj = new JSONObject().put("term", 
									new JSONObject().put("parameterFacet", facetVal));
							excArray.put(excObj);
						} else if (arrayid[1].indexOf("investigator") == 0 && !investigatorNone) {
							excObj = new JSONObject().put("term", 
									new JSONObject().put("investigatorFacet", facetVal));
							excArray.put(excObj);
						} else if (arrayid[1].indexOf("taxonomy") == 0 && !taxonomyNone) {
							excObj = new JSONObject().put("term", 
									new JSONObject().put("taxonomyFacet", facetVal));
							excArray.put(excObj);
						}
					}
				}

				JSONObject boolJSON = new JSONObject();
				if (incArray.length()>0 )boolJSON.put("should", incArray);
				if (excArray.length()>0)boolJSON.put("must_not", excArray);
				if (boolJSON.length()>0) facetQuery.put("bool", boolJSON);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return facetQuery;
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
}
