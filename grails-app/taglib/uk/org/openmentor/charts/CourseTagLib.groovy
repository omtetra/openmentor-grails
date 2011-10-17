package uk.org.openmentor.charts

import uk.org.openmentor.domain.DataBook;

import grails.converters.JSON

class CourseTagLib {
	def courseChart = { attrs, body ->
		DataBook dataBook = attrs.book
		String ref = attrs.ref
		
		List<String> bands = dataBook.getDataPoints()
		List<Number> idealValues = dataBook.getDataSeries("IdealCounts")
		List<Number> actualValues = dataBook.getDataSeries("ActualCounts")
		
		JSON writer = new JSON()
		writer.setTarget([bands: bands, ideal: idealValues, actual: actualValues])
		
		out << """
<script type="text/javascript">
jQuery(function () {
    var data = ${writer.toString()};
    var dataIdeal =  [];
    var dataActual = [];
    var ticks = [];

    for(i = 0; i < data.bands.length; i++) {
        ticks.push([1 + i * 3, data.bands[i]]);
        dataIdeal.push([i * 3, data.ideal[i]]);
        dataActual.push([1 + i * 3, data.actual[i]]);
    }

    var plot = jQuery.plot(jQuery("#${ref}"), [
        {
            label: "Ideal",
            data: dataIdeal,
            bars: { show: true }
        },
        {
            label: "Actual",
            data: dataActual,
            bars: { show: true }
        }
    ], {
        xaxis: { ticks: ticks },
        grid: { hoverable: true, clickable: true },
    });
});
</script>
"""
	}
}
