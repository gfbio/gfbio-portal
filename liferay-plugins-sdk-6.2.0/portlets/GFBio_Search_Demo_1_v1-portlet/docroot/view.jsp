<%@ page import="javax.portlet.RenderResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%!RenderResponse renderResponse;%>
<portlet:defineObjects />


<script type='text/javascript'>
	var portletAjaxURL = '<portlet:resourceURL id="currentURL" escapeXml="false" />';
</script>
<script src="<%=request.getContextPath()%>/js/main.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery-1.11.0.min.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery-ui.min.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.dataTables.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/js/dataTables.jqueryui.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/js/dataTables.colVis.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery.dataTables.columnFilter.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/js/ColVis.js"
	type="text/javascript"></script>
<!-- <script type="text/javascript" -->
<%-- 	src="<%=request.getContextPath()%>/js/jquery.tristate.js"></script> --%>
<!-- <script type="text/javascript" -->
<%-- 	src="<%=request.getContextPath()%>/js/jquery.updateWithJSON.min.js"></script> --%>
	
	
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/main.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/dataTables.jqueryui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/dataTables.colVis.css">
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="<%=request.getContextPath()%>/css/checktree.css"> --%>

<script type="text/javascript">
	$(document).ready(function() {
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword);
// 		createFacetTree(keyword);
	});

	function getSearchResult(keyword){
		$.ajax({
			"url": portletAjaxURL
			+ "/GFBioSearch",
		"data" : {
			"<portlet:namespace />mode" : "getResult",
				"<portlet:namespace />queryString" : keyword
		},
		"dataSrc" : "dataset",
		"type" : "GET",

        success : function(data) {
            var jsonDataset = eval("(function(){return " + data + ";})()");
            var dataset = jsonDataset.dataset;
            facet = jsonDataset.facet;
            createDatatable(dataset,facet);
			addToolTip();
        }	
		});
	
	}
	
// 	function createFacetTree(keyword){

// 		$.ajax({
// 			"url": portletAjaxURL
// 			+ "/GFBioSearch",
// 		"data" : {
// 			"<portlet:namespace />mode" : "getFacet",
// 			"<portlet:namespace />queryString" : keyword},
// 		"dataSrc" : "dataset",
// 		"type" : "GET",

//         success : function(data) {
//             var jsonDataset = eval("(function(){return " + data + ";})()");
//             var facet = jsonDataset.facet;

//     	    var items = [];
//     	    var i =0;
//     	    $.each(facet, function (id, option) {
//     	    	i= i+1;
//     	    	var listString = '<li>'+
//     			'<input type="checkbox" id="p_'+
//     			i+'" value="'+i+'"/><label>' + 
//     			id + '</label>';
    			
//     			j= i*10;
//     			listString += '<ul>';
//     	        $.each(option, function (id2, option2) {
//     	        	listString+='<li>';
//     	        	listString+='<input type="checkbox" id="p_'+
//     	        				j+'" value="'+j+'"/>'+
//     	        				'<label>'+option2.name+'</label>'; 
//     	        	listString+='</li>';
//     	        	j= j+1;
//     	        });
//     			listString += '</ul>';
    	        
//     	        listString += '</li>';
//     	        items.push(listString);
//     	    });  
    	    
//     		$('#facetUl').append(items.join(''));
//     		$("ul.tristate").tristate();
//         }	
// 		});
		
// 	}
	
	function gfbioQuery(keyword) {
		$('#tableId').dataTable().fnClearTable();
		getSearchResult(keyword);
}
</script>


<div id="search_portlet">
	<label>Search:&nbsp; <input id="gfbioSearchInput" name="gfbioSearchInput"
		class="acInput" value="shark" autocomplete="off"> <input
		id="QueryButton" name="QueryButton" type="button" value="Find Data"
		style="font-weight: bold" onclick="javascript:gfbioQuery(document.getElementById('gfbioSearchInput').value);" /></label>

<br />
<!-- <div id="search_result_facet" class="divleft"> -->
<!-- 		<ul id="facetUl" class="tristate"> -->
<!-- </ul> -->
<!--         <br style="clear:both"/> -->

<!-- </div> -->
<div id="search_result_table" >
		<table style="border: 0; cellpadding: 0; cellspacing: 0;"
				id="tableId" class="display" >
				<thead class="ui-state-default">
					<tr>
						<th>Title</th>
						<th>Author(s)</th>
						<th>Description</th>
						<th>Data Center</th>
						<th>Region</th>
						<th>Project</th>
						<th>Year</th>
						<th>Parameter</th>
						<th>Taxonomy</th>
						<th>Investigator</th>
						<th>Score</th>
						<th>Data Count</th>
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
						<th>Year</th>
						<th>Parameter</th>
						<th>Taxonomy</th>
						<th>Investigator</th>
						<th>Score</th>
						<th>Data Count</th>
					</tr>
				</tfoot>
			</table>
</div>

<div style="clear: both"></div>
</div>