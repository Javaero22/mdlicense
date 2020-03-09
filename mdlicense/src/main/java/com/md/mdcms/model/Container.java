package com.md.mdcms.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.rest.model.ButtonBackingBean;
import com.md.mdcms.rest.model.IBackingBean;
import com.md.mdcms.rest.model.Navigation;
import com.md.mdcms.rest.model.State;
import com.md.mdcms.xml.Xml;

public class Container extends Model implements IContainer, IConstants, Xml {

	private static final long serialVersionUID = -5553904646601426345L;

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(Container.class);

	@ElementList(required = false, inline = true)
	private List<Field> fields;

	@ElementList(required = false, inline = true)
	private List<Item> items;

	@ElementList(required = false, inline = true)
	private List<Grid> grids;

	@ElementList(required = false, inline = true)
	private List<Array> arrays;

	@Attribute
	private String type;

	@Attribute(required = false)
	private String id;

	@Element(required = false)
	private Caption caption;

	@Element(required = false)
	private Header header;

	@Element(name = "tabList", required = false)
	private Tablist tabList;

	@ElementList(required = false, inline = true)
	private List<Box> boxes;

	public Container() {
		super();
	}

	public Container(List<Field> fields, String type) {
		super();
		this.fields = fields;
		this.type = type;
	}

	public Container(String type) {
		super();
		this.type = type;
	}

	public Field getField(String fieldId) {
		if (fields != null) {
			for (Iterator<Field> iter = fields.iterator(); iter.hasNext();) {
				Field field = (Field) iter.next();
				if (field.getId().equalsIgnoreCase(fieldId)) {
					return field;
				}
			}
		}
		return null;
	}

	public String getFieldValue(String fieldName) {
		Field field = getField(fieldName);
		if (field != null) {
			String fieldValue = field.getValue();
			if (fieldValue != null && !fieldValue.equals(""))
				return fieldValue;
		}
		return null;
	}

	public List<Array> getArrays() {
		return arrays;
	}

	public List<Box> getBoxes() {
		return boxes;
	}

	public Caption getCaption() {
		return caption;
	}

	public List<Field> getFields() {
		return fields;
	}

	public List<Grid> getGrids() {
		if (grids == null) {
			grids = new ArrayList<Grid>();
		}
		return grids;
	}

	public Header getHeader() {
		return header;
	}

	public String getId() {
		return id;
	}

	public List<Item> getItems() {
		return items;
	}

	public Tablist getTabList() {
		return tabList;
	}

	public String getType() {
		return type;
	}

	public void setArrays(List<Array> arrays) {
		this.arrays = arrays;
	}

	public void setBoxes(List<Box> boxes) {
		this.boxes = boxes;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void setGrids(List<Grid> grids) {
		this.grids = grids;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setTabList(Tablist tabList) {
		this.tabList = tabList;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCaption(String caption) {
		this.caption = new Caption(caption);
	}

	public void add(Grid currentGrid) {
		if (currentGrid.getRows() != null && !currentGrid.getRows().getRows().isEmpty()) {
			if (this.grids == null)
				this.grids = new ArrayList<Grid>();

			this.grids.add(currentGrid);
		}
	}

	public void add(List<Grid> grids) {
		if (this.grids == null)
			this.grids = new ArrayList<Grid>();

		this.grids.addAll(grids);
	}

	public void addField(String id, String value) {
		if (fields == null)
			fields = new ArrayList<Field>();
		fields.add(new Field(id, value));
	}

	public void setContainerFieldValue(String fieldName, String value) {
		for (Iterator<Field> iter = getFields().iterator(); iter.hasNext();) {
			Field field = (Field) iter.next();
			if (fieldName.equals(field.getId()))
				field.setValue(value);
		}
	}

	/**
	 * @param string
	 * @return
	 */
	private Array getArray(String type) {
		if (arrays != null && !arrays.isEmpty()) {
			for (Iterator<Array> iterator = arrays.iterator(); iterator.hasNext();) {
				Array array = iterator.next();
				if (type.equals(array.getType())) {
					return array;
				}
			}
		}
		return null;
	}

	public State createState() {
		State state = new State();
		state.setAction(getFieldValue(ACTION));
		state.setAlpha1(getFieldValue(ALPHA1));
		state.setAlpha2(getFieldValue(ALPHA2));
		state.setFunction(getFieldValue(FUNCTION));
		state.setLangId(getFieldValue(LANGID));
		state.setNumeric1(getFieldValue(NUMERIC1));
		state.setNumeric2(getFieldValue(NUMERIC2));
		state.setRequestCode(getFieldValue(REQUESTCODE));
		state.setScreen(getFieldValue(SCREEN));
		state.setThread(getFieldValue(THREAD));
		return state;
	}

	public void fillRequestCodeList(IBackingBean backingBean, boolean newMethod) {
		ButtonBackingBean bbb = new ButtonBackingBean();

		// bbb.setButtonsNotToRender(backingBean.getButtonsnottorender() == null
		// ? new ArrayList<String>()
		// : backingBean.getButtonsnottorender());
		// bbb.setButtonsToRender(
		// backingBean.getButtonstorender() == null ? new ArrayList<String>() :
		// backingBean.getButtonstorender());
		Map<String, Button> buttonsMap = new HashMap<String, Button>();
		Vector<Button> buttons = new Vector<Button>();

		/*
		 * add the links
		 */
		Array linkArray = getArray("link");
		/*
		 * set the linkTitle
		 */
		if (linkArray != null) {
			bbb.getNavigation().setLinkTitle(linkArray.getValue());
			for (Iterator<Item> iter = linkArray.getArrayItems().iterator(); iter.hasNext();) {
				Item item = (Item) iter.next();

				Navigation navigationNode = new Navigation();
				navigationNode.setRequestCode(item.getId().toLowerCase());
				navigationNode.setType("link");
				navigationNode.setLabel(item.getValue());

				// bbb.getNavigation().getRequestcode()
				// .add(new NavigationNode(item.getId().toLowerCase(), "link",
				// item.getValue()));
				bbb.getNavigation().getRequestcodes().add(navigationNode);
			}
		}

		/*
		 * add the buttons
		 */
		Array buttonArray = getArray("button");
		for (Item item : buttonArray.getArrayItems()) {
			Button btn = new Button(BTN + item.getId(), item.getId(), item.getValue());
			buttonsMap.put(item.getId(), btn);
			buttons.add(btn);
		}

		// if (buttons != null && !buttons.isEmpty()) {
		// if (buttonsMap.get(BROWSE) != null) {
		// backingBean.setHasBrowseButton(true);
		// } else {
		// backingBean.setHasBrowseButton(false);
		// }
		// } else {
		// backingBean.setHasBrowseButton(false);
		// }
		// backingBean.setButtons(buttons);
		bbb.setButtonsMap(buttonsMap);
		bbb.setButtons(buttons);

		backingBean.setButtonBackingBean(bbb);
	}

	// public ButtonBackingBean fillRequestCodeList(IBackingBean backingBean) {
	// ButtonBackingBean bbb = new ButtonBackingBean();
	//
	// bbb.setButtonsNotToRender(backingBean.getButtonsnottorender() == null ?
	// new ArrayList<String>()
	// : backingBean.getButtonsnottorender());
	// bbb.setButtonsToRender(
	// backingBean.getButtonstorender() == null ? new ArrayList<String>() :
	// backingBean.getButtonstorender());
	// Map<String, Button> buttonsMap = new HashMap<String, Button>();
	// Vector<Button> buttons = new Vector<Button>();
	//
	// /*
	// * add the links
	// */
	// Array linkArray = getArray("link");
	// /*
	// * set the linkTitle
	// */
	// if (linkArray != null) {
	// bbb.getNavigation().setLinkTitle(linkArray.getValue());
	// for (Iterator<Item> iter = linkArray.getArrayItems().iterator();
	// iter.hasNext();) {
	// Item item = (Item) iter.next();
	//
	// bbb.getNavigation().getRequestcode()
	// .add(new NavigationNode(item.getId().toLowerCase(), "link",
	// item.getValue()));
	//
	// }
	// }
	//
	// /*
	// * add the buttons
	// */
	// Array buttonArray = getArray("button");
	// for (Item item : buttonArray.getArrayItems()) {
	// Button btn = new Button(BTN + item.getId(), item.getId(),
	// item.getValue());
	// buttonsMap.put(item.getId(), btn);
	// buttons.add(btn);
	// }
	//
	// if (buttons != null && !buttons.isEmpty()) {
	// if (buttonsMap.get(BROWSE) != null) {
	// backingBean.setHasBrowseButton(true);
	// } else {
	// backingBean.setHasBrowseButton(false);
	// }
	// } else {
	// backingBean.setHasBrowseButton(false);
	// }
	// // backingBean.setButtons(buttons);
	// bbb.setButtonsMap(buttonsMap);
	// bbb.setButtons(buttons);
	//
	// return bbb;
	// }

	public void fillScreenFields(IBackingBean backingBean, boolean newMethod) {
		Field field = getField(XLSPTH);
		if (field != null && field.getValue() != null && !"".equals(field.getValue())) {
			backingBean.setXlspth(field.getValue());
		}

		if (getFields() != null && !getFields().isEmpty()) {
			for (Field field2 : getFields()) {
				String id = field2.getId().toLowerCase();

				/*
				 * FIELDS
				 */
				try {
					com.md.mdcms.rest.model.StringField pageField = getPageField(backingBean, field2, true);
					BeanUtils.copyProperties(pageField, field2);
					// if (!backingBean.hasBrowseButton()) {
					// pageField.setBrowse(FALSE);
					// }
					BeanUtils.setProperty(backingBean, id, pageField);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
	}

	// public void fillScreenFields(IBackingBean backingBean) {
	// Field field = getField(XLSPTH);
	// if (field != null && field.getValue() != null &&
	// !"".equals(field.getValue())) {
	// backingBean.setXlspth(field.getValue());
	// }
	//
	// if (getFields() != null && !getFields().isEmpty()) {
	// for (Field field2 : getFields()) {
	// String id = field2.getId().toLowerCase();
	//
	// /*
	// * FIELDS
	// */
	// try {
	// IHtmlField pageField = getPageField(backingBean, field2);
	// BeanUtils.copyProperties(pageField, field2);
	// // if (!backingBean.hasBrowseButton()) {
	// // pageField.setBrowse(FALSE);
	// // }
	// BeanUtils.setProperty(backingBean, id, pageField);
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	// }
	// }

	private com.md.mdcms.rest.model.StringField getPageField(IBackingBean backingBean, Field field, boolean newMethod) {
		com.md.mdcms.rest.model.StringField pageField = null;
		java.lang.reflect.Field bbField;
		String id = field.getId().toLowerCase();
		try {
			bbField = backingBean.getClass().getDeclaredField(id);
			Class<?> bbFieldClass = bbField.getType();
			if (bbFieldClass == com.md.mdcms.rest.model.StringField.class) {
				pageField = new com.md.mdcms.rest.model.StringField();
				// } else if (bbFieldClass == NumberField.class) {
				// pageField = new NumberField();
				// } else if (bbFieldClass == TimeField.class) {
				// pageField = new TimeField();
				// // } else if (bbFieldClass == AmountField.class) {
				// // pageField = new AmountField();
				// } else if (bbFieldClass == DateField.class) {
				// pageField = new DateField();
				// ((DateField) pageField).setValue(field.getValue());
				// } else if (bbFieldClass == CheckboxField.class) {
				// pageField = new CheckboxField();
			} else {
				pageField = new com.md.mdcms.rest.model.StringField();
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return pageField;
	}

	private IHtmlField getPageField(IBackingBean backingBean, Field field) {
		IHtmlField pageField = null;
		java.lang.reflect.Field bbField;
		String id = field.getId().toLowerCase();
		try {
			bbField = backingBean.getClass().getDeclaredField(id);
			Class<?> bbFieldClass = bbField.getType();
			if (bbFieldClass == StringField.class) {
				pageField = new StringField();
			} else if (bbFieldClass == NumberField.class) {
				pageField = new NumberField();
			} else if (bbFieldClass == TimeField.class) {
				pageField = new TimeField();
				// } else if (bbFieldClass == AmountField.class) {
				// pageField = new AmountField();
			} else if (bbFieldClass == DateField.class) {
				pageField = new DateField();
				((DateField) pageField).setValue(field.getValue());
			} else if (bbFieldClass == CheckboxField.class) {
				pageField = new CheckboxField();
			} else {
				pageField = new StringField();
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return pageField;
	}

	public Grid getGrid(String gridId) {
		for (Iterator<Grid> iter = grids.iterator(); iter.hasNext();) {
			Grid grid = (Grid) iter.next();
			if (gridId.equals(grid.getId())) {
				return grid;
			}
		}
		return null;
	}

	private Item getItem(String id) {
		for (Iterator<Item> iter = items.iterator(); iter.hasNext();) {
			Item item = (Item) iter.next();
			if (id.equals(item.getId()))
				return item;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.model.IContainer#setTabList(java.util.Vector)
	 */
	public void setTabList(Vector<Field> tabList) {
		// TODO Auto-generated method stub

	}

	public void addGrids(Vector<Grid> grids) {
		// TODO Auto-generated method stub

	}

	public Vector<Field> getSingleFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setArray(Vector<Field> array) {
		// TODO Auto-generated method stub

	}

	public void setGrids(Vector<Grid> grids) {
		// TODO Auto-generated method stub

	}

	public void setSingleFields(Vector<Field> singleFields) {
		// TODO Auto-generated method stub

	}

}
