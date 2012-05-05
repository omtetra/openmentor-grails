package uk.org.openmentor.charts

import uk.org.openmentor.config.Category;
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.domain.SummaryEntry
import uk.org.openmentor.util.MultiMap;

import grails.converters.JSON


class DifferenceChartTagLib {
	
	def getEntryData(MultiMap data, String category, String band) {
		SummaryEntry entry = data.getAt(category).getAt(band)
		def value = (entry.ideal == 0) ? 0 : (100 * ((entry.actual - entry.ideal) / entry.ideal))
		return [value: value, actual: entry.actual, ideal: entry.ideal]
	}
	
	def differenceChart = { attrs, body ->
		Summary summary = attrs.summary
		String ref = attrs.ref
		String band = attrs.band
		String action = attrs.action
		
		assert action != null
		
		MultiMap data = summary.data
		List<String> categories = data.keySet() as List<String>
		Integer categoryCount = categories.size()
		List<Number> table = categories.collect { getEntryData(data, it, band) }
		
		JSON writer = new JSON()
		writer.setTarget([band: band, categories: categories, table: table, data: data])
		
		out << """
<script type="text/javascript">

function showTooltip(x, y, contents) {
    jQuery('<div id="tooltip">' + contents + '</div>').css( {
		position: 'absolute',
        display: 'none',
        top: y + 5,
        left: x + 5,
        border: '1px solid #fdd',
        padding: '2px',
        'background-color': '#fee',
        opacity: 0.80
	}).appendTo("body").fadeIn(200);
}

jQuery(function () {
    var data = ${writer.toString()};
    var table = data.table;
    var categories = data.categories;
    var categoryCount = categories.length;
    
    var ticks = [];
    var series = [];
    for(j = 0; j < categoryCount; j++) {
		ticks.push([0.5 + j, categories[j]]);
        series.push([j, table[j].value]);
    };
    
	var descriptor = {
        data: series,
        bars: { show: true }
    };

    var tooltipItem = undefined;

	function getLabel(item) {
		var actual = table[item.dataIndex].actual;
		var ideal = table[item.dataIndex].ideal;
		return "Actual: " + actual + ", ideal: " + ideal;
	}

	jQuery("#${ref}").bind("plotclick", function (event, pos, item) {
		if (item) {
			window.location.href = "${action}/" + categories[item.dataIndex];
		}
	});

    jQuery("#${ref}").bind("plothover", function (event, pos, item) {
        if (item) {
			if (tooltipItem !== item) {
				if (tooltipItem !== undefined) {
					jQuery("#tooltip").remove();
				}
				tooltipItem = item;
            	showTooltip(item.pageX, item.pageY, getLabel(item));
			}
        } else {
			if (tooltipItem !== undefined) {
				tooltipItem = undefined;
				jQuery("#tooltip").remove();
			}
        }
    });

    var plot = jQuery.plot(jQuery("#${ref}"), [ descriptor ], {
        xaxis: { ticks: ticks },
        grid: { hoverable: true, clickable: true },
    });
});
</script>
"""
	}
}
