function createDatatable(dataset){
	var oTable = $('#tableId').dataTable( {
		"bJQueryUI": true,
		"bDestroy": true,
//		"dom": 'C<"clear">lfrtip',
		"sDom" : 'Clfrtip',
		colVis: {exclude: [0,1,2]},
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
		                	"data":"dataCount",
			            	"sWidth": "10%"
		                }
		            ],
		"order": [[ 10, "desc" ]],
	    "sAutoWidth": true,
		"aaData": dataset,
		"oLanguage": {
		    "sSearch": "Filter: "
		}
	});			

}

function addToolTip(){
	$('#tableId tbody').on('mouseover','tr', function () {
		var table = $('#tableId').dataTable();
		var iRow = table.fnGetPosition(this); 
		var sCitation = table.fnGetData(iRow)["authors"];
		var sDescription = table.fnGetData(iRow)["description"];
		
		this.setAttribute( 'title', "Author(s):"+ sCitation +"\n\nDescription: "+ sDescription);
	});

//		$('#tableId').dataTable().$('tr').tooltip( {
//	        "delay": 0,
//	        "track": true,
//	        "fade": 250
//	    } );
}