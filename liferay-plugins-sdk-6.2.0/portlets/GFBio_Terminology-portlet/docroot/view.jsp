<%@ page import="javax.portlet.RenderResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%!RenderResponse renderResponse;%>

<portlet:defineObjects />
<portlet:resourceURL id="terminologyURL" var="terminologyURL"
	escapeXml="false" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"
	type="text/javascript">
</script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"
	type="text/javascript">
</script>
<script src="${pageContext.request.contextPath}/js/main.js"
	type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/main.css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/jquery-ui.css"></link>

<script type="text/javascript">
var narrowerHint = "Retrieves the terms that are one level narrower than a given one.";
var allnarrowerHint = "Retrieves all terms that are narrower of a given one including each possible path to the leaves of the hierarchy.";
var broaderHint = "Retrieves the terms that are one level broader than a given one.";
var allbroaderHint = "Retrieves all terms that are broader of a given one including each possible path to the top.";
$(document).ready(function() {
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
	searchReset();
	 
	 $( "#searchOption" ).dialog({
		 autoOpen: false,
		 height:320,
		 width: 350,
		 modal: true,
		 buttons: {
			 "OK": function(){
				 $( this ).dialog( "close" );
			 },
			 "Reset": function(){
				 searchReset();
			 }
		 },
		 close: function() {
			 $( this ).dialog( "close" );
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
					clearContext('termDetail');
					clearContext('relatedTermsScope');
					clearContext('relatedTermsResult');

					writeTerminologyInfo(d,'termInfo_result');
				}
			});
}

function terminologySearch(){
	var searchTerm = document.getElementById("terminologySearchInput").value;
	var term = getSelectedTerminologies("searchTerminologiesSelect");
	var matchTypeSelect = document.getElementById("matchType");
	var matchType = matchTypeSelect.options[matchTypeSelect.selectedIndex].value;
	var firstHitSelect = document.getElementById("firstHit");
	var firstHit = firstHitSelect.options[firstHitSelect.selectedIndex].value;

	$.ajax({
		"url": "<%=terminologyURL%>" + "/GFBioTerminology",
			"data" : {
				"<portlet:namespace />mode" : "search",
				"<portlet:namespace />queryString" : encodeURI(searchTerm),
				"<portlet:namespace />terminologies" : encodeURI(term),
				"<portlet:namespace />matchType" : matchType,
				"<portlet:namespace />firstHit" : firstHit
			},
			"type" : "POST",

			success : function(d) {
				clearContext('termInfo_result');
				clearContext('termDetail');
				clearContext('relatedTermsScope');
				clearContext('relatedTermsResult');
				
				writeTerminologyResult(d,'search_result');
			}
		});
	}
function showTermDetail(uri,terminology){
	$.ajax({
		"url": "<%=terminologyURL%>/GFBioTerminology",
		"data" : {
			"<portlet:namespace />mode" : "getTermDetail",
			"<portlet:namespace />terminologies" : encodeURI(terminology),
			"<portlet:namespace />termuri" : encodeURI(uri)
		},
		"type" : "POST",

		success : function(d) {
// 			console.log("showTermDetail: "+d);
			var jsonDataset = eval("(function(){return " + d
					+ ";})()");
			var termDetail = jsonDataset.termDetail;
			var relatedTerms = jsonDataset.relatedTerms;
			// display term detail
			writeTermsDetail(termDetail);
			// display relatedTerms
			writeScopeButton();
			writeRelatedTermsResult(relatedTerms);
			
			$("#relatedTermsScope").buttonset().change(
					function(e){onScopeTermRelatedChange(e,uri,terminology);});
			    
		}
	});
}
	function onScopeTermRelatedChange(e,termuri,terminology){
		console.log(e.target.id);
		console.log(termuri);
	    var hierarchy = e.target.id;
		$.ajax({
			"url": "<%=terminologyURL%>/GFBioTerminology",
			"data" : {
				"<portlet:namespace />mode" : "getTermRelated",
				"<portlet:namespace />terminologies" : encodeURI(terminology),
				"<portlet:namespace />termuri" : encodeURI(termuri),
				"<portlet:namespace />hierarchy" : hierarchy
			},
			"type" : "POST",

			success : function(d) {
// 				console.log("onScopeTermRelatedChange: "+d);
				var jsonDataset = eval("(function(){return " + d
						+ ";})()");
				writeRelatedTermsResult(jsonDataset);
			}
		});
	}
</script>
<div id="terminology_portlet">
	<label>Terminology Search: <input id="terminologySearchInput"
		value="biomass" /> <input id="terminologyButton" type="button"
		value="Go!" style="font-weight: bold"
		onclick="javascript:terminologySearch();" /> <a
		href="javascript:showSearchOption();"> Option</a></label>
	<div id="searchOption" title="Search Option">
		<table>
			<tr style="vertical-align: top">
				<td>Scope:</td>
				<td><select id="searchTerminologiesSelect" style="width: 100%"
					size="4" multiple="multiple" onchange="excludeFirstOption(this)"
					title="To specify the terminologies to search.">
				</select></td>
			</tr>
			<tr>
				<td>Match type:</td>
				<td><select id="matchType"
					title="Looks for exact and/or partial matches.">
						<option value="included" selected>Included</option>
						<option value="exact">Exact</option>
				</select></td>
			</tr>
			<tr>
				<td>First hit:</td>
				<td><select id="firstHit"
					title="To stop at the first terminology where a match is found.">
						<option value="false" selected>False</option>
						<option value="true">True</option>
				</select></td>
			</tr>
			<!-- 			<tr> -->
			<!-- 				<td colspan="2" style="text-align:center;">Result display</td> -->
			<!-- 			</tr> -->
			<!-- 			<tr><td>Format:</td> -->
			<!-- 				<td><select id="format"> -->
			<!-- 					<option value="json" selected>JSON</option> -->
			<!-- 					<option value="csv">CSV</option></select> -->
			<!-- 				</td> -->
			<!-- 			</tr> -->
			<!-- 			<tr><td>Hierarchy:</td> -->
			<!-- 				<td> -->
			<!-- 					<select id="hierarchy"> -->
			<!-- 					<option value="narrower" selected title="Retrieves the terms that are one level narrower than a given one.">Narrower</option> -->
			<!-- 					<option value="allnarrower" title="Retrieves all terms that are narrower of a given one including each possible path to the leaves of the hierarchy.">All Narrower</option> -->
			<!-- 					<option value="broader" title="Retrieves the terms that are one level broader than a given one.">Broader</option> -->
			<!-- 					<option value="allbroader" title="Retrieves all terms that are broader of a given one including each possible path to the top.">All Broader</option> -->
			<!-- 					</select> -->
			<!-- 				</td> -->
			<!-- 			</tr> -->
		</table>
	</div>
	<div id="search_result"></div>
	<br> <label>Terminology Information: <select
		id="terminologyInfoSelect" style="width: auto">
	</select> <input id="getTerminologyInfoButton" type="button" value="Go!"
		style="font-weight: bold; width: auto;"
		onclick="javascript:listTermInfo();" /></label>

	<div id="termInfo_result"></div>

	<div id="termDetail"></div>
	<br>
	<div id="relatedTermsScope"></div>
	<div id="relatedTermsResult"></div>
</div>