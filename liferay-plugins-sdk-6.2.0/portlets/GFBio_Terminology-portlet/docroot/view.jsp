<%@ page import="javax.portlet.RenderResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%!RenderResponse renderResponse;%>

<portlet:defineObjects />
<portlet:resourceURL id="terminologyURL" var="terminologyURL"
	escapeXml="false" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"
	type="text/javascript">
</script>
<script src="${pageContext.request.contextPath}/js/simple-expand.min.js"
	type="text/javascript">
</script>
<script src="${pageContext.request.contextPath}/js/main.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/main.css">

<script type="text/javascript">

$(document).ready(function() {
	$('.expander').simpleexpand();
	$.ajax({
		"url": "<%=terminologyURL%>"
		+ "/GFBioTerminology",
		"data" : {
			"<portlet:namespace />mode" : "getTerminologiesList"
		},
		"type" : "POST",

        success : function(d) {
			writeTerminologyMultipleSelection(d);
			writeTerminologySingleSelection(d);
        }
	});

});

// show processing mouse while waiting for ajax result
$(document).ajaxStart(function ()
		{
		    $('body').addClass('wait');

		}).ajaxComplete(function () {

		    $('body').removeClass('wait');

		});
		
function listTermInfo(){
	var terminology = getSelectedTerminologies("terminologyInfoSelect");
	$.ajax({
			"url": "<%=terminologyURL%>" + "/GFBioTerminology",
				"data" : {
					"<portlet:namespace />mode" : "listAllTerm",
					"<portlet:namespace />terminologies" : encodeURI(terminology)
				},
				"type" : "POST",

				success : function(d) {
					clearContext('search_result');
					writeTerminologyInfo(d,'termInfo_result');
				}
			});
}

function terminologySearch(){
	var searchTerm = document.getElementById("terminologySearchInput").value;
	var term = getSelectedTerminologies("searchTerminologiesSelect");
	$.ajax({
		"url": "<%=terminologyURL%>" + "/GFBioTerminology",
			"data" : {
				"<portlet:namespace />mode" : "search",
				"<portlet:namespace />queryString" : encodeURI(searchTerm),
				"<portlet:namespace />terminologies" : encodeURI(term)
			},
			"type" : "POST",

			success : function(d) {
				clearContext('termInfo_result');
				writeTerminologyResult(d,'search_result');
			}
		});
	}
</script>
<div id="terminology_portlet">
		<label>Terminology Search:
		<input id="terminologySearchInput" 
					value="biomass" />
		<input id="terminologyButton" 
					type="button" value="Go!" style="font-weight: bold"
					onclick="javascript:terminologySearch();" />
		<a class="expander" href="#"> Option</a></label><div id="searchOption" class="content">
			<table><tr style="vertical-align: top">
			<td>Scope:</td><td>
				<select id="searchTerminologiesSelect" style="width:100%" size="4"
						multiple="multiple">
				</select>
			</td></tr>
			<tr>
			<td>Match type:</td>
				<td><select id="matchType">
					<option value="exact">Exact</option>
					<option value="include">Included</option></select>
				</td>
			</tr>
			<tr><td>First hit:</td>
				<td><select id="firstHit">
					<option value="true">True</option>
					<option value="false">False</option></select>
				</td>
			</tr>
			<tr><td>Hierarchy:</td>
				<td>
					<input id="hierarchySettings1" 
					type="button" value="Settings" style="font-weight: bold; width:100%"
					onclick="javascript:alert('Ta da!');" />
				</td>
			</tr>
			</table>
		</div>
		<div id="search_result"></div>
		<br>
		<label>Terminology Information:
			<select id="terminologyInfoSelect" style="width:auto">
			</select>
			<input id="getTerminologyInfoButton" 
					type="button" value="Go!" style="font-weight: bold; width:auto;"
					onclick="javascript:listTermInfo();" /></label>

		<div id="termInfo_result"></div>
</div>
