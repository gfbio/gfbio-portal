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
		// use query term from other page.
		var urlReq = window.location.search;
		var startPos = urlReq.indexOf("q_=");
		if (startPos >=0){
			var endPos = urlReq.indexOf("&",startPos+3);
			urlReq = urlReq.substring(startPos+3,endPos);
			console.log("input: "+urlReq);	
			var searchInput = document.getElementById("gfbioSearchInput");
			searchInput.value = urlReq;
		}
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword);

// 		console.log('tagcloud');
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
			 $("#gfbioSearchInput").keyup(function(event){
				    if(event.keyCode == 13){
				        $("#QueryButton").click();
				    }
				});
	};
	 
	function gfbioQuery() {
		$('#tableId').DataTable().clear();
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword);
	}


	function getValueByAttribute(list, attr, val){
	    var result = null;
	    $.each(list, function(index, item){
	        if(item[attr].toString() == val.toString()){
// 	           result = index;
	           result = list[index].value;
	           return false;     // breaks the $.each() loop
	        }
	    });
	    return result;
	}


	function getSearchResult(keyword){
		console.log('getSearchResult');
		writeResultTable();
		var oTable = $('#tableId').dataTable( {
				"bDestroy" : true,
				"bJQueryUI" : true,    
		        "bProcessing": true,
		        "bServerSide": true,
		        "sAjaxSource": "<%=searchURL%>" + "/GFBioSearch",
			    "sAjaxDataProp": "dataset",
			    "type": "POST", 
				"fnServerParams": function ( aoData ) {
					var iDisplayStart = getValueByAttribute(aoData,"name","iDisplayStart");
					var iDisplayLength = getValueByAttribute(aoData,"name","iDisplayLength");
				       aoData.push( { "name": "<portlet:namespace />mode", "value": "getResult" } );
				       aoData.push( { "name": "<portlet:namespace />queryString", "value": keyword} );
				       aoData.push( { "name": "<portlet:namespace />from", "value": iDisplayStart} );
				       aoData.push( { "name": "<portlet:namespace />size", "value": iDisplayLength} );
					 },
					"aoColumns" : [ {
						"data" : "title",
						"sWidth" : "30%",
						"sortable" : false
					}, {
						"data" : "authors",
						"visible" : false,
						"searchable" : true,
						"sortable" : false
					}, {
						"data" : "description",
						"visible" : false,
						"searchable" : true,
						"sortable" : false
					}, {
						"data" : "dataCenter",
						"sWidth" : "25%",
						"searchable" : true,
						"sortable" : false
					}, {
						"data" : "region",
						"visible" : false,
						"searchable" : true,
						"sortable" : false
					}, {
						"data" : "project",
						"sWidth" : "25%",
						"searchable" : true,
						"sortable" : false
					}, {
						"data" : "citationDate",
						"sWidth" : "10%",
						"searchable" : true,
						"sortable" : false
					}, {
						"data" : "parameter",
						"visible" : false,
						"searchable" : true,
						"sortable" : false
					}, {
						"data" : "investigator",
						"visible" : false,
						"sortable" : false
					}, 
					{
						"data" : "score",
						"visible" : false,
						"sortable" : false
					}, 
					{
						"data" : "timeStamp",
						"visible" : false,
						"sortable" : false
					},
					{
						"data" : "dataLink",
						"visible" : false,
						"sortable" : false
					},
					{
						"data" : "metadataLink",
						"visible" : false,
						"sortable" : false
					},
					{
						"data" : "maxLatitude",
						"visible" : false,
						"sortable" : false
					},
					{
						"data" : "minLatitude",
						"visible" : false,
						"sortable" : false
					},
					{
						"data" : "maxLongitude",
						"visible" : false,
						"sortable" : false
					},
					{
						"data" : "minLongitude",
						"visible" : false,
						"sortable" : false
					},
					{
						"class" : "details-control",
						"sortable" : false,
						"data" : null,
						"defaultContent" : ""
					} // last column for +button
					],
					"sDom" : 'Clrtip',
					// display show/hide column button
					colVis : {
						exclude : [ 0,10,17]
					},
					"order" : [ [ 9, "desc" ] ], // ordered by score
					"sAutoWidth" : true,
// 					"sPaginationType" : "full_numbers",

					"fnDrawCallback" : function(oSettings) {
// 						console.log('drawcallback');
						// do nothing if table is empty
					    if (!$(".dataTables_empty")[0]){
							console.log('table draw callback');
							addToolTip();
							addExtraRow();
					    }
					}
		    } );
			setFireSelectedData(oTable);
		    
		$.ajax({
			"url": "<%=searchURL%>"
			+ "/GFBioSearch",
		"data" : {
			"<portlet:namespace />mode" : "getFacet",
			"<portlet:namespace />queryString" : keyword
		},
		"type" : "POST",

        success : function(data) {
            var jsonDataset = eval("(function(){return " + data + ";})()");
			createFacetTree(jsonDataset);
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