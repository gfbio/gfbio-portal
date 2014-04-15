
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<portlet:resourceURL var="currentURL" id="currentURL" />

<script src="http://jqueryjs.googlecode.com/files/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function readData() {
		var keyword = document.getElementById("QueryInput").value;

		jQuery.ajax({
			url : "<%=renderResponse.encodeURL(currentURL.toString())%>" + "/GFBioSearch",
			data : {
				"<portlet:namespace />queryString" : keyword
			},
			type : "POST",
			dataType : "text",
			success : function(retData) {
				document.getElementById("search_result").innerHTML = retData;
			}
		});
	}
	function getLink( linkURL) {
		jQuery.ajax({
			url : "<%=renderResponse.encodeURL(currentURL.toString())%>"
					+ "/GFBioSearch",
			data : {
				"<portlet:namespace />linkURL" : linkURL
			},
			type : "POST",
			dataType : "text",
			success : function(retData) {
				document.getElementById("search_result").innerHTML = retData;
			}
		});

	}
	$(document).ready(function(){
		$("#QueryInput").keypress(function(e) {
			if (e.keyCode == 13) {
				$("#QueryButton").click();
			}
		});
	});
</script>


<div>
	<label>Search:&nbsp;</label> <input id="QueryInput" name="QueryInput"
		class="acInput" value="shark" autocomplete="off"> <input
		id="QueryButton" name="QueryButton" type="button" value="Find Data"
		style="font-weight: bold" onclick="javascript:readData();" />
</div>
<div id="search_result"></div>
