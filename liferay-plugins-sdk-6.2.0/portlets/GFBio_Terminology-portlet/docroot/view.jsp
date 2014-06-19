<%@ page import="javax.portlet.RenderResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%!RenderResponse renderResponse;%>

<portlet:defineObjects />
<portlet:resourceURL id="terminologyURL" var="terminologyURL" escapeXml="false" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"
	type="text/javascript">
</script>

<script type="text/javascript">
	function terminologyQuery(){
		var keyword = document.getElementById("terminologyInput").value;
		console.log(keyword);
		$.ajax({
			"url": "<%=terminologyURL%>"
			+ "/GFBioTerminology",
			"data" : {
				"<portlet:namespace />mode" : "getResult",
					"<portlet:namespace />queryString" : encodeURI(keyword)
			},
			"dataSrc" : "dataset",
			"type" : "POST",

	        success : function(d) {
// 	        	console.log(d);
	            var jsonDataset = eval("(function(){return " + d + ";})()");
				
	            var results = jsonDataset.results;
	            var bindings = results.bindings;
	            var displaytext = "";
				jQuery.each(bindings, function() {
// 					console.log(this);
					var name = "";
					var uri = "";
		 	        jQuery.each(this, function(i, val) {
		 	        	if (i == "graph"){
// 	 						console.log("     graph: "+val.value);
		 	        	}
		 	        	else if (i == "label"){
// 	 						console.log("     label: "+val.value);
	 						name = val.value;
		 	        	}
		 	        	else if (i == "uri"){
// 	 						console.log("     uri: "+val.value);
	 						uri = val.value;
		 	        	}
					});
		 	        console.log(name +" URI: "+uri);
		 	        displaytext += "<a href='"+uri+"' target='_newtab'>"+name+"</a></br>";
	            });
	            displaytext += "";
	        	var div = document.getElementById('terminology_result');
// 	        	div.innerHTML = "<pre>"+JSON.stringify(jsonDataset, null, "     ")+"</pre>";
	        	div.innerHTML = "<pre>"+displaytext+"</pre>";
	        }
		});
	}
</script>
<div id="terminology_portlet">
	<label>Terminology Search:&nbsp; <input id="terminologyInput"
		name="terminologyInput" value="biomass"> 
	<input id="terminologyButton" name="terminologyButton"
		type="button" value="Find Terminology" style="font-weight: bold"
		onclick="javascript:terminologyQuery();" /></label>
</div>
<div id="terminology_result"></div>