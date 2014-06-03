function createDatatable(dataset,facet){
	
	var oTable = $('#tableId').dataTable( {
		"bJQueryUI": true,
		"bDestroy": true,
		"sDom" : 'Clfrtip',
		"bUseColVis": true,
		colVis: {exclude: [0,1,2]},
		"columns": [
		               {
		            	   "data":"title",
		            	   "sWidth": "30%"},
		                {
		                	"data":"authors",
		                    "visible": false
		                },
		                {
		                	"data":"description",
		                    "visible": false
		                },{
		                	"data":"dataCenter",
			            	"sWidth": "25%"
		                },
		                {
		                	"data":"region",
		                    "visible": false,
		                    "searchable": true
		                },{
		                	"data":"project",
			            	"sWidth": "25%"
		                },{
		                	"data":"citedDate",
			            	"sWidth": "10%"
		                },
		                {
		                	"data":"parameter",
		                    "visible": false,
		                    "searchable": false
		                },
		                {
		                	"data":"investigator",
		                    "visible": false
		                },
		                {
		                	"data":"taxonomy",
		                    "visible": false
		                },
		                {
		                	"data":"score",
		                    "visible": false
		                },
		                {
		                	"data":"dataCount",
			            	"sWidth": "7%"
		                }
		                ,{
		                    "class":          'details-control',
		                    "sortable":      false,
		                    "data":           null,
		                    "defaultContent": ''
		                }
		            ],
		"order": [[ 10, "desc" ]],
	    "sAutoWidth": true,
		"aaData": dataset,
		"oLanguage": {
		    "sSearch": "Filter: "
		}
	});

	 $('#tableId tbody').on('click', 'td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var oTable = $('#tableId').DataTable();
	        var row = oTable.row( tr );
	 
	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            row.child.hide();
	            tr.removeClass('shown');
	        }
	        else {
	            // Open this row
	            row.child( format(row.data()) ).show();
	            tr.addClass('shown');
	        }
	    } );
}

function addToolTip(){
	$('#tableId tbody').on('mouseover','tr', function () {
		var table = $('#tableId').dataTable();
		var iRow = table.fnGetPosition(this); 
		var sCitation = "";
		var sDescription = "";
		if (table.length >0){
			sCitation = table.fnGetData(iRow)["authors"];
			sDescription = table.fnGetData(iRow)["description"];
		}
		this.setAttribute( 'title', "Author(s):"+ sCitation +"\n\nDescription: "+ sDescription);
	});

}



function createFacetTree(data){

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
			Liferay.fire('facetUpdate', {
				ipcData : facet
			});

			$('#search_result_facet').on("changed.jstree",
				function(e, data) {
					var str = data.selected + '';
					var selectedList = str.split(",");
					getFilterTable(selectedList);
			});
}

function createFacetList(facet){

	var listString = '<ul>';
    $.each(facet, function (id, option) {
    	listString += '<li id="li1_'+id+'">' + id;
		listString += '<ul>';
        $.each(option, function (id2, option2) {
        	var val = option2.name;
        	if (val.length>20) val = val.substring(0,20);
        	listString+='<li id="li2_'+id+'_'+ val+'">'; 
        	listString+=option2.name+'</li>';
        });
		listString += '</ul>';
        listString += '</li>';
    });  
	listString += '</ul>';
    return listString;
}


function getFilterTable(selectedList) {

	var columns = $('#tableId thead th');

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
	var taxonomyFilter = '';
	var taxonomyId = 9;
	var taxonomyCheck = true;
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
			} else if (arrayid[1] == "taxonomy") {
				taxonomyCheck = false;
				taxonomyFilter = '';
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
			} else if (arrayid[1] == "taxonomy" && taxonomyCheck) {
				if (taxonomyFilter != "")
					taxonomyFilter += "|";
				taxonomyFilter += arrayid[2];
			} else if (arrayid[1] == "investigator" && investigatorCheck) {
				if (investigatorFilter != "")
					investigatorFilter += "|";
				investigatorFilter += arrayid[2];
			}
		}
	}
	var oTable = $('#tableId').DataTable();
	oTable.column(datacenterId,true,false).search(datacenterFilter).draw();
	oTable.column(regionId).search(regionFilter,true,false).draw();
	oTable.column(projectId).search(projectFilter,true,false).draw();
	oTable.column(parameterId).search(parameterFilter,true,false).draw();
	oTable.column(taxonomyId).search(taxonomyFilter,true,false).draw();
	oTable.column(investigatorId).search(investigatorFilter,true,false).draw();
}

function checkAllTree(facet) {
	$.each(facet, function(id, option) {
		//$('#search_result_facet').jstree("select_node", "#li1_" + id).jstree("open_all");
		$('#search_result_facet').jstree("select_node", "#li1_" + id).jstree("open_node", $('#li1_region'));
	});
}
function format ( d ) {
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
        '<tr>'+
            '<td>Full name:</td>'+
//            '<td>'+d.name+'</td>'+
        '</tr>'+
        '<tr>'+
            '<td>Extension number:</td>'+
//            '<td>'+d.extn+'</td>'+
        '</tr>'+
        '<tr>'+
            '<td>Extra info:</td>'+
//            '<td>And any further details here (images etc)...</td>'+
        '</tr>'+
    '</table>';
}