package com.md.mdcms.model;

import java.util.List;
import java.util.Vector;

public interface IContainer {

	public Caption getCaption();

	public void setCaption(String caption);

	public String getType();

	public void setType(String type);

	public String getId();

	public void setId(String id);

	public Vector<Field> getSingleFields();

	public void setSingleFields(Vector<Field> singleFields);

	public List<Array> getArrays();

	public void setArray(Vector<Field> array);

	public Tablist getTabList();

	public void setTabList(Vector<Field> tabList);

	public List<Grid> getGrids();

	public void setGrids(Vector<Grid> grids);

	public void addGrids(Vector<Grid> grids);

}
