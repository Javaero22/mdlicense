package com.md.mdcms.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class Rows implements Serializable {

	private static final long serialVersionUID = -3121154970388737434L;

	@ElementList(inline = true, required = false)
	private List<Row> rows;

	public Rows() {
		super();
	}

	public Rows(List<Row> rows) {
		super();
		this.rows = rows;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

}
