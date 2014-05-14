<%@ page import="javax.portlet.RenderResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%!RenderResponse renderResponse;%>
<portlet:defineObjects />


<portlet:resourceURL var="currentURL" id="currentURL" />
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<script src="http://cdn.datatables.net/1.10.0/js/jquery.dataTables.js"
	type="text/javascript"></script>
<script
	src="http://cdn.datatables.net/plug-ins/e9421181788/integration/jqueryui/dataTables.jqueryui.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="http://cdn.datatables.net/1.10.0/css/jquery.dataTables.css">

<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="http://cdn.datatables.net/plug-ins/e9421181788/integration/jqueryui/dataTables.jqueryui.css">

<script type="text/javascript">
	function createDataTable(keyword){

		var oTable = $('#tableId').dataTable( {
			"processing": true,
			"bJQueryUI": true,
			"columns": [
			               {
			            	   "data":"title",
			            	   "sWidth": "30%"},
			                {
			                	"data":"authors",
			                    "visible": false,
			                    "searchable": false
			                },
			                {
			                	"data":"description",
			                    "visible": false,
			                    "searchable": false
			                },{
			                	"data":"dataCenter",
				            	"sWidth": "25%"
			                },
			                {
			                	"data":"region",
			                    "visible": false,
			                    "searchable": false
			                },{
			                	"data":"project",
				            	"sWidth": "25%"
			                },{
			                	"data":"citedDate"
			                },
			                {
			                	"data":"parameter",
			                    "visible": false,
			                    "searchable": false
			                },
			                {
			                	"data":"investigator",
			                    "visible": false,
			                    "searchable": false
			                },
			                {
			                	"data":"taxonomy",
			                    "visible": false,
			                    "searchable": false
			                },
			                {
			                	"data":"score",
			                    "visible": false,
			                    "searchable": false
			                },
			                {
			                	"data":"dataCount"
			                }
			            ],
			"order": [[ 10, "desc" ]],
			"ajax": {
				"url": "<%=renderResponse.encodeURL(currentURL.toString())%>"
					+ "/GFBioSearch",
				"data" : {
						"<portlet:namespace />queryString" : keyword
				},
				"dataSrc" : "dataset",
				"type" : "GET"
			}
		});			

		$('#tableId tbody').on('mouseover','tr', function () {
			var iRow = oTable.fnGetPosition(this); 
			var sCitation = oTable.fnGetData(iRow)[1];
			var sDescription = oTable.fnGetData(iRow)[2];
			
			this.setAttribute( 'title', "Author(s):"+ sCitation +"\nDescription: "+ sDescription);
		});
	    oTable.$('tr').tooltip( {
	        "delay": 0,
	        "track": true,
	        "fade": 250
	    } );
	}
	
	function readData() {
		var keyword = document.getElementById("QueryInput").value;

		jQuery.ajax({
			url : "<%=renderResponse.encodeURL(currentURL.toString())%>"
					+ "/GFBioSearch",
			data : {
				"<portlet:namespace />queryString" : keyword
			},
			type : "POST",
			dataType : "json",
			success : function(retData) {
				$('#tableId').dataTable().fnDestroy();
				createDataTable(keyword);
			}
		});
	}
	$(document).ready(function() {
		var keyword = document.getElementById("QueryInput").value;
		createDataTable(keyword);
	});
</script>


<div>
	<label>Search:&nbsp;</label> <input id="QueryInput" name="QueryInput"
		class="acInput" value="shark" autocomplete="off"> <input
		id="QueryButton" name="QueryButton" type="button" value="Find Data"
		style="font-weight: bold" onclick="javascript:readData();" />
</div>
<br />
<div id="search_result"></div>
<table style="border: 0; cellpadding: 0; cellspacing: 0" id="tableId"
	class="display">
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
</table>