function createDatatable(dataset) {
	
//	console.log('create datatable:'+dataset);
//	console.log(searchURL);
//	console.log(keyword);
	
//	 		"dataSrc" : "dataset",
//	 		"type" : "POST",

//	         success : function(data) {
//	             var jsonDataset = eval("(function(){return " + data + ";})()");
//	             var dataset = jsonDataset.dataset;
//	             facet = jsonDataset.facet;
//	             createDatatable(dataset,facet);
//	 			createFacetTree(facet);
//	         }	
	var oTable = $('#tableId').DataTable({
		"bDestroy" : true,
		"bJQueryUI" : true,        
//		"processing": true,
//        "serverSide": true,
//        "ajax": {
//        	"url":searchURL,
//        	"data": {
//        		"mode" : "getResult",
//        		"queryString" : encodeURI(keyword)
//        	},
//        	"dataSrc" : "dataset",
//        	"type" : "POST"
//        },
//        "fnServerParams" : function(aoData){
//        	aoData.push({
//        		"name":"<portlet:namespace />mode",
//        		"value":"getResult"
//        	},{
//        		"name":"<portlet:namespace />queryString",
//        		"value": encodeURI(keyword)
//        	});
//        },
//        "sAjaxSource":searchURL,
		"sDom" : 'Clrtip',
		// display show/hide column button
		// "bUseColVis": true,
		colVis : {
			exclude : [ 0, 11 ]
		},
		// custom visible columns, except title and +button columns
		"columns" : [ {
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
			"data" : "citation",
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
		}, {
			"class" : "details-control",
			"sortable" : false,
			"data" : null,
			"defaultContent" : ""
		} // last column for +button
		],
		"order" : [ [ 9, "desc" ] ], // ordered by score
		"sAutoWidth" : true,
		"aaData" : dataset,
		"oLanguage" : {
			"sSearch" : "Filter: " // change search box label
		},
		"sPaginationType" : "full_numbers",

		"fnDrawCallback" : function() {
			// do nothing if table is empty
		    if (!$(".dataTables_empty")[0]){
				console.log('table draw callback');
				addToolTip();
				addExtraRow();
//				 var jsonDataset = eval("(function(){return " + data + ";})()");
//	             var dataset = jsonDataset.dataset;
//	             facet = jsonDataset.facet;
//	             createDatatable(dataset,facet);
//	 			createFacetTree(facet);
		    }
		}
	});

}
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

	var listString = '<ul>';
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
	return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
			+ createTR('Full title',d.title)
			+ createTR('Authors',d.authors)
			+ createTR('Data Center', d.dataCenter)
			+ createTR('Citation',d.citation)
			+ createTR('Description',d.description)
			+ createTR('Investigator',d.investigator)
			+ createTR('Project',d.project)
			+ createTR('Region',d.region)
			+ createTR('Repository', 'PANGAEA : <a href="http://www.pangaea.de/">http://www.pangaea.de/</a>')
			+ createTR('Link', '<a href="'+d.dsLink+'" target="_newtab">'+d.dsLink+'</a>')
			+ createTR('License', d.dataRights)
			+ createTR('Download', '<a href="'+d.dlLink+'">Dataset Download Link</a>')
			
			+ '</table>';
}

function createTR(name,data){
	var text = '<tr><td><b>'+name+':</b></td><td>' 
				+ data + '</td></tr>';
	return text;
}