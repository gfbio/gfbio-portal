function writeTerminologyResult(d,output) {
//	console.log(d);
	var jsonDataset = eval("(function(){return " + d + ";})()");

	var results = jsonDataset.results;
	var bindings = results.bindings;
	var displaytext = "";
	var i = 0;
	jQuery.each(bindings, function() {
		var graph = "";
		var name = "";
		var uri = "";
		jQuery.each(this, function(i, val) {
			if (i == "graph") {
				graph = val.value;
			} else if (i == "label") {
				name = val.value;
			} else if (i == "uri") {
				uri = val.value;
			}
		});
		// console.log(name +" URI: "+uri);
		if (graph!="") {
			var shortGraph = graph.substring(graph.lastIndexOf("/") + 1);
			displaytext += shortGraph+": ";
		}
		displaytext += "<a href='" + uri + "' target='_newtab'>" + name
				+ "</a></br>";
		i++;
	});
	displaytext += "";
	var div = document.getElementById(output);
	if (displaytext == "") {
		displaytext = "No result.";
	} else {
		displaytext = "Total term(s) : "+ i + "</br>" + displaytext;
	}
	div.innerHTML = "<pre>" + displaytext + "</pre>";
}
function writeTerminologyInfo(d,output) {
//	console.log(d);
	var jsonDataset = eval("(function(){return " + d + ";})()");
	var terminologyInfo = jsonDataset.terminologyInfo;
	var allTerm = jsonDataset.allTerm;
	
	var infotext = "<table>";
	// get info
	var results = terminologyInfo.results;
	var bindings = results.bindings;
	jQuery.each(bindings, function() {
		var attribute = "";
		var value = "";
		jQuery.each(this, function(i, val) {
			if (i == "attribute") {
				attribute = val.value;
			} else if (i == "value") {
				value = val.value;
			} 
		});
		var shortAttr = attribute.substring(attribute.lastIndexOf("/") + 1);
		infotext += "<tr><td>" + shortAttr + ":</td><td>" + value
				+ "</td></tr>";
		i++;
	});
	infotext += "</table>";

	var displaytext = "<table>";
	// get terms
	results = allTerm.results;
	bindings = results.bindings;
	var i = 0;
	jQuery.each(bindings, function() {
		var graph = "";
		var name = "";
		var uri = "";
		jQuery.each(this, function(i, val) {
			if (i == "graph") {
				graph = val.value;
			} else if (i == "label") {
				name = val.value;
			} else if (i == "uri") {
				uri = val.value;
			}
		});
		if (graph!="") {
			var shortGraph = graph.substring(graph.lastIndexOf("/") + 1);
			displaytext += shortGraph+": ";
		}
		displaytext += "<tr><td>" + name
				+ "</td><td><a href='" + uri + "' target='_newtab'>"+uri+"</a></td></tr>";
		i++;
	});
	displaytext += "</table>";
	
	if (i == 0) {
		displaytext = "<br>No result.";
	} else {
		displaytext = "<br>Total term(s) : "+ i + 
		"</br><div style='height:300px; overflow-y:scroll;'>" + displaytext +"</div>";
	}
	var div = document.getElementById(output);
	div.innerHTML = infotext+displaytext;
}

function writeTerminologyMultipleSelection(d) {

	var searchTerminologiesSelect = document.getElementById("searchTerminologiesSelect");
	var options = "<option value='' selected>All</option>";
	var jsonDataset = eval("(function(){return " + d + ";})()");

	var results = jsonDataset.results;
	var bindings = results.bindings;
	jQuery.each(bindings, function() {
		var acronym = "";
		var uri = "";
		jQuery.each(this, function(i, val) {
			if (i == "acronym") {
				acronym = val.value;
			} else if (i == "uri") {
				uri = val.value;
			}
		});
		console.log(acronym + " URI: " + uri);
		var shortAcronym = acronym.substring(acronym.lastIndexOf("/") + 1);
		options += "<option value='" + shortAcronym + "'>" + shortAcronym
				+ "</option>";
	});
	searchTerminologiesSelect.innerHTML = options;
	// $(terminologies).chosen();
}
function writeTerminologySingleSelection(d) {

	var terminologyInfoSelect = document.getElementById("terminologyInfoSelect");
	var options = "";
	var jsonDataset = eval("(function(){return " + d + ";})()");

	var results = jsonDataset.results;
	var bindings = results.bindings;
	jQuery.each(bindings, function() {
		var acronym = "";
		var uri = "";
		jQuery.each(this, function(i, val) {
			if (i == "acronym") {
				acronym = val.value;
			} else if (i == "uri") {
				uri = val.value;
			}
		});
		console.log(acronym + " URI: " + uri);
		var shortAcronym = acronym.substring(acronym.lastIndexOf("/") + 1);
		options += "<option value='" + shortAcronym + "'>" + shortAcronym
				+ "</option>";
	});
	terminologyInfoSelect.innerHTML = options;
}
function clearContext(id){

	var obj = document.getElementById(id);
	obj.innerHTML = "";
}
function getSelectedTerminologies(id){

	var terminologies = document.getElementById(id);
	var term = "";
    for (var i=0;i<terminologies.length;i++){
    	var terminology = terminologies[i];
        if (terminology.selected)
        {
        	if (terminology.text=="All"){
        		term = "";
        		break;
        	}
        	else{
        		if (term!="") term += ",";
    			term += terminology.text;
        	}
        }
    }
    return term;
}