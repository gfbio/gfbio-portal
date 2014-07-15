//function setFireSelectedData(oTable){
// TODO: prevent detail row selected
	// for multiple selection
//	console.log('2');
//	$('#tableId tbody').off("click");
//    $('#tableId tbody').on( 'click', 'tr', function (e) {
//        $(this).toggleClass('selected');
//    } );
//	$('#pubSelectedData').off("click");
//    $('#pubSelectedData').click( function () {
//    	var jsonData = {};
//    	var results = [];
//    	jsonData.results = results;
//      	console.log('3');
//        $.each(oTable.rows('.selected').data(),function(i,value){
//        	var result = {"metadataLink":value.metadataLink,
//        			"timeStamp": value.timeStamp,
//        			"maxLatitude": value.maxLatitude,
//        			"minLatitude": value.minLatitude,
//        			"maxLongitude": value.maxLongitude,
//        			"minLongitude": value.minLongitude};
//        	jsonData.results.push(result);
//        	console.log('fire selected data');
//        });
//    	Liferay.fire('gadget:gfbio.search.selectedData', jsonData);
//    } );
    
    // for single selection
//	$('#tableId tbody').on( 'click', 'tr', function () {
//        if ( $(this).hasClass('selected') ) {
//            $(this).removeClass('selected');
//        }
//        else {
//        	oTable.$('tr.selected').removeClass('selected');
//            $(this).addClass('selected');
//        }
//    } );
//}

function addExtraRow() {
	var elems = document.getElementsByTagName('td');
	var elm = null;
	// check if the event has been added
    for (var i in elems) {
        if((' ' + elems[i].className + ' ').indexOf(' details-control ')
                > -1) {
            elm = elems[i];
        }
    }
	var ev = $._data(elm, 'events');
	if (ev && ev.click) {
		console.log('click bound');
	} else {
		$('.details-control').click(function(evt) {
			evt.stopPropagation();
			evt.preventDefault();
			console.log('show detail clicked');
			var tr = $(this).closest('tr');
			var oTable = $('#tableId').DataTable();
			var row = oTable.row(tr);

			console.log(row.child.isShown());
			if (row.child.isShown()) {
				// This row is already open - close it
				row.child.hide();
				tr.removeClass('shown');
			} else {
				// Open this row
				var extraRow =createExtraRow(row.data());
				row.child(extraRow).show();
				tr.addClass('shown');
				tr.addClass('detail-row');
			}
		});
	}
}

function addToolTip() {
	console.log('add tooltip');
	$('.odd, .even').hover(
			function() {
				var table = $('#tableId').dataTable();
				var iRow = table.fnGetPosition(this);
				var sCitation = "";
				var sDescription = "";
				if (table.length > 0) {
					sCitation = table.fnGetData(iRow)["authors"];
					sDescription = table.fnGetData(iRow)["description"];
				}
				this.setAttribute('title', "Author(s):" + sCitation
						+ "\n\nDescription: " + sDescription);
			});
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
		var str = data.selected + '';
		var selectedList = str.split(",");
		getFilterTable(selectedList);
	});
}

function createFacetList(facet) {

	var listString = '<ul class="facetUL">';
	$.each(facet, function(id, option) {
		listString += '<li id="li1_' + id + '">' + id;
		listString += '<ul>';
		$.each(option, function(id2, option2) {
			var val = option2.name;
			var count = option2.count;
			if (val.length > 20)
				val = val.substring(0, 20);
			listString += '<li id="li2_' + id + '_' + val + '">';
			listString += option2.name +' ('+ count+')</li>';
		});
		listString += '</ul>';
		listString += '</li>';
	});
	listString += '</ul>';
	return listString;
}

function getFilterTable(selectedList) {
//TODO: improve filter function
	var datacenterFilter = '';
	var datacenterId = 3;
	var datacenterCheck = true;
	var regionFilter = '';
	var regionId = 4;
	var regionCheck = true;
	var projectFilter = '';
	var projectId = 5;
	var projectCheck = true;
	var parameterFilter = '';
	var parameterId = 7;
	var parameterCheck = true;
	var investigatorFilter = '';
	var investigatorId = 8;
	var investigatorCheck = true;

	for ( var i = 0; i < selectedList.length; i++) {
		var id = selectedList[i];
		var arrayid = id.split("_");
		if (arrayid[0] == "li1") {
			if (arrayid[1] == "datacenter") {
				datacenterCheck = false;
				datacenterFilter = '';
			} else if (arrayid[1] == "region") {
				regionCheck = false;
				regionFilter = '';
			} else if (arrayid[1] == "project") {
				projectCheck = false;
				projectFilter = '';
			} else if (arrayid[1] == "parameter") {
				parameterCheck = false;
				parameterFilter = '';
			} else if (arrayid[1] == "investigator") {
				investigatorCheck = false;
				investigatorFilter = '';
			}
		} else if (arrayid[0] == "li2") {
			if (arrayid[1] == "datacenter" && datacenterCheck) {
				if (datacenterFilter != "")
					datacenterFilter += "|";
				datacenterFilter += arrayid[2];
			} else if (arrayid[1] == "region" && regionCheck) {
				if (regionFilter != "")
					regionFilter += "|";
				regionFilter += arrayid[2];
			} else if (arrayid[1] == "project" && projectCheck) {
				if (projectFilter != "")
					projectFilter += "|";
				projectFilter += arrayid[2];
			} else if (arrayid[1] == "parameter" && parameterCheck) {
				if (parameterFilter != "")
					parameterFilter += "|";
				parameterFilter += arrayid[2];
			} else if (arrayid[1] == "investigator" && investigatorCheck) {
				if (investigatorFilter != "")
					investigatorFilter += "|";
				investigatorFilter += arrayid[2];
			}
		}
	}
	var oTable = $('#tableId').DataTable();
	console.log(datacenterFilter);
	oTable.column(datacenterId, true, false).search(datacenterFilter).draw();
	console.log(regionFilter);
	oTable.column(regionId).search(regionFilter, true, false).draw();
	console.log(projectFilter);
	oTable.column(projectId).search(projectFilter, true, false).draw();
	console.log(parameterFilter);
	oTable.column(parameterId).search(parameterFilter, true, false).draw();
//	console.log(taxonomyFilter);
//	oTable.column(taxonomyId).search(taxonomyFilter, true, false).draw();
	console.log(investigatorFilter);
	oTable.column(investigatorId).search(investigatorFilter, true, false)
			.draw();
}

