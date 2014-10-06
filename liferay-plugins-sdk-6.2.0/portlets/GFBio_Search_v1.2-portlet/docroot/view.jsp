<%@ page import="javax.portlet.RenderResponse" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%!RenderResponse renderResponse; %>
<portlet:defineObjects />

<portlet:resourceURL escapeXml="false" id="searchURL" var="searchURL" />
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath() %>/css/search.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/tree/style.css" />
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/spectrum.css" />
<script type="text/javascript">
var oTable;
// window.onload  ... is a critical bug, because it overwrites all other eventhandlers from page outside portlet
window.onload=function() {
		// use query term from other page.
		listenToPageRequest();
		var keyword = document.getElementById("gfbioSearchInput").value;
		if (keyword.length >0)
			getSearchResult(keyword,"");

		listenToEnterPress();
		setAutoComplete();
	};
	
	function createQueryFieldArray() {
		var jArr = [];
			jArr.push("_score");
			jArr.push("citation.title");
			jArr.push("citation.authors");
			jArr.push("description");
			jArr.push("dataCenter");
			jArr.push("region");
			jArr.push("project");
			jArr.push("citation.date");
			jArr.push("parameter");
			jArr.push("investigator");
			jArr.push("internal-datestamp");
			jArr.push("maxLatitude");
			jArr.push("minLatitude");
			jArr.push("maxLongitude");
			jArr.push("minLongitude");
			jArr.push("taxonomy");
			jArr.push("datalink");
			jArr.push("metadatalink");
// 			jArr.push("xml");
// 			jArr.push("format");
// 			jArr.push("type");
// 			jArr.push("internal-source");
// 			jArr.push("citation.source");
// 			jArr.push("feature");
// 			jArr.push("citation.publisher");
		return jArr;
	}
	function fnServerObjectToArray(keyword){
		return function (sSource, aoData, fnCallback){

				var iDisplayStart = getValueByAttribute(aoData,"name","iDisplayStart");
				var iDisplayLength = getValueByAttribute(aoData,"name","iDisplayLength");

                console.log(iDisplayStart);
                console.log(iDisplayLength);
                var queryfield = createQueryFieldArray();
                console.log(queryfield);
			$.ajax(sSource, {
		        contentType: 'application/json; charset=UTF-8',
		        type: 'POST',
		        data: 
		        	JSON.stringify({
		          'query': {
		            'simple_query_string': {
		            	"query":keyword
		            },
		          },
			      'from':iDisplayStart,
			      'size':iDisplayLength,
			      'fields': queryfield
		        }),
		        dataType: 'json',
		        success: function(json) {
					var datasrc = json.hits.hits;
                    var res = [];
                    for (var i = 0, iLen = datasrc.length; i < iLen; i++) {
                        var inner = new Object();
						var score = datasrc[i]._score;
						var sourceObj = datasrc[i].fields;
                     	inner.score  = score;
                     	inner.title  = sourceObj["citation.title"];
                     	inner.authors = sourceObj["citation.authors"];
                     	inner.description = sourceObj["description"];
                     	inner.dataCenter = sourceObj["dataCenter"];
                     	inner.region = sourceObj["region"];
                     	inner.project = sourceObj["project"];
                     	inner.citationDate = sourceObj["citation.date"];
                     	inner.parameter = sourceObj["parameter"];
                     	inner.investigator = sourceObj["investigator"];
                     	inner.timeStamp = sourceObj["internal-datestamp"][0];
                     	inner.maxLatitude = sourceObj["maxLatitude"][0];
                     	inner.minLatitude = sourceObj["minLatitude"][0];
                     	inner.maxLongitude = sourceObj["maxLongitude"][0];
                     	inner.minLongitude = sourceObj["minLongitude"][0];
                     	inner.taxonomy = sourceObj["taxonomy"];
                     	inner.dataLink = sourceObj["datalink"];
                     	inner.metadataLink = sourceObj["metadatalink"][0];
                       	res.push(inner);
                    }
                    json.iTotalRecords = json.hits.total;
                    json.iTotalDisplayRecords = json.hits.total;
                    json.data = res;
//                     console.log(resObj);
                    fnCallback(json);
		        }
			});
		};
	};
	function getSearchResult(keyword,filter) {
		console.log('getSearchResult: '+keyword);
		writeResultTable();
		var oTable = $('#tableId').DataTable( {
	    				"bDestroy" : true,
	    				"bJQueryUI" : true,
	    		        "bProcessing": true,
	    		        "bServerSide": true,
	     				"sAjaxSource":'http://ws.pangaea.de/es/dataportal-gfbio/pansimple/_search',
	    				"bRetrieve": true,
	    				"fnServerData": fnServerObjectToArray(keyword), 
  					 
	     				"aoColumns" : [ 
	     				    {
	    						"data" : "score",
	    						"visible" : false,
	    						"sortable" : false
	    					},
	    					{
	    						"data" : "title",
	     						"sortable" : false,
	     					}, 
	     					{
	     						"class" : "color-control",
	     						"sortable" : false,
	     						"data" : null,
	     						"defaultContent" : "<input type='text' class='full-spectrum'/>",
	     					},
	     					{	// last column for +button
	     						"class" : "details-control",
	     						"sortable" : false,
	     						"data" : null,
	     						"defaultContent" : "",
	     					} 
	     					],
	    				"sDom" : 'lrtip',
	    				"order" : [ [ 0, "desc" ] ], // ordered by score
	    				"sAutoWidth" : true,

	     				"fnDrawCallback" : function(oSettings) {
	     					// do nothing if table is empty
	     				    if (!$(".dataTables_empty")[0]) {
	     						console.log('table draw callback');
	     						addToolTip();
	     						addExtraRow();
	     						addColorPicker();
	     						setSelectedRowStyle();
	     				    }
	     				}
	    		    } );
	    		onRowClick();
	}
	
// 	function getFilterTable(selectedList) {
// 		// clear visualBasket
// 		var visualBasket = document.getElementById("visualBasket");
// 		visualBasket.value = "";
// 		updateVisualisation();
// 		// call Ajax
// 		var facetFilter = document.getElementById("facetFilter");
// 		facetFilter.value = selectedList;
// // 		console.log("getFilterTable: " + selectedList);
// 		gfbioQuery();
// 	}

</script>

<div id="search_portlet">
	<label>Search:&nbsp; <input id="gfbioSearchInput"
		name="gfbioSearchInput" class="acInput" value=""
		autocomplete="off"> <input id="QueryButton" name="QueryButton"
		type="button" value="Find Data" style="font-weight: bold"
		onclick="javascript:gfbioQuery();" /></label> <br />
<!-- 	<div class="divleft" id="div_facet_outer"> -->
<!-- 		Facet Filter -->
<!-- 		<div class="search-facet" id="search_result_facet"></div> -->
<!-- 	</div> -->
	<div id="search_result_table"></div>

<!-- 	<div style="clear: both"></div> -->
</div>

<input id="visualBasket" type="hidden" value="">
<!-- <input id="facetFilter" type="hidden" value=""> -->
<!-- <div id="facetDialog" title="Facet Filter:"></div> -->
<script src="${pageContext.request.contextPath}/js/main.js"
	type="text/javascript"></script>
