<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />
<script type="text/javascript">
	var portletAjaxURL = '<portlet:resourceURL id="tagcloudURL" escapeXml="false" />';
</script>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/tagcloud.css" />

<script type="text/javascript">

// create tag cloud from facet
Liferay.on('facetUpdate', function(event) { updateCloud(event);});

function fireFacet(id,data) {
	Liferay.fire('cloudFacetSelection', {
		facetGroup : id,
		facetName : data
	});
}

function updateCloud(event) {
	var facet = event.ipcData;
	var items = [];

	var i = 0;
	$.each(facet, function(id, option) {
		var color =  ['#C24641','#FF8040','#ADA96E','#008080','#157DEC','#810541'];//getColor(i,6);
		var listString='';
		$.each(option, function(id2, option2) {
			listString+='<a href="javascript:fireFacet(\''+id+'\',\''+(id2+1)+'\')" rel="'
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
</script>

<div class="tagcloud" id="cloud">
</div>