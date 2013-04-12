package uk.org.openmentor.util

import java.util.TreeMap

class MultiMap extends TreeMap {

	MultiMap(){}

	def getAt(Object o) {
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
	
	public String toString() {
		Iterator i = entrySet().iterator();
         if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            def e = i.next();
            def key = e.getKey();
            def value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(", ");
        }
	}
	
	/**
	 * Override the default clone() method to do a deep copy. This is useful for
	 * MultiMap values in a summary, as it allows clone() on a summary to be 
	 * effective. 
	 */
	public Object clone() {
		return collectEntries { 
			[it.key, it.value.clone()]
		}
	}
}
