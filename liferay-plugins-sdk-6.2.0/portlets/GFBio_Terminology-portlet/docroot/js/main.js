function writeTerminologyResult(d,output) {
	var jsonDataset = eval("(function(){return " + d + ";})()");

	var results = jsonDataset.results;
	var bindings = results.bindings;
	var displaytext = "<table>";
	var i = 0;
	// loop for each result
	jQuery.each(bindings, function() {
		var graph = this.graph.value;
		var name = this.label.value;
		var uri = this.uri.value;

		var terminology = "";
		displaytext +="<tr><td>";
		if (graph!="") {
			var shortGraph = graph.substring(graph.lastIndexOf("/") + 1);
			displaytext += shortGraph+": ";
			terminology = shortGraph;
		}
		displaytext +="</td><td>";
		displaytext += "<a href='javascript:showTermDetail(\""+
				uri+"\",\""+terminology+"\")'>" + name
				+ "</a></td>";
		displaytext += "<td><a href='"+uri+"' target='_blank'>"+uri+"</a></td>";
		displaytext += "</tr>";
		i++;
	});
	displaytext += "</table>";
	
	// show total number of result(s)
	if (i == 0) {
		displaytext = "No result.";
	} else {
		displaytext = "Total term(s) : "+ i + "</br>" + displaytext;
	}
	var div = document.getElementById(output);
	div.innerHTML = "<pre><div style='height:300px; overflow-y:scroll;'>" + displaytext + "</div></pre>";
}
function writeTerminologyInfo(d,output) {
	var jsonDataset = eval("(function(){return " + d + ";})()");
	var terminologyInfo = jsonDataset.terminologyInfo;
	var allTerm = jsonDataset.allTerm;
	
	var infotext = "<table style='display: block; max-height: 150px; overflow-y: scroll'>";
	// get info
	var results = terminologyInfo.results;
	var bindings = results.bindings;
	jQuery.each(bindings, function() {
		var attribute = this.attribute.value;
		var value = this.value.value;
		var atttype =this.attribute.type;
		var valtype =this.value.type;

		var shortAttr = attribute.substring(attribute.lastIndexOf("/") + 1);
		if (atttype == "uri"){
		infotext += "<tr><td><a href='"+attribute+"' target='_blank'>" + shortAttr 
				+ ":</a></td>";}
		else {
			infotext += "<tr><td>" + shortAttr 
			+ ":</td>";
		}
		if (valtype == "uri"){
			infotext += "<td><a href='"+value+"' target='_blank'>" + value
			+ "</a></td></tr>";
		}
		else {
			infotext += "<td>" + value
			+ "</td></tr>";
		}
	});
	infotext += "</table>";

	var displaytext = writeAllTerm(allTerm);
	var div = document.getElementById(output);
	div.innerHTML = infotext+displaytext;
}
function writeAllTerm(allTerm){

	var terminology = getSelectedTerminologies('terminologyInfoSelect');
	var displaytext = "<table>";
	// get terms
	var results = allTerm.results;
	var bindings = results.bindings;
	var i = 0;
	jQuery.each(bindings, function() {
		var name = this.label.value;
		var uri = this.uri.value;

		displaytext += "<tr><td>" + name
				+ "</td><td><a href='javascript:showTermDetail(\""+
				uri+"\",\""+terminology+"\")'>"+
				uri+"</a></td></tr>";
		i++;
	});
	displaytext += "</table>";

	if (i == 0) {
		displaytext = "<br>No result.";
	} else {
		displaytext = "<br>Total term(s) : "+ i + 
		"</br><pre><div style='height:200px; overflow-y:scroll;'>" + displaytext +"</div></pre>";
	}
	return displaytext;
}

