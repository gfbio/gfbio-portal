function createDatatable(dataset,facet){
	var region = [];
	for (var i=0; i<facet.facet.region.length; i++){
		region.push(facet.facet.region[i].name);
	}
	console.log('region:'+region);
	var datacenter = [];
	for (i=0; i<facet.facet.datacenter.length; i++){
		datacenter.push(facet.facet.datacenter[i].name);
	}
	console.log('datacenter:'+datacenter);
	var project = [];
	for (i=0; i<facet.facet.project.length; i++){
		project.push(facet.facet.project[i].name);
	}
	console.log('project:'+project);
	var parameter = [];
	for (i=0; i<facet.facet.parameter.length; i++){
		parameter.push(facet.facet.parameter[i].name);
	}
	console.log('parameter:'+parameter);
	var investigator = [];
	for (i=0; i<facet.facet.investigator.length; i++){
		investigator.push(facet.facet.investigator[i].name);
	}
	console.log('investigator:'+investigator);
	var taxonomy = [];
	for (i=0; i<facet.facet.taxonomy.length; i++){
		taxonomy.push(facet.facet.taxonomy[i].name);
	}
	console.log('taxonomy:'+taxonomy);
	
	var oTable = $('#tableId').dataTable( {
		"bJQueryUI": true,
		"bDestroy": true,
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
		},
//		"oColVis": { "fnStateChange": function (iColumn, bVisible) { columnstatechange();   } }
	}).columnFilter({
		"aoColumns": [
	     		null,
	     		null,
	     		null,
	     		{ type: "checkbox", values: datacenter  },
	     		{ type: "checkbox", values: region  },
	     		{ type: "checkbox", values: project  },
	     		null,
	     		{ type: "checkbox", values: parameter  },
	     		{ type: "checkbox", values: investigator  },
	     		{ type: "checkbox", values: taxonomy  }
	     		],
	    "bUseColVis": true
	});			
//	 columnstatechange();
}

function columnstatechange(){
    $("tfoot input").keyup( function () {
         var oSettings = oTable.fnSettings();
         var renderedIndex=$("tfoot input").index(this);
         var hiddenColumnsCount=0;
         var aoColumns=oSettings.aoColumns;
                //count the hidden columns preceding the currently active filter input.
                //note: we want to stop counting at the current filter index, which is renderedIndex+hiddenColumnsCount
         for (i=0; i<=renderedIndex+hiddenColumnsCount; i++) { 
             if (!aoColumns[i].bVisible) hiddenColumnsCount++;
         }
         oTable.fnFilter( this.value, renderedIndex+hiddenColumnsCount );
     } );
    $("tfoot input").focus( function () {
         if ( this.className == "search_init" ) {
           this.className = "";
           this.title = this.value;         // use the title attribute to store the temp value for the filter input
           this.value = "";
         }
     } );
    $("tfoot input").blur( function (i) {
         if ( this.value == "" ) {
           this.className = "search_init";
           this.value = this.title;
         }
     } );

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