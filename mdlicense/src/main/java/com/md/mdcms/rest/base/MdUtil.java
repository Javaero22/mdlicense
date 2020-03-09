package com.md.mdcms.rest.base;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.rest.model.IBackingBean;
import com.md.mdcms.rest.model.StringField;

public class MdUtil implements IConstants {

	public static Object getField(IBackingBean backingBean, String fieldName) {
		java.lang.reflect.Field f;
		try {
			f = backingBean.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(backingBean);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NullPointerException e) {

		}
		return null;
	}

	public static boolean isRequestImportant(StringField pageField) {
		String editable = pageField.getEditable();
		String visible = pageField.getVisible();

		if (editable != null && TRUE.equals(editable)) {
			return true;
		} else if (visible != null && FALSE.equals(visible)) {
			return true;
			// } else if (getUpdate()) {
			// return true;
		} else {
			return false;
		}
	}

}
