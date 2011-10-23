package uk.org.openmentor.domain

import org.apache.log4j.Logger;

class DataSheet {
	
	/**
     * Our usual logger.
     */
	static final Logger log = Logger.getLogger(this)

	/** List of column labels */
	private List<String> columns = new ArrayList<String>();

	/** List of row labels */
	private List<String> rows = new ArrayList<String>();
	
	
	private List<List<Object>> data = new ArrayList<List<Object>>();
	
	/**
	 * Sets a list of column labels
	 * @param columns
	 */
	public void setColumnLabels(List<String> columns) {
		this.columns.clear();
		this.columns.addAll(columns);
	}
	
	/**
	 * Retrieves a list of column labels
	 * @return
	 */
	public List getColumnLabels() {
		return columns;
	}
	
	/**
	 * Sets a list of row labels
	 * @param rows
	 */
	public void setRowLabels(List<String> rows) {
		this.rows.clear();
		this.rows.addAll(rows);
	}

	/**
	 * Retrieves a list of row labels
	 * @return
	 */
	public List getRowLabels() {
		return rows;
	}
	
	/**
	 * Sets a column/row value
	 * @param column
	 * @param row
	 * @param value
	 */
	public void setValue(int column, int row, Object value) {
		while (row + 1 > data.size())
			data.add(new ArrayList<Object>());
		List<Object> rowBlock = data.get(row);
		if (rowBlock == null) {
			data.set(row, rowBlock = new ArrayList<Object>());
		}
		while (column + 1 > rowBlock.size())
			rowBlock.add(null);
		rowBlock.set(column, value);
	}

	/**
	 * Retrieves a column/row value
	 * @param column
	 * @param row
	 * @return
	 */
	public Object getValue(int column, int row) {
		while (row + 1 > data.size())
			data.add(new ArrayList<Object>());
		List rowBlock = data.get(row);
		if (rowBlock == null) {
			data.set(row, rowBlock = new ArrayList<Object>());
		}
		while (column + 1 > rowBlock.size())
			rowBlock.add(null);
		return rowBlock.get(column);
	}
}
