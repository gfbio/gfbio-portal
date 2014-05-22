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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.checktree_yctin.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.updateWithJSON.min.js"></script>
	
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/checktree.css">

<script type="text/javascript">
	$(document).ready(function() {
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword);
		$("ul.tree").checkTree({
			// You can add callbacks to the expand, collapse, check, uncheck, and  halfcheck
			// events of the tree. The element you use as the argument is the LI element of
			// the object that fired the event.
			onExpand: function(el) {
				console.log("expanded ", el.find("label:first").text());
			},
			onCollapse: function(el) {
				console.log("collapsed ", el.find("label:first").text());
			},
			onCheck: function(el) {
				console.log("checked ", el.find("label:first").text());
			},
			onUnCheck: function(el) {
				console.log("un checked ", el.find("label:first").text());
			},
			onHalfCheck: function(el) {
				console.log("half checked ", el.find("label:first").text());
			}
		
			/*
			// You can set the labelAction variable to either "check" or "expand" 
			// to change what happens when you click on the label item.
			// The default is expand, which expands the tree. Check will toggle
			// the checked state of the items.
			labelAction: "expand"
			*/
			
			// You can also change what happens when you hover over a label (over and out)
// 			onLabelHoverOver: function(el) { alert("You hovered over " + el.text()); },
// 			onLabelHoverOut: function(el) { alert("You hovered out of " + el.text()); }
			
		});
	});

	function getSearchResult(keyword){
		$.ajax({
			"url": portletAjaxURL
			+ "/GFBioSearch",
		"data" : {
				"<portlet:namespace />queryString" : keyword
		},
		"dataSrc" : "dataset",
		"type" : "GET",

        success : function(data) {
            var jsonDataset = eval("(function(){return " + data + ";})()");
            var dataset = jsonDataset.dataset;
            var facet = jsonDataset.facet;
            createDatatable(dataset);
			addToolTip();
// 			createFacetTree(facet);
        }	
		});
	
	}
	
	function createFacetTree(facet){
	    var items = [];
	    var i =0;
	    $.each(facet, function (id, option) {
	    	i= i+1;
	    	var listString = '<li>'+
			'<input type="checkbox" name="ids[]" id="p_'+
			i+'" value="'+i+'"><label>' + 
			id + '</label>';
			
			j= i*10;
	        $.each(option, function (id2, option2) {
	        	listString+='<ul><li>';
	        	listString+='<input type="checkbox" name="ids[]" id="p_'+
	        				j+'" value="'+j+'">'+
	        				'<label>'+option2.name+'</label>'; 
	        	listString+='</li></ul>';
	        	j= j+1;
	        });
	        
	        listString += '</li>';
	        items.push(listString);
	    });  
	    
		$('#facetUl').append(items.join(''));
// 		var $checktree = 
			$("#facetUl").checkTree();
// 		$checktree.update();
	}
	
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
<div id="search_result_facet" class="divleft">
	
		<ul id="facetUl" class="tree">
</ul>
        <br style="clear:both"/>
</div><div id="search_result_table" class="divright">
		<table style="border: 0; cellpadding: 0; cellspacing: 0;"
				id="tableId" class="display" >
				<thead>
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