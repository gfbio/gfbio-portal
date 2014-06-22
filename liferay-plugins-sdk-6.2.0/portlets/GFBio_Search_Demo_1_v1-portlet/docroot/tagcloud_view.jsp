<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />
<script type='text/javascript'>
	var portletAjaxURL = '<portlet:resourceURL id="tagcloudURL" escapeXml="false" />';
</script>

<script src="<%=request.getContextPath()%>/js/jquery.tagcloud.0-0-1.js"
	type="text/javascript"></script>
<script type="text/javascript">
function hex (c) {
	  var s = "0123456789abcdef";
	  var i = parseInt (c);
	  if (i == 0 || isNaN (c))
	    return "00";
	  i = Math.round (Math.min (Math.max (0, i), 255));
	  return s.charAt ((i - i % 16) / 16) + s.charAt (i % 16);
	}

	/* Convert an RGB triplet to a hex string */
	function convertToHex (rgb) {
	  return hex(rgb[0]) + hex(rgb[1]) + hex(rgb[2]);
	}

	/* Remove '#' in color hex string */
	function trim (s) { return (s.charAt(0) == '#') ? s.substring(1, 7) : s }

	/* Convert a hex string to an RGB triplet */
	function convertToRGB (hex) {
	  var color = [];
	  color[0] = parseInt ((trim(hex)).substring (0, 2), 16);
	  color[1] = parseInt ((trim(hex)).substring (2, 4), 16);
	  color[2] = parseInt ((trim(hex)).substring (4, 6), 16);
	  return color;
	}
	function getColor(step, count){
		var start = convertToRGB ('#aa0000');    /* The beginning of your gradient */
	    var end   = convertToRGB ('#0088ff');    /* The end of your gradient */
	    var alpha = 0.5;                         /* Alpha blending amount */
	    var c = [];

//    	 	c[0] = (start[0] * alpha) + ((1 - alpha) * end[0]*step/count);
//    	 	c[1] = (start[1] * alpha) + ((1 - alpha) * end[1]*step/count);
//    	 	c[2] = (start[2] * alpha) + ((1 - alpha) * end[2]*step/count);
   	 	c[0] = (start[0]) + ( end[0]*step/count);
   	 	c[1] = (start[1]) + ( end[1]*step/count);
   	 	c[2] = (start[2]) + ( end[2]*step/count);
		return convertToHex(c);
	}
$(function () {
	console.log('tagcloud');
	  Liferay.on(
	            'facetUpdate',
	            function(event) {
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
	    );
});
 </script> 

<div id="cloud">
</div>