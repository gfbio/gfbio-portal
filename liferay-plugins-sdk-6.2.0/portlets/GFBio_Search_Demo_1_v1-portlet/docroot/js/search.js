//function setFireSelectedData(oTable){
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
function updateFacet(event){
	var facet = event.ipcData;
    var items = [];

    var i = 0;
    
    $.each(facet, function (id, option) {
    	var color =  ['#C24641','#FF8040','#ADA96E','#008080','#157DEC','#810541'];//getColor(i,6);   
        var listString='';
    	$.each(option, function (id2, option2) {
        	listString+='<a href="#" rel="'
        	+option2.count+'"><font color="'+color[i]+'">'
        	+option2.name+'</font></a>&nbsp;&nbsp;&nbsp;';
        });
        items.push(listString);

    	 i= i+1;
    });  
    
	$('#cloud').append(items.join(''));

	$.fn.tagcloud.defaults = {
			  size: {start: 10, end: 16, unit: 'pt'}
			  //color: {start: '#cde', end: '#f52'}
			};
	  $('#cloud a').tagcloud();
}

function gfbioQuery() {
	$('#tableId').DataTable().clear();
	var keyword = document.getElementById("gfbioSearchInput").value;
	getSearchResult(keyword);
}

function addExtraRow() {
	var elems = document.getElementsByTagName('td');
	var elm = null;
	// check if the event has been added
	for ( var i in elems) {
		if ((' ' + elems[i].className + ' ').indexOf(' details-control ') > -1) {
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
				var extraRow = createExtraRow(row.data());
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

	var listString = '<ul id="facetUL">';
	$.each(facet, function(id, option) {
		listString += '<li id="li1_' + id + '">' + id;
		listString += '<ul>';
		$.each(option, function(id2, option2) {
			var val = option2.name;
			var count = option2.count;
			if (val.length > 20)
				val = val.substring(0, 20);
			listString += '<li id="li2_' + id + '_' + val + '">';
			listString += option2.name + ' (' + count + ')</li>';
		});
		listString += '</ul>';
		listString += '</li>';
	});
	listString += '</ul>';
	return listString;
}

function getFilterTable(selectedList) {
	// TODO: improve filter function
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
	// console.log(taxonomyFilter);
	// oTable.column(taxonomyId).search(taxonomyFilter, true, false).draw();
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
			+ createTR('Full title', d.title)
			+ createTR('Authors', d.authors)
			+ createTR('Data Center', d.dataCenter)
			// + createTR('Citation',d.citation)
			+ createTR('Description', d.description)
			+ createTR('Investigator', d.investigator)
			+ createTR('Project', d.project)
			+ createTR('Region', d.region)
			+ createTR('Taxonomy', d.taxonomy)
			+ createTR('MaxLatitude', d.maxLatitude)
			+ createTR('MinLatitude', d.minLatitude)
			+ createTR('MaxLongitude', d.maxLongitude)
			+ createTR('MinLongitude', d.minLongitude)
			+ createTR('Repository',
					'PANGAEA : <a href="http://www.pangaea.de/">http://www.pangaea.de/</a>');
	if (d.metadataLink != "N/A")
		data += createTR('Link', '<a href="' + d.metadataLink
				+ '" target="_newtab">' + d.metadataLink + '</a>');
	// + createTR('License', d.dataRights)
	if (d.dataLink != "N/A")
		data += createTR('Download', '<a href="javascript:onDownload(\''
				+ d.dataLink + '\');">' + d.dataLink + '</a>');

	data += '</table>';
	return data;
}

function onDownload(link) {
	document.location = 'data:Application/octet-stream,'
			+ encodeURIComponent(link);
}

function createTR(name, data) {
	var text = '<tr><td><b>' + name + ':</b></td><td>' + data + '</td></tr>';
	return text;
}
function writeResultTable() {
	var displaytext = "<table style='border: 0; cellpadding: 0; cellspacing: 0;' id='tableId' class='display'>";
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
	displaytext += "<th></th>";
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
	displaytext += "<th></th>";
	displaytext += "<th></th>";
	displaytext += "</tr></tfoot></table>";
//	displaytext += "<input id='pubSelectedData' name='pubSelectedData'";
//	displaytext += " type='button' value='Publish selected data'";
//	displaytext += " style='font-weight: bold; width:100%'/>";

	var div = document.getElementById('search_result_table');
	div.innerHTML = displaytext;
}

function getValueByAttribute(list, attr, val) {
	var result = null;
	$.each(list, function(index, item) {
		if (item[attr].toString() == val.toString()) {
			result = list[index].value;
			return false; // breaks the $.each() loop
		}
	});
	return result;
}

function onRowClick(){
	$('#tableId tbody').off("click");
    $('#tableId tbody').on( 'click', 'tr', function (e) {
    	// prevent selecting the extra row
    	if ($(this).hasClass('even') ||$(this).hasClass('odd'))
    	{
    		$(this).toggleClass('selected');
			var basket = document.getElementById("visualBasket");
    		var basketStr = basket.value;
    		var jsonData = {};
    		var selected = [];
    		if ($(this).hasClass('selected')){
    			// add to basket
        		if (basketStr=="") {
        			jsonData.selected = selected;
        		}else{
        			jsonData = JSON.parse(basketStr);	
        		}
    			
        		var nRow = $(this).context;
        		var tRows = $('#tableId').DataTable().rows();
        		var resultArray = createResultArray(nRow,tRows);
        		jsonData.selected.push(resultArray);
        		basket.value = JSON.stringify(jsonData);
    		}else{
    			// remove from basket
        		if (basketStr!="") {
        			jsonData = JSON.parse(basketStr);	
            		var nRow = $(this).context;
            		var tRows = $('#tableId').DataTable().rows();
            		var resultArray = createResultArray(nRow,tRows);
            		jsonData.selected = JSONfindAndRemove(jsonData.selected,'metadataLink',resultArray.metadataLink);
            		basket.value = JSON.stringify(jsonData);
        		}
    		}
//	    	  console.log('basket: ');
//    		console.log(JSON.stringify(document.getElementById("visualBasket").value));

    	    //update visualisation
    		var jsonData = getSelectedResult();
    		console.log('fire selected data: ');
    		Liferay.fire('gadget:gfbio.search.selectedData', jsonData);
    	}
        
    } );
}

function JSONfindAndRemove(array, property, value) {
	var resultArray = [];
	   $.each(array, function(index, result) {
	      if(result[property] == value) {
	    	  console.log('found '+index);
	          // Remove from array is not working I don't know why,
	    	  // just do it another way round
//	          array.splice(index,1);
	      }    else{
	    	  resultArray.push(result);
	      }
	      
   });
	   return resultArray;
}


function getStyle(x,styleProp)
{
	if (x.currentStyle)
		var y = x.currentStyle[styleProp];
	else if (window.getComputedStyle)
		var y = document.defaultView.getComputedStyle(x,null).getPropertyValue(styleProp);
	return y;
}
function componentFromStr(numStr, percent) {
    var num = Math.max(0, parseInt(numStr, 10));
    return percent ?
        Math.floor(255 * Math.min(100, num) / 100) : Math.min(255, num);
}

function rgbToHex(rgb) {
    var rgbRegex = /^rgb\(\s*(-?\d+)(%?)\s*,\s*(-?\d+)(%?)\s*,\s*(-?\d+)(%?)\s*\)$/;
    var result, r, g, b, hex = "";
    if ( (result = rgbRegex.exec(rgb)) ) {
        r = componentFromStr(result[1], result[2]);
        g = componentFromStr(result[3], result[4]);
        b = componentFromStr(result[5], result[6]);

        hex = "0x" + (0x1000000 + (r << 16) + (g << 8) + b).toString(16).slice(1);
    }
    return hex;
}

function listenToEnterPress(){
	 $("#gfbioSearchInput").keyup(function(event){
		    if(event.keyCode == 13){
		        $("#QueryButton").click();
		    }
		});
}
function listenToPageRequest(){
	var urlReq = window.location.search;
	var startPos = urlReq.indexOf("q_=");
	if (startPos >=0){
		var endPos = urlReq.indexOf("&",startPos+3);
		urlReq = urlReq.substring(startPos+3,endPos);
		console.log("input: "+urlReq);	
		var searchInput = document.getElementById("gfbioSearchInput");
		searchInput.value = urlReq;
	}
}

function setAutoComplete(){
	$('#gfbioSearchInput').autocomplete({
	    minLength: 1,
	    delay: 0,
	    source: function(request, response) {
	      $.ajax('http://ws.pangaea.de/es/portals/_suggest', {
	        contentType: 'application/json; charset=UTF-8',
	        type: 'POST',
	        data: JSON.stringify({
	          'suggest': {
	            'text': request.term,
	            'completion': {
	              'field': 'suggest',
	              'size': 12,
	            },
	          },
	        }),
	        dataType: 'json',
	        success: function(data) {
	          response($.map(data.suggest[0].options, function(item) {
	            return item.text;
	          }));
	        },
	      });
	    },
	    open: function() {
	      var maxWidth = $(document).width() - $(this).offset().left - 16;
	      $(this).autocomplete('widget').css({
	        'max-width': maxWidth + "px"
	      });
	    },
	 });
}
function getSelectedResult() {
	var jsonData = {};
	var selected = [];
	var basket = document.getElementById("visualBasket");
	var basketStr = basket.value;
	if (basketStr=="") {
		jsonData.selected = selected;
	}else{
		jsonData = JSON.parse(basketStr);	
	}
	
	return jsonData;
}
function createResultArray(nRow,tRows){
	var div = nRow.getElementsByClassName("sp-preview-inner")[0];
	var color = getStyle(div, "background-color");
	var iRow = nRow._DT_RowIndex;
	var value = tRows.data()[iRow];
	var result = {"metadataLink":value.metadataLink,
	"timeStamp": value.timeStamp,
	"maxLatitude": value.maxLatitude,
	"minLatitude": value.minLatitude,
	"maxLongitude": value.maxLongitude,
	"minLongitude": value.minLongitude,
	"color":rgbToHex(color)};
	return result;
}
function setSelectedRowStyle(){
	// read basket value
	var basket = document.getElementById("visualBasket");
	var basketStr = basket.value;
	var jsonData = {};
	if (basketStr!="") {
		jsonData = JSON.parse(basketStr);
		// loop through each value and compare if the 
		// similar value exists on the current page
		 $.each(jsonData.selected, function(index, result) {
			 var selectedLink = result['metadataLink'];
			 var tb = $('#tableId').DataTable();
			 var displayedResult = tb.rows().data();

			 $.each(displayedResult, function(ind2, res2) {
				var displayedLink = res2.metadataLink;
			    if(selectedLink == displayedLink) {
						console.log('found selected row: '+ind2);
						// if yes, toggle class to selected.
						var row = tb.rows().nodes()[ind2];
						row.className +=' selected';
					}
			 });
		 });
	}
}

function addColorPicker(){
	//TODO: choose color for each row
	var i =0;
	var color="rgb(204, 204, 204)";
	$("#tableId tbody tr").each(function(){
		var row = i%8;
		i++;
		switch (row){
		case 0:
			color = "rgb(224, 102, 102)";
			break;
		case 1:
			color = "rgb(246, 178, 107)";
			break;
		case 2:
			color = "rgb(255, 217, 102)";
			break;
		case 3:
			color = "rgb(147, 196, 125)";
			break;
		case 4:
			color = "rgb(118, 165, 175)";
			break;
		case 5:
			color = "rgb(109, 158, 235)";
			break;
		case 6:
			color = "rgb(111, 168, 220)";
			break;
		case 7:
			color = "rgb(142, 124, 195)";
			break;
		}
		var elm = $(this).context.childNodes;
		for (j in elm){
			var tdClass = elm[j].className;
			if (tdClass.indexOf("color-control") >= 0){
				console.log(color);
				var input = elm[j].childNodes[0];
				input.value = color;
				break;
			}
		}
	});
	  $("input.full-spectrum").spectrum(
			  {
//			    color: "rgb(244, 204, 204)",
//			    showInput: true,
		    showInitial: true,
		    showPalette: true,
		    showSelectionPalette: true,
		    maxPaletteSize: 10,
//			    preferredFormat: "hex",
		    palette: [
		        ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)",
		        "rgb(204, 204, 204)", "rgb(217, 217, 217)","rgb(255, 255, 255)"],
		        ["rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)", 
		        "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)"], 
		        ["rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)", 
		        "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)"], 
		        ["rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)", 
		        "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)"],
		        ["rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
		        "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)"],
		        ["rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)", 
		        "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)"]
		    ],
		    change: function(color) {
		        console.log(color.toHexString());
		        // read basket value
		    	var basket = document.getElementById("visualBasket");
		    	var basketStr = basket.value;
		    	if (basketStr!="") {
			    	var jsonData = {};
		    		jsonData = JSON.parse(basketStr);
			        // get metadata link of the current row
		    		var col = this.parentElement;
		    		var row = col.parentElement;
					var tb = $('#tableId').DataTable();
					var metadataLink = tb.rows().data()[row.rowIndex-1].metadataLink; 
			        // loop compare with the link in basket
	 				$.each(jsonData.selected, function(index, result) {
	 					var selectedLink = result['metadataLink'];
	 				    if(selectedLink == metadataLink) {
		 					// if found the link, update row detail
		 					jsonData.selected[index].color = color.toHexString();
	 				    }
	 				});
	        		basket.value = JSON.stringify(jsonData);
		    	}
		    }
		}
	  );	
}