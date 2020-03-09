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

import com.md.mdcms.backingbean.IBackingBean;
import com.md.mdcms.base.IConstants;
import com.md.mdcms.bean.IBean;

public class Grid extends Model implements IConstants {

	private static final long serialVersionUID = -2199479032605819025L;

	private static final String SELECTION_INT = "selectionInt";
	private static final String LINKLIST = "linklist";

	private static final String STRING_FIELD = "StringField";
	private static final String NUMBER_FIELD = "NumberField";
	private static final String TIME_FIELD = "TimeField";
	// private static final String AMOUNT_FIELD = "AmountField";
	private static final String DATE_FIELD = "DateField";
	private static final String CHECKBOX_FIELD = "CheckboxField";

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(Grid.class);

	@Attribute
	private String id;

	@Attribute(required = false)
	private String maxindex;

	@Attribute(required = false)
	private String pagenr;

	@Attribute(required = false)
	private String maxpages;

	@Attribute(required = false)
	private String moreRows;

	@Element(required = false)
	private Header header;

	@Element(required = false)
	private Rows rows;

	public Grid() {
		super();
	}

	public Grid(String id, Header header, Rows rows) {
		super();
		this.id = id;
		this.header = header;
		this.rows = rows;
	}

	public Grid(String gridId) {
		this.id = gridId;
	}

	public Header getHeader() {
		return header;
	}

	public String getId() {
		return id;
	}

