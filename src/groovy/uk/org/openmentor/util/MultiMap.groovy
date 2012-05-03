package uk.org.openmentor.util

import java.util.TreeMap;

class MultiMap extends TreeMap {

	MultiMap(){}

	def getAt(Object o){
		return getProperty(o.toString())
	}

	def getProperty(String b) {
		def v = get(b)
		if (v == null){
			v = new MultiMap()
			put(b, v)
		}
		return v
	}
}
