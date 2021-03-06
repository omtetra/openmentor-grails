// Place your Spring DSL code here
beans = {
	
	classifierComponent(uk.org.openmentor.classifier.impl.PatternClassifier) { bean ->
		bean.autowire = 'byName'
		sourceFile = "/WEB-INF/data/rulesets/default.xml"
		resourceOpener = ref('resourceService')
	}
	
	extractorComponent(uk.org.openmentor.extractor.impl.ExtractStandard) { bean ->
		bean.autowire = 'byName'
	}	
}