	public Rows getRows() {
		return rows;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRows(Rows rows) {
		this.rows = rows;
	}

	public String getMaxpages() {
		return maxpages;
	}

	public String getPagenr() {
		return pagenr;
	}

	public void setMaxpages(String maxpages) {
		this.maxpages = maxpages;
	}

	public void setPagenr(String pagenr) {
		this.pagenr = pagenr;
	}

	/**
	 * @return the maxindex
	 */
	public String getMaxindex() {
		return maxindex;
	}

	/**
	 * @param maxindex
	 *            the maxindex to set
	 */
	public void setMaxindex(String maxindex) {
		this.maxindex = maxindex;
	}

	/**
	 * @return the moreRows
	 */
	public String getMoreRows() {
		return moreRows;
	}

	/**
	 * @param moreRows
	 *            the moreRows to set
	 */
	public void setMoreRows(String moreRows) {
		this.moreRows = moreRows;
	}

	public void addRows(List<Row> rows) {
		if (rows != null && !rows.isEmpty()) {
			if (this.rows == null)
				this.rows = new Rows();

			this.rows.setRows(rows);
		}
	}

	public void addHeader(List<Item> headerItems) {
		if (headerItems != null && !headerItems.isEmpty()) {
			if (this.header == null)
				this.header = new Header();

			this.header.setHeaderItems(headerItems);
		}
	}

	public void fillLinklist(IBackingBean backingBean) {
		// ButtonBackingBean buttonBB = (ButtonBackingBean) JsfSupporter
		// .findManagedBean(ButtonBackingBean.BEAN_NAME
		// + backingBean.getResponseState().getThreadForBean());
		//
		// /*
		// * by default there is no linklist
		// */
		// backingBean.setHasLinklist(false);
		//
		// List<Item> headerItems = getHeader().getHeaderItems();
		// if (headerItems != null && !headerItems.isEmpty()) {
		// if (getHeader().getHeaderItems().get(0).getLinklist() != null) {
		//
		// /*
		// * set the linklistTitle
		// */
		// String linklistTitle = getHeader().getHeaderItems().get(0)
		// .getLinklistvalue();
		// buttonBB.getNavigation().setLinklistTitle(linklistTitle);
		//
		// Vector<Field> links = convertToCodeTextPair(getHeader()
		// .getHeaderItems().get(0).getLinklist());
		//
		// for (Iterator<Field> iterator = links.iterator(); iterator
		// .hasNext();) {
		// Field field = (Field) iterator.next();
		//
		// backingBean.getLinklist().add(field.getId());
		//
		// buttonBB.getNavigation()
		// .getLinklist()
		// .add(new NavigationNode(field.getId(), LINKLIST,
		// field.getValue()));
		//
		// }
		//
		// backingBean.setHasLinklist(true);
		// }
		//
		// /*
		// * if there is a list then add the content to the buttons
		// */
		// if (getHeader().getHeaderItems().get(0).getList() != null) {
		//
		// Vector<Field> btns = convertToCodeTextPair(getHeader()
		// .getHeaderItems().get(0).getList());
		//
		// // Vector<String> linkasbtn = backingBean.getLinksasbuttons();
		// // Map<String, Button> buttonsMap = new HashMap<String,
		// // Button>();
		// // Vector<Button> buttons = new Vector<Button>();
		// Map<String, Button> buttonsMap = buttonBB.getButtonsMap();
		// Vector<Button> buttons = buttonBB.getButtons();
		//
		// for (Iterator<Field> iterator = btns.iterator(); iterator
		// .hasNext();) {
		// Field field = (Field) iterator.next();
		//
		// // buttonBB.getLinklist().add(field.getId());
		//
		// Button btn = new Button(BTN + field.getId(), field.getId(),
		// field.getValue());
		// buttonsMap.put(field.getId(), btn);
		// buttons.add(btn);
		// }
		//
		// /*
		// * put the existing buttons to the end
		// */
		// // if (!buttonsMap.isEmpty()) {
		// // Collection<Button> btns = buttonBB.getButtons().values();
		// // Vector<Button> btns = buttonBB.getButtons();
		// // for (Iterator<Button> iterator = btns.iterator(); iterator
		// // .hasNext();) {
		// // Button button = (Button) iterator.next();
		// // buttons.add(button);
		// // }
		// // backingBean.setButtons(buttons);
		// buttonBB.setButtonsMap(buttonsMap);
		// buttonBB.setButtons(buttons);
		// // }
		//
		// backingBean.setHasLinklist(true);
		// }
		// }
	}

	public void fillHeaderItems(IBackingBean backingBean) {
		if (getHeader() != null) {
			List<Item> headerItems = getHeader().getHeaderItems();
			String id;
			if (headerItems != null && !headerItems.isEmpty()) {
				for (Iterator<Item> iter = headerItems.iterator(); iter.hasNext();) {
					Item item = (Item) iter.next();
					id = item.getId().toLowerCase();

					/*
					 * FIELDS
					 */
					try {
						IHtmlField pageField = getPageField(backingBean, item);
						BeanUtils.copyProperties(pageField, item);
						BeanUtils.setProperty(backingBean, id, pageField);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
	}

	private static Vector<Field> convertToCodeTextPair(String list) {
		String[] listEntry = list.split(",");
		Vector<Field> linkList = new Vector<Field>();
		for (int i = 0; i < listEntry.length; i++) {
			String[] codeText = listEntry[i].split("=");
			linkList.add(new Field(codeText[0], codeText[1]));
		}
		return linkList;
	}

	public void fillRowResponseValues(IBackingBean masterBackingBean, IBackingBean gridBackingBean, IBean gridBean,
			Grid grid) {

		List baseList = new ArrayList();

		int r = -1;
		// if (masterBackingBean.hasFilter()) {
		// try {
		// r++;
		// BeanUtils.setProperty(gridBean, "rownr", "0");
		// BeanUtils.setProperty(gridBean, "selectionInt", "0");
		// baseList.add(BeanUtils.cloneBean(gridBean));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		int selectedRow = -1;

		List<Row> rows = getRows().getRows();
		if (rows != null && !rows.isEmpty()) {

			if (rows.size() == 1) {
				selectedRow = r + 1;
			}

			Map<String, Object> properties = new HashMap<String, Object>();

			String id;

			if (rows != null && !rows.isEmpty()) {
				for (Row row : rows) {

					properties.clear();

					r++;

					/* row nr value if not null else count (r) */
					if (row.getNr() != null) {
						properties.put(ROWNR, row.getNr());
					} else {
						properties.put(ROWNR, r);
					}
					properties.put(SELECTION_INT, r);

					List<Item> items = row.getRowItems();
					for (Item item : items) {

						id = item.getId().toLowerCase();

						if (SFLOPT.equals(id.toUpperCase())) {
							if (TRUE.equals(item.getFocus())) {
								selectedRow = r;
							}
						}
						/*
						 * FIELDS
						 */
						try {
							IHtmlField pageField = getPageField(gridBean, item);
							BeanUtils.setProperty(gridBean, item.getId().toLowerCase(), pageField);
						} catch (Exception e) {
							// LOG.fatal(e);
							// e.printStackTrace();
						}
					}

					try {
						BeanUtils.populate(gridBean, properties);
						baseList.add(BeanUtils.cloneBean(gridBean));
					} catch (Exception e) {
						// LOG.fatal("setRowResponseValues -> Exception ", e);
						// e.printStackTrace();
					}

				} // for
			}
		}

		gridBackingBean.setSelectionInt(selectedRow);
		gridBackingBean.setList(baseList);
	}

	private IHtmlField getPageField(IBackingBean bean, Item item) {
		IHtmlField pageField = null;
		java.lang.reflect.Field bbField;
		String id = item.getId().toLowerCase();
		try {
			bbField = bean.getClass().getDeclaredField(id);
			if (bbField.getType().toString().endsWith(STRING_FIELD)) {
				pageField = new StringField();
			} else if (bbField.getType().toString().endsWith(NUMBER_FIELD)) {
				pageField = new NumberField();
			} else if (bbField.getType().toString().endsWith(TIME_FIELD)) {
				pageField = new TimeField();
				// } else if
				// (bbField.getType().toString().endsWith(AMOUNT_FIELD)) {
				// pageField = new AmountField();
			} else if (bbField.getType().toString().endsWith(DATE_FIELD)) {
				pageField = new DateField();
				((DateField) pageField).setValue(item.getValue());
			} else if (bbField.getType().toString().endsWith(CHECKBOX_FIELD)) {
				pageField = new CheckboxField();
			} else {
				pageField = new StringField();
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
		}
		return pageField;
	}

	private IHtmlField getPageField(IBean bean, Item item) {
		IHtmlField pageField = null;
		java.lang.reflect.Field bbField;
		String id = item.getId().toLowerCase();
		try {
			bbField = bean.getClass().getDeclaredField(id);
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
			} else if (bbFieldClass == CheckboxField.class) {
				pageField = new CheckboxField();
			} else {
				pageField = new StringField();
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
		}
		item.asField(pageField);
		return pageField;
	}

}
