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
window.onload=function() {
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword);

		console.log('tagcloud');
		  Liferay.on(
		            'facetUpdate',
		            function(event) {
						var facet = event.ipcData;
		        	    var items = [];

		        	    var i = 0;
		        	    
		        	    $.each(facet, function (id, option) {
		        	    	var color =  ['#C24641','#FF8040','#ADA96E','#008080','#157DEC','#810541'];//getColor(i,6);   
		        	        var listString='';
		        	    	$.each(option, function (id2, option2) {
		        	        	listString+='<a href="#" rel="'
		        	        	+option2.count+'"><font color="'+color[i]+'">'
		        	        	+option2.name+'</font></a>&nbsp;&nbsp;&nbsp;';
		        	        });
		        	        items.push(listString);

		        	    	 i= i+1;
		        	    });  
		        	    
		        		$('#cloud').append(items.join(''));

		        		$.fn.tagcloud.defaults = {
		        				  size: {start: 10, end: 16, unit: 'pt'}
		        				  //color: {start: '#cde', end: '#f52'}
		        				};
		        		  $('#cloud a').tagcloud();
		            }
		    );
	};

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
	</div>

	<div style="clear: both"></div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"
	type="text/javascript"></script>