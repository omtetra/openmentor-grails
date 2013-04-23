// Custom charting code for OpenMentor. This draws a variety of bullet
// charts while supporting progressive enhancement from a table. 

(function( $ ) {
	
  var methods = {
	init: function(options) {
	  return this.each(function() {
		var config = $.extend( {
	       'location'         : 'top',
		   'background-color' : 'blue',
		   'shades'           :  ["#bbb", "#999", "#777", "#555"],
		   'entry-size'       : 40,
		   'background-size'  : 30,
		   'ideal-size'       : 20,
		   'actual-size'      : 10,
		   'top-margin'       : 0,
		   'right-margin'     : 10,
		   'bottom-margin'    : 20,
		   'left-margin'      : 100,
		   'width'            : 500,
		   'label-style'      : {"text-anchor" : "start", font: '12px Helvetica, Arial', fill: "#000"},
		   'tick-label-style' : {font: '10px Helvetica, Arial', fill: "#555"},
		   'actual-style'     : {stroke: "none", fill: "#555"},
		   'ideal-style'      : {stroke: "none", fill: "black"},
		   'background-style' : {stroke: "none", fill: "#ddd"},
		   'tick-style'       : {stroke: '#777', "stroke-width" : 0.5},
		   'actual-selector'  : '.bullet-actual',
		   'ideal-selector'   : '.bullet-ideal',
		   'label-selector'   : '.bullet-label',
		   'link-selector'    : '.bullet-label a',
		   'entry-selector'   : '.bullet',
		   'bounds-chi'       : 2.706,
		   'bounds-minimum'   : 8
		}, options);
	    var $this = $(this),
	        data = $this.data('bulletChart'),
	        chartElement = jQuery('<div class="bullet-chart"></div>');
	    if (! data) {
	      config.target = $this;
	      config.chartElement = chartElement;
	      $(this).data('bulletChart', config);
	    }
	    methods.show.apply($this);
	  })
	},
	destroy: function() {
	  return this.each(function() {
		var $this = $(this),
        data = $this.data('bulletChart');
		$(window).unbind('.bulletChart');
		data.bulletChart.remove();
		$this.removeData('bulletChart');
	  });
	},
	getData: function() {
	  var data = $(this).data('bulletChart');
	  var dataSource = data['target'];
	  var entrySelector = data['entry-selector'];
	  var labelSelector = data['label-selector'];
	  var linkSelector = data['link-selector'];
	  var actualSelector = data['actual-selector'];
	  var idealSelector = data['ideal-selector'];
	  var tableRows = [];
	  dataSource.find(entrySelector).each(function(i, e) {
		var label = jQuery(e).find(labelSelector).text();
		var link = jQuery(e).find(linkSelector).prop('href');
		var ideal = jQuery(e).find(idealSelector).text();
		var actual = jQuery(e).find(actualSelector).text();
		tableRows.push({"name" : label, "actual" : actual, "ideal" : ideal, "link" : link})
	  })
	  return tableRows;
	},
	_getTooltipTitle: function(entry) {
	  return "Actual: " + entry.actual + ", ideal: " + entry.ideal;
	},
	_showAxis: function() {
	  var data = $(this).data('bulletChart');
	  var entryHeight = data["entry-size"];
	  var paper = data['paper'];
	  var barspace = entryHeight - data["background-size"];
	  
	  var axis = data['axis'];
	  var scaler = axis.scaler;
	  
	  var tickcount = axis.tickCount;
	  var tickspaces = tickcount - 1;
	  var tickrange = (data['width'] - data['left-margin'] - data['right-margin'] - 2) / tickspaces;
	  var path = [];
	  var ticktop = data['top-margin'] + (data["entry-size"] * data["entry-count"]) - barspace/2;
	  
	  var axisTickCount = axis.ticks.length;
	  for(var k = 0; k < axisTickCount; k++) {
		var tick = axis.ticks[k];
		var tickleft = data['left-margin'] + (tick * scaler);
		path = path.concat(["M", tickleft, ticktop, "L", tickleft, ticktop + 8]);
		var tickText = parseFloat(tick.toPrecision(8));
		var label = paper.text(tickleft, ticktop + 14, tickText.toString()).attr(data["tick-label-style"]);
	  }
	  
	  path = path.join(",");
	  paper.path(path).attr(data["tick-style"]);
	},
	_showEntry: function(element, entry, i) {
		var data = $(this).data('bulletChart');
		
		var entryHeight = data["entry-size"];
		var paper = data['paper'];
		var barspace = entryHeight - data["background-size"];
		var label = paper.text(1.5, i * entryHeight + data['top-margin'], entry.name).attr(data['label-style']);
		var labelheight = label.getBBox().height;
		var labeloffset = entryHeight / 2;
		var shades = data.shades;

		var actualboxoffset = (entryHeight - data["actual-size"]) / 2;
		var idealmarkeroffset = (entryHeight - data["ideal-size"]) / 2;
		var backgroundoffset = (entryHeight - data["background-size"]) / 2;
		var fullbarheight = data["background-size"];
	
        var axis = data['axis'];
		var scaler = axis.scaler;

		// Don't place bounds for a very low ideal count, as they won't be reliable.
		if (data["bounds-chi"] && entry.ideal >= data["bounds-minimum"]) {
   		  var chi = data["bounds-chi"];
		  var f = parseInt(entry.ideal);
		  var factor = Math.sqrt(16 - 4*(2 / f)*(2*f - chi));
		  var lower = (4 - factor) / ( 4 / f);
		  var upper = (4 + factor) / ( 4 / f);
		  entry.ranges = [[lower, upper]];
		}
		
		label.transform("t0," + labeloffset);
		if (entry.link) {
			paper.rect().attr(label.getBBox()).attr({
				fill: "#bbb",
				opacity: 0,
				cursor: "pointer"
			}).click(function () { window.location.href = entry.link; });
		}
		
		var background = paper.rect(
			data['left-margin'] + .5, i * entryHeight + data['top-margin'] + backgroundoffset, 
			data['width'] - data['left-margin'] - data['right-margin'] - .5, data["background-size"]).attr(data['background-style']);
		var ranges = entry.ranges;
		if (ranges) {
			var length = ranges.length;
			for(var j = 0; j < length; j++) {
				var range = ranges[j];
				var rangeleft = Math.max(data['left-margin'] + .5 + (range[0] * scaler), data['left-margin']);
				var rangewidth = Math.min(((range[1] - range[0]) * scaler), (data["width"] - data["right-margin"] - rangeleft));
				var emphasis = paper.rect(
					rangeleft, i * entryHeight + data['top-margin'] + backgroundoffset, 
					rangewidth, fullbarheight).attr({stroke: 'none', fill: shades[j]});
			}
		}
		
//		console.log(f, factor, lower, upper);
//		
//		
//		var chi = entry.ideal 
//			? ((2 * (entry.actual - entry.ideal) * (entry.actual - entry.ideal)) / entry.ideal)
//		    : 0;
		
		
		var tooltipTitle = methods._getTooltipTitle.call(this, entry);
		var actualbox = paper.rect(
			data['left-margin'] + .5, i * entryHeight + data['top-margin'] + actualboxoffset, 
			entry.actual * scaler, data["actual-size"]).attr(data['actual-style']);
		jQuery(actualbox.node).tooltip({'container': element, 'placement': 'right', "title" : tooltipTitle});
		var idealmarker = paper.rect(
			data['left-margin'] + .5 + (entry.ideal * scaler) - 1.5, i * entryHeight + data['top-margin'] + idealmarkeroffset, 
			3, data["ideal-size"]).attr(data["ideal-style"]);
	  
	},
	_getAxis: function() {
		var $this = $(this),
	    data = $this.data('bulletChart');
		var tableData = data['table-data'];
		var tableDataLength = tableData.length;
		var values = [];
		for(var i = 0; i < tableDataLength; i++) {
			var entry = tableData[i];
			values.push(Math.max(entry.actual, entry.ideal));
		}
		var maximum = Math.max.apply(Math, values);
		//console.log(Math.max.apply(Math, values));
		
        var dec = -Math.floor(Math.log(maximum) / Math.LN10);
        var magn = Math.pow(10, -dec);
        var norm = maximum / magn; 
        var size;
        var axis = {min: 0, max: maximum};
        
        //console.log("A", dec, norm, magn);
        
        if (norm < 1.5)
            size = 2;
        else if (norm < 3)
            size = 5;
        else
        	size = 10;
        
        size *= (magn / 10);
        
        axis.tickDecimals = Math.max(0, dec);
        axis.tickSize = size;

        var ticks = [];
        // spew out all possible ticks
        var start = axis.tickSize * Math.floor(axis.min / axis.tickSize),
            i = 0, v = Number.NaN, prev;
        do {
            prev = v;
            v = start + i * axis.tickSize;
            ticks.push(v);
            ++i;
        } while (v < axis.max && v != prev);
        
        axis.ticks = ticks;
        axis.tickCount = axis.ticks.length;
        axis.max = Math.max(axis.max, axis.ticks[axis.tickCount - 1]);
        axis.scaler = (data['width'] - data['left-margin'] - data['right-margin'] - .5) / axis.max;
        
		return axis;
	},
	show: function() {
	  this.each(function() {
		var $this = $(this),
	    data = $this.data('bulletChart');
		
		var dataTable = jQuery(this);
		var tableData = methods.getData.apply(this);
		
	    var entryHeight = data["entry-size"];
	    var entryCount = tableData.length;
	    var width = data["width"];
		var height = entryHeight * entryCount + data["bottom-margin"];
	    		
		var element = jQuery('<div class="bullet-chart"></div>').insertAfter(this);
		element.width(width).height(height);
		
		var paper = Raphael(element.get(0), width, height);
		//var c = paper.rect(-100, -100, 2000, 2000).attr({'fill': '#ffd'});
		
		data['entry-count'] = entryCount;
		data['table-data'] = tableData;
		data['paper'] = paper;
		data['axis'] = methods._getAxis.apply(this);
		$this.data('bulletChart', data);
		
		//console.log(data);
		
		var tableDataLength = tableData.length;
		for(var i = 0; i < tableDataLength; i++) {
			var entry = tableData[i];
			methods._showEntry.call($this, element, entry, i);
		}
		
		//dataTable.collapse('hide');
		dataTable.hide();
		
		methods._showAxis.apply(this);
	  });
	}
  };
	
  $.fn.bulletChart = function(options) {
	methods.init.apply( this, arguments );
  }
})( jQuery );
