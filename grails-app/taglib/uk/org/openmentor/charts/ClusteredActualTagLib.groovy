package uk.org.openmentor.charts

import uk.org.openmentor.config.Category;
import uk.org.openmentor.domain.Summary
import uk.org.openmentor.util.MultiMap;

import grails.converters.JSON

class ClusteredActualTagLib {
	def clusteredActualChart = { attrs, body ->
		Summary summary = attrs.summary
		String ref = attrs.ref
		
		List<String> bands = Category.getBands()
		MultiMap data = summary.data
		List<String> categories = data.keySet() as List<String>
		Integer categoryCount = categories.size()
		List<List<Number>> table = categories.collect { cat -> bands.collect { data.getAt(cat).getAt(it).actual } }
		
		JSON writer = new JSON()
		writer.setTarget([bands: bands, categories: categories, table: table])
		
		out << """
<script type="text/javascript">
jQuery(function () {
    var data = ${writer.toString()};
    var table = data.table;
    var categories = data.categories;
    var categoryCount = categories.length;
    var space = categoryCount + 1;
    var series = new Array(categories);
    var descriptors = new Array(categories);

    for(j = 0; j < categoryCount; j++) {
        series[j] = new Array();
    };

    var ticks = [];

    for(i = 0; i < data.bands.length; i++) {
        ticks.push([(categoryCount/2) + i * space, data.bands[i]]);
        for(j = 0; j < categoryCount; j++) {
            series[j].push([j + i * space, table[j][i]]);
        };
    }
    
    for(j = 0; j < categoryCount; j++) {
        descriptors[j] = {
            label: categories[j],
            data: series[j],
            bars: { show: true }
        }
    };

    var plot = jQuery.plot(jQuery("#${ref}"), descriptors, {
        xaxis: { ticks: ticks },
        grid: { hoverable: true, clickable: true },
    });
});
</script>
"""
	}
}
