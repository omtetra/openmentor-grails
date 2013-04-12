package uk.org.openmentor.charts

import uk.org.openmentor.domain.Summary
import uk.org.openmentor.util.MultiMap;

import grails.converters.JSON

class ActualIdealTagLib {
	
	def categorizationInfoService
	
	private actualIdealDisplay(attrs, MultiMap data) {
		String ref = attrs.ref
		List<String> keys = data.keySet() as List
		List<Number> idealValues = keys.collect { val -> data.get(val)?.ideal ?: 0 }
		List<Number> actualValues = keys.collect { val -> data.get(val)?.actual ?: 0 }

		out << """
<table id="${ref}-table" class="actual-ideal table table-striped table-condensed">
    <thead>
        <tr> 
             <td></td>
             <th scope="col">Ideal</th>
             <th scope="col">Actual</th>
        </tr>
    </thead>
    <tbody>
""" +
					(0..keys.size()-1).collect { i ->
						"<tr class='bullet'><td class='bullet-label'><a href='/xxx'>${keys[i]}</a></td><td class='bullet-ideal'>${idealValues[i]}</td><td class='bullet-actual'>${actualValues[i]}</td></tr>"
					}.join("\n") +
					"""
    </tbody>
</table>
<script type="text/javascript">
//jQuery(function () {
//  jQuery("#${ref}-table").bulletChart();
//});
</script>
"""
	}
	
	def actualIdealData = { attrs, body ->
		MultiMap data = attrs.data		
		actualIdealDisplay(attrs, data)
	}
	
	def actualIdealTable = { attrs, body ->
		Summary summary = attrs.summary		
		
		if (summary.submissionCount == 0) {
			out << """
<div class="alert alert-error">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<p><b>No submissions yet.</b> Please try again when you have uploaded some submissions.</p>
</div>
"""
			return
		}
		
		actualIdealData(attrs, summary.data)
	}
	
	def actualIdealChart = { attrs, body ->
		Summary summary = attrs.summary
		String ref = attrs.ref
		
		List<String> bands = categorizationInfoService.getBands()
		MultiMap data = summary.data
		List<Number> idealValues = bands.collect { data.getAt(it).ideal }
		List<Number> actualValues = bands.collect { data.getAt(it).actual }
		
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