function checkAllTree(facet) {
	$.each(facet, function(id, option) {
		$('#search_result_facet').jstree("select_node", "#li1_" + id).jstree(
				"open_node", $('#li1_region'));
	});
}
function createExtraRow(d) {
	var data = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
			+ createTR('Full title',d.title)
			+ createTR('Authors',d.authors)
			+ createTR('Data Center', d.dataCenter)
//			+ createTR('Citation',d.citation)
			+ createTR('Description',d.description)
			+ createTR('Investigator',d.investigator)
			+ createTR('Project',d.project)
			+ createTR('Region',d.region)
			+ createTR('Taxonomy',d.taxonomy)
			+ createTR('MaxLatitude',d.maxLatitude)
			+ createTR('MinLatitude',d.minLatitude)
			+ createTR('MaxLongitude',d.maxLongitude)
			+ createTR('MinLongitude',d.minLongitude)
			+ createTR('Repository', 'PANGAEA : <a href="http://www.pangaea.de/">http://www.pangaea.de/</a>');
	if (d.metadataLink != "N/A")
		data += createTR('Link', '<a href="'+d.metadataLink+'" target="_newtab">'+d.metadataLink+'</a>');
//			+ createTR('License', d.dataRights)
	if (d.dataLink != "N/A")	
		data += createTR('Download', '<a href="javascript:onDownload(\''+d.dataLink+'\');">'+d.dataLink+'</a>');
			
	data += '</table>';
	return data;
}

function onDownload(link) {
    document.location = 'data:Application/octet-stream,' +
                         encodeURIComponent(link);
}

function createTR(name,data){
	var text = '<tr><td><b>'+name+':</b></td><td>' 
				+ data + '</td></tr>';
	return text;
}
function writeResultTable(){
	var displaytext ="<table style='border: 0; cellpadding: 0; cellspacing: 0;' id='tableId' class='display'>";
		displaytext += "<thead class='ui-state-default'><tr>";				
		displaytext += "<th>Title</th>";	
		displaytext += "<th>Author(s)</th>";	
		displaytext += "<th>Description</th>";	
		displaytext += "<th>Data Center</th>";	
		displaytext += "<th>Region</th>";	
		displaytext += "<th>Project</th>";	
		displaytext += "<th>Citation Date</th>";	
		displaytext += "<th>Parameter</th>";	
		displaytext += "<th>Investigator</th>";	
		displaytext += "<th>Score</th>";	
		displaytext += "<th>Timestamp</th>";		
		displaytext += "<th>Max Latitude</th>";	
		displaytext += "<th>Min Latitude</th>";	
		displaytext += "<th>Max Longitude</th>";	
		displaytext += "<th>Min Longitude</th>";	
//		displaytext += "<th></th>";	
		displaytext += "<th></th>";	
		displaytext += "</tr></thead>";	
		displaytext += "<tfoot><tr>";	
		displaytext += "<th>Title</th>";	
		displaytext += "<th>Author(s)</th>";	
		displaytext += "<th>Description</th>";	
		displaytext += "<th>Data Center</th>";	
		displaytext += "<th>Region</th>";	
		displaytext += "<th>Project</th>";	
		displaytext += "<th>Citation Date</th>";	
		displaytext += "<th>Parameter</th>";	
		displaytext += "<th>Investigator</th>";	
		displaytext += "<th>Score</th>";	
		displaytext += "<th>Timestamp</th>";		
		displaytext += "<th>Max Latitude</th>";	
		displaytext += "<th>Min Latitude</th>";	
		displaytext += "<th>Max Longitude</th>";	
		displaytext += "<th>Min Longitude</th>";	
//		displaytext += "<th></th>";	
		displaytext += "<th></th>";	
		displaytext += "</tr></tfoot></table>";	
		displaytext += "<input id='pubSelectedData' name='pubSelectedData'";
		displaytext += " type='button' value='Publish selected data'";
		displaytext += " style='font-weight: bold; width:100%'/>";	
				 
	var div = document.getElementById('search_result_table');
	div.innerHTML = displaytext;
}


function getValueByAttribute(list, attr, val){
    var result = null;
    $.each(list, function(index, item){
        if(item[attr].toString() == val.toString()){
           result = list[index].value;
           return false;     // breaks the $.each() loop
        }
    });
    return result;
}
