<%@ page import="javax.portlet.RenderResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%!RenderResponse renderResponse;%>
<portlet:defineObjects />

<portlet:resourceURL id="searchURL" var="searchURL" escapeXml="false" />

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/search.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/tree/style.css" />

<script type="text/javascript">
	$(document).ready(function() {
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword);
	});

	function gfbioQuery() {
		$('#tableId').DataTable().clear();
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword);
	}
	
	function getSearchResult(keyword){
		$.ajax({
			"url": "<%=searchURL%>"
			+ "/GFBioSearch",
		"data" : {
			"<portlet:namespace />mode" : "getResult",
				"<portlet:namespace />queryString" : keyword
		},
		"dataSrc" : "dataset",
		"type" : "POST",

        success : function(data) {
            var jsonDataset = eval("(function(){return " + data + ";})()");
            var dataset = jsonDataset.dataset;
            facet = jsonDataset.facet;
            createDatatable(dataset,facet);
			createFacetTree(facet);
        }	
		});
	}


</script>


<div id="search_portlet">
	<label>Search:&nbsp; <input id="gfbioSearchInput"
		name="gfbioSearchInput" class="acInput" value="tree"
		autocomplete="off"> <input id="QueryButton" name="QueryButton"
		type="button" value="Find Data" style="font-weight: bold"
		onclick="javascript:gfbioQuery();" /></label>

	<br />
	<div id="div_facet_outer" class="divleft">
		Facet Filter
		<div id="search_result_facet">
		</div>
	</div>
	<div id="search_result_table" class="divright">
		<table style="border: 0; cellpadding: 0; cellspacing: 0;" id="tableId"
			class="display">
			<thead class="ui-state-default">
				<tr>
					<th>Title</th>
					<th>Author(s)</th>
					<th>Description</th>
					<th>Data Center</th>
					<th>Region</th>
					<th>Project</th>
					<th>Citation Date</th>
					<th>Parameter</th>
					<th>Investigator</th>
					<th>Score</th>
					<th>Timestamp</th>
					<th>Data Link</th>
					<th>Metadata Link</th>
					<th>Max Latitude</th>
					<th>Min Latitude</th>
					<th>Max Longitude</th>
					<th>Min Longitude</th>
					<th></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Title</th>
					<th>Author(s)</th>
					<th>Description</th>
					<th>Data Center</th>
					<th>Region</th>
					<th>Project</th>
					<th>Citation Date</th>
					<th>Parameter</th>
					<th>Investigator</th>
					<th>Score</th>
					<th>Timestamp</th>
					<th>Data Link</th>
					<th>Metadata Link</th>
					<th>Max Latitude</th>
					<th>Min Latitude</th>
					<th>Max Longitude</th>
					<th>Min Longitude</th>
					<th></th>
				</tr>
			</tfoot>
		</table>
		<input id="pubSelectedData" name="pubSelectedData"
		type="button" value="Publish selected data" style="font-weight: bold; width:100%"/>
	</div>

	<div style="clear: both"></div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"
	type="text/javascript"></script>