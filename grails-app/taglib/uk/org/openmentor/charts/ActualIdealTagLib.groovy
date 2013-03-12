package uk.org.openmentor.charts

import uk.org.openmentor.domain.Summary
import uk.org.openmentor.util.MultiMap;

import grails.converters.JSON

class ActualIdealTagLib {
	
	def categorizationInfoService
	
	def actualIdealTable = { attrs, body ->
		Summary summary = attrs.summary
		String ref = attrs.ref
		
		MultiMap data = summary.data
		List<String> keys = data.keySet() as List
		List<Number> idealValues = keys.collect { val -> data.get(val)?.ideal ?: 0 }
		List<Number> actualValues = keys.collect { val -> data.get(val)?.actual ?: 0 }
		
		out << """
<table class="actual-ideal">
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
			"<tr><td>${keys[i]}</td><td>${idealValues[i]}</td><td>${actualValues[i]}</td></tr>"
		}.join("\n") +
		"""
    </tbody>
</table>
"""
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