function writeTerminologyMultipleSelection(d) {

	var searchTerminologiesSelect = document.getElementById("searchTerminologiesSelect");
	var options = "<option value='' selected>All</option>";
	var jsonDataset = eval("(function(){return " + d + ";})()");

	var results = jsonDataset.results;
	var bindings = results.bindings;
	jQuery.each(bindings, function() {
		var acronym = this.acronym.value;
		var uri = this.uri.value;
		var shortAcronym = acronym.substring(acronym.lastIndexOf("/") + 1);
		options += "<option value='" + shortAcronym + "'>" + shortAcronym
				+ "</option>";
	});
	searchTerminologiesSelect.innerHTML = options;
}
function writeTerminologySingleSelection(d) {

	var terminologyInfoSelect = document.getElementById("terminologyInfoSelect");
	var options = "";
	var jsonDataset = eval("(function(){return " + d + ";})()");

	var results = jsonDataset.results;
	var bindings = results.bindings;
	jQuery.each(bindings, function() {
		var acronym = this.acronym.value;
		var uri = this.uri.value;
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

function showSearchOption(){
	$("#searchOption").dialog("open");
}

function excludeFirstOption(select) {

    if (select.selectedIndex === 0) {
        for (var i=0; i<select.options.length; i++) {
            select.options[i].selected = false;
        }
    }
}
function searchReset(){

	 $('#searchTerminologiesSelect').val('All');
	 $('#matchType').val('included');
	 $('#firstHit').val('false');
	 $('#format').val('json');
	 $('#hierarchy').val('narrower');
}


function writeScopeButton(){
	$('#relatedTermsScope').off('change');
	clearContext('relatedTermsScope');
	var displaytext = "<input type='radio' id='narrower' name='relatedTermsScope' checked='checked'/>"+
					"<label for='narrower'"+
					" title='"+narrowerHint+"'"+
					">Narrower</label>";
	displaytext += "<input type='radio' id='allnarrower' name='relatedTermsScope'/>"+
					"<label for='allnarrower'"+
					" title='"+allnarrowerHint+"'"+
					">All Narrower</label>";
	displaytext += "<input type='radio' id='broader' name='relatedTermsScope'/>"+
					"<label for='broader'"+
					" title='"+broaderHint+"'"+
					">Broader</label>";
	displaytext += "<input type='radio' id='allbroader' name='relatedTermsScope'/>"+
					"<label for='allbroader'"+
					" title='"+allbroaderHint+"'"+
					">All Broader</label>";

	var div = document.getElementById('relatedTermsScope');
	div.innerHTML = displaytext;
}
function writeRelatedTermsResult(relatedTerms) {
//	console.log("writeRelatedTermsResult: ");
	var displaytext = "<table id='relatedTermsResult'>";
	var i = 0;
	jQuery.each(relatedTerms.results.bindings, function() {
		var label = "";//this.narrowerlabel.value;
		var uri = "";//this.narroweruri.value;
		jQuery.each(this, function(i, val) {
			if (i.endsWith("label")) {
				label = val.value;
			} else if (i.endsWith("uri")) {
				uri = val.value;
			}
		});

		displaytext += "<tr>";
		displaytext += "<td>" + label + "</td>"
				+ "<td><a href='"+uri+
					"' target='_blank'>" + uri
				+ "</d></td>";
		displaytext += "</tr>";
		i++;
	});
	displaytext += "</table>";
	var termcount = "";
	if (i > 0) termcount = "Total result: "+i+"<br>";
	else termcount = "No result.<br>";
	var div = document.getElementById('relatedTermsResult');
	div.innerHTML = termcount+displaytext;
}

function writeTermsDetail(termDetail){
	var displaytext = "<table id='termDetail'>";
	jQuery.each(
		termDetail.results.bindings,
			function() {
				var attribute = this.attribute.value;
				var value = this.value.value;
				var valtype = this.value.type;
				var shortAttr = attribute
								.substring(attribute
										.lastIndexOf("/") + 1);
				displaytext += "<tr>";
//				displaytext += "<td><a href='"
//							+ encodeURI(attribute)
//							+ "' target='_blank'>"
//							+ shortAttr + "</a></td>";
				displaytext += "<td>"+ shortAttr + "</td>";
				if (valtype == "uri") {
					displaytext += "<td><a href='"+value+
						"' target='_blank'>"
						+ value + "</a></td>";
				} else {
					displaytext += "<td>" + value
								+ "</td>";
				}

				displaytext += "</tr>";
	});

	displaytext += "</table>";
	var div = document.getElementById('termDetail');
	div.innerHTML = displaytext;
}