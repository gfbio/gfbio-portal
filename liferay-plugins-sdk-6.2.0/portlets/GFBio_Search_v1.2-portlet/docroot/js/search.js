

function gfbioQuery() {
		// clear visualBasket
 		var visualBasket = document.getElementById("visualBasket");
		visualBasket.value = "";
		updateVisualisation();
	$('#tableId').DataTable().clear();
	var keyword = document.getElementById("gfbioSearchInput").value;
//	var filter = document.getElementById("facetFilter").value;
	var filter = "";
	getSearchResult(keyword,filter);
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
//	console.log('add tooltip');
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


function checkAllTree(facet) {
	$.each(facet, function(id, option) {
		$('#search_result_facet').jstree("select_node", "#l1__" + id).jstree(
				"open_node", $('#l1__region'));
	});
}
function createExtraRow(d) {
	if (!d.authors) d.authors = "N/A";
	if (!d.dataCenter) d.dataCenter = "N/A";
	if (!d.description) d.description = "N/A";
	if (!d.investigator) d.investigator = "N/A";
	if (!d.project) d.project = "N/A";
	if (!d.region) d.region = "N/A";
	if (!d.taxonomy) d.taxonomy = "N/A";
	if (!d.maxLatitude) d.maxLatitude = "N/A";
	if (!d.minLatitude) d.minLatitude = "N/A";
	if (!d.maxLongitude) d.maxLongitude = "N/A";
	if (!d.minLongitude) d.minLongitude = "N/A";
	if (!d.maxLongitude) d.maxLongitude = "N/A";
	if (!d.metadataLink) d.metadataLink = "N/A";
	if (!d.dataLink) d.dataLink = "N/A";
	if (!d.citationDate) d.citationDate = "N/A";
	if (!d.parameter) d.parameter = "N/A";
	if (!d.timeStamp) d.timeStamp = "N/A";

 	
	var data = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
//			+ createTR('Full title', d.title)
			+ createTR('Authors', d.authors)
			+ createTR('Data Center', d.dataCenter)
			+ createTR('Description', d.description)
			+ createTR('Investigator', d.investigator)
			+ createTR('Project', d.project)
			+ createTR('Region', d.region)
			+ createTR('Taxonomy', d.taxonomy)
			+ createTR('MaxLatitude', d.maxLatitude)
			+ createTR('MinLatitude', d.minLatitude)
			+ createTR('MaxLongitude', d.maxLongitude)
			+ createTR('MinLongitude', d.minLongitude)
			+ createTR('Citation Date', d.citationDate)
			+ createTR('Parameter', d.parameter)
			+ createTR('Internal Timestamp', d.timeStamp)
//			+ createTR('Repository',
//					'PANGAEA : <a href="http://www.pangaea.de/">http://www.pangaea.de/</a>');
	if (d.metadataLink)
		data += createTR('Link', '<a href="' + d.metadataLink
				+ '" target="_newtab">' + d.metadataLink + '</a>');
	// + createTR('License', d.dataRights)
	if (d.dataLink)
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

function onRowClick() {
	$('#tableId tbody').off("click");
	$('#tableId tbody').on( 'click', 'tr', function(e) {
		// prevent selecting the extra row
		if ($(this).hasClass('even') ||$(this).hasClass('odd'))
		{
			$(this).toggleClass('selected');
			var basket = document.getElementById("visualBasket");
			var basketStr = basket.value;
			var jsonData = {};
			var selected = [];
			if ($(this).hasClass('selected')) {
				// add to basket
				if (basketStr=="") {
					jsonData.selected = selected;
				}else {
					jsonData = JSON.parse(basketStr);
				}

				var nRow = $(this).context;
				var tRows = $('#tableId').DataTable().rows();
				var resultArray = createResultArray(nRow,tRows);
				jsonData.selected.push(resultArray);
				basket.value = JSON.stringify(jsonData);
			}else {
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
//	    	console.log('basket: ');
//    		console.log(JSON.stringify(document.getElementById("visualBasket").value));

		    //update visualisation
			updateVisualisation();
		}

	} );
}

function updateVisualisation() {
	var jsonData = getSelectedResult();
	console.log('fire selected data: '+JSON.stringify(jsonData));
	Liferay.fire('gadget:gfbio.search.selectedData', jsonData);

}

function JSONfindAndRemove(array, property, value) {
	var resultArray = [];
	   $.each(array, function(index, result) {
	      if (result[property] == value) {
	    	  console.log('found '+index);
	          // Remove from array is not working I don't know why,
	    	  // just do it another way round
	      }    else {
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
	var result;
	var r;
	var g;
	var b;
	var hex = "";
	if ( (result = rgbRegex.exec(rgb)) ) {
		r = componentFromStr(result[1], result[2]);
		g = componentFromStr(result[3], result[4]);
		b = componentFromStr(result[5], result[6]);

		hex = "0x" + (0x1000000 + (r << 16) + (g << 8) + b).toString(16).slice(1);
	}
	return hex;
}

function listenToEnterPress() {
	 $("#gfbioSearchInput").keyup(function(event) {
		    if (event.keyCode == 13) {
		        $("#QueryButton").click();
		    }
		});
}
function listenToPageRequest() {
	var urlReq = window.location.search;
	var startPos = urlReq.indexOf("q_=");
	if (startPos >=0) {
		var endPos = urlReq.indexOf("&",startPos+3);
		urlReq = urlReq.substring(startPos+3,endPos);
		console.log("input: "+urlReq);
		var searchInput = document.getElementById("gfbioSearchInput");
		searchInput.value = urlReq;
	}
}

function setAutoComplete() {
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
	}else {
		jsonData = JSON.parse(basketStr);
	}

	return jsonData;
}
function createResultArray(nRow,tRows) {
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
function setSelectedRowStyle() {
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
			    if (selectedLink == displayedLink) {
						console.log('found selected row: '+ind2);
						// if yes, toggle class to selected.
						var row = tb.rows().nodes()[ind2];
						row.className +=' selected';
					}
			 });
		 });
	}
}

function addColorPicker() {
	var i =0;
	var color="rgb(204, 204, 204)";
	$("#tableId tbody tr").each(function() {
		var row = i%8;
		i++;
		switch (row) {
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
		for (j in elm) {
			var tdClass = elm[j].className;
			if (tdClass.indexOf("color-control") >= 0) {
//				console.log(color);
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
	 				    if (selectedLink == metadataLink) {
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

function capitaliseFirstLetter(string)
{
    return string.charAt(0).toUpperCase() + string.slice(1);
}

