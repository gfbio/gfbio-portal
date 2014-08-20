<%@ page import="javax.portlet.RenderResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%!RenderResponse renderResponse;%>
<portlet:defineObjects />

<portlet:resourceURL id="searchURL" var="searchURL" escapeXml="false" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/search.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/tree/style.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/spectrum.css" />
<script type="text/javascript">
window.onload=function() {
		// use query term from other page.
		listenToPageRequest();
		var keyword = document.getElementById("gfbioSearchInput").value;
		getSearchResult(keyword,"");

		// call to server for facet data
		$.ajax({
			"url": "<%=searchURL%>" + "/GFBioSearch",
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
		// create tag cloud from facet
		Liferay.on('facetUpdate', function(event) { updateFacet(event);});
		
		listenToEnterPress();
		setAutoComplete();
	};
	
	function getSearchResult(keyword,filter){
		console.log('getSearchResult: '+keyword);
		writeResultTable();
		var oTable = $('#tableId').DataTable( {
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
				       aoData.push( { "name": "<portlet:namespace />filter", "value": filter} );
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
						"class" : "color-control",
						"sortable" : false,
						"data" : null,
						"defaultContent" : "<input type='text' class='full-spectrum'/>"
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
						exclude : [ 0,9,15,16]
					},
				"order" : [ [ 9, "desc" ] ], // ordered by score
				"sAutoWidth" : true,

				"fnDrawCallback" : function(oSettings) {
					// do nothing if table is empty
				    if (!$(".dataTables_empty")[0]){
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

	function createFacetTree(data) {

		var listString = createFacetList(data.facet);

		$("#search_result_facet").jstree("destroy");
		var ul = document.getElementById('search_result_facet');
		ul.innerHTML = listString;

		$('#search_result_facet').jstree({
			"checkbox" : {
				"keep_selected_style" : false,
				"undetermined" : true
			},
			"core" : {
				"themes" : {
					"icons" : false
				}
			},
			"plugins" : [ "checkbox" ]
		});

		checkAllTree(data.facet);

		// broadcast variable to other portlets
		Liferay.fire('facetUpdate', {
			ipcData : data.facet
		});

		$('#search_result_facet').on("changed.jstree", function(e, data) {
	
			var tree = data.instance._model.data;
			var str = data.selected;
			var filterlist = new Array();
			for(var ind =0; ind<str.length; ind++){
				var id = str[ind];
				if (id.indexOf("l1__")==0) filterlist.push(id);
				else{
					for (var node in tree){
						if ((node == id)&&(node.indexOf("l2__")==0)){
							var val = tree[node].li_attr.value;
							filterlist.push(node + "__" +val);
							break;
						}
					}
				}
			}
			var selectedList = filterlist.join(",,");
			getFilterTable(selectedList);
		});

	}

	function getFilterTable(selectedList) {
		// call Ajax
		console.log("Facet List: "+selectedList);
		var facetFilter = document.getElementById("facetFilter");
		facetFilter.value = selectedList;
		gfbioQuery();
	}

	function createFacetList(facet) {
		//TODO: add others option on every facets
		var listString = '<ul id="facetUL">';
		$.each(facet, function(id, option) {
			listString += '<li id="l1__' + id + '">' + id;
			listString += '<ul>';
			var i = 1;
			$.each(option, function(id2, option2) {
				var val = option2.name;
				var count = option2.count;
				listString += '<li id="l2__' + id + '__' + i + '"value="'+val+'">';
				listString += val + ' (' + count + ')</li>';
				i += 1; 
			});
			listString += '</ul>';
			listString += '</li>';
		});
		listString += '</ul>';
		return listString;
	}
</script>

<div id="search_portlet">
	<label>Search:&nbsp; <input id="gfbioSearchInput"
		name="gfbioSearchInput" class="acInput" value="tree"
		autocomplete="off"> <input id="QueryButton" name="QueryButton"
		type="button" value="Find Data" style="font-weight: bold"
		onclick="javascript:gfbioQuery();" /></label> <br />
	<div id="div_facet_outer" class="divleft">
		Facet Filter
		<div id="search_result_facet" class="search-facet"></div>
	</div>
	<div id="search_result_table" class="divright"></div>

	<div style="clear: both"></div>
</div>

<input type="hidden" id="visualBasket" value="">
<input type="hidden" id="facetFilter" value="">

<script src="${pageContext.request.contextPath}/js/main.js"
	type="text/javascript"></script>