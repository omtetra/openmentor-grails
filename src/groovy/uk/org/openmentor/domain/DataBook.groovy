package uk.org.openmentor.domain

import org.apache.log4j.Logger;

class DataBook {
	/**
     * Our usual logger.
     */
	static final Logger log = Logger.getLogger(this)

    /** The list of points. */
    private List<String> points;
    
    /** The storage used for the data. */
    private Map<String, List> hmModelData = new HashMap<String, List>();
    
    /** The storage used for additional properties. */
    private Map<String, String> properties = new HashMap<String, String>();
	
	/**
	 * Sets the data points for the rendering; each data series
	 * should have a value matching the corresponding data point.
	 *
	 * @param value the data to be stored
	 */
	public void setDataPoints(List<String> value) {
		points = value;
		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("DataPoint list is: ");
			for (String s : value) {
				sb.append(s);
				sb.append(", ");
			}
			log.debug(sb.toString());
		}
	}
	
	/**
	 * Returns the data points for the rendering; each data series
	 * should have a value matching the corresponding data point.
	 *
	 * @return		the List of data points
	 */
	public List<String> getDataPoints() {
		return points;
	}

	/**
	 * Sets a property - this can be used to mark up the DataBook
	 * with additional values for either rendering or display.
	 *
	 * @param key		the property key
	 * @param value		the property value
	 */
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}
	
	/**
	 * Gets a property - this can be used to mark up the DataBook
	 * with additional values for either rendering or display.
	 *
	 * @param key		the property key
	 * @return   	    the property value
	 */
	public String getProperty(String key) {
		return (String) properties.get(key);
	}
	
	/**
	 * Stores given data against a given key.
	 *
	 * @param key the key used for storage
	 * @param value the data to be stored.
	 */
	public void setDataSeries(String key, List value) {
		if (log.isDebugEnabled()) {
			log.debug("Storing against key " + key);
			StringBuilder sb = new StringBuilder();
			sb.append("DataSeries list is: ");
			for (Object o : value) {
				String tmp = o.toString();
				if (tmp.length() > 44) {
					sb.append(tmp.substring(0,40));
					sb.append("...");
				} else {
					sb.append(tmp);
				}
				sb.append(", ");
			}
			log.debug(sb.toString());
		}
		hmModelData.put(key, value);
	}

	/**
	 * The <code>getDataSeries</code> returns data that was
	 * previously stored against the given key.
	 *
	 * @param key the key to retrieve the data
	 * @return the data as an <code>Object</code>.
	 */
	public List<Object> getDataSeries(String key) {
		if (log.isDebugEnabled()) {
			log.debug("Retrieving against key " + key);
		}
		if (hmModelData.containsKey(key)) {
			return hmModelData.get(key);
		}
		return null;
	}
}
