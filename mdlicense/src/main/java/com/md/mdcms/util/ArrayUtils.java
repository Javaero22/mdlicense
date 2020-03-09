package com.md.mdcms.util;

import java.lang.reflect.Array;

public class ArrayUtils {

	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static Object concat(Object fromArray, Object arr2) {
		int len1 = (fromArray == null) ? (-1) : Array.getLength(fromArray);

		if (len1 <= 0) {
			return arr2;
		}

		int len2 = (arr2 == null) ? (-1) : Array.getLength(arr2);

		if (len2 <= 0) {
			return fromArray;
		}

		Class<?> commonComponentType = commonClass(fromArray.getClass()
				.getComponentType(), arr2.getClass().getComponentType());
		Object newArray = Array.newInstance(commonComponentType, len1 + len2);
		System.arraycopy(fromArray, 0, newArray, 0, len1);
		System.arraycopy(arr2, 0, newArray, len1, len2);

		return newArray;
	}

	public static Object remove(Object from, Object what) {
		int len1 = (what == null) ? (-1) : Array.getLength(what);

		if (len1 <= 0) {
			return from;
		}

		int len2 = (from == null) ? (-1) : Array.getLength(from);

		if (len2 <= 0) {
			return what;
		}

		Class<?> commonComponentType = commonClass(what.getClass()
				.getComponentType(), from.getClass().getComponentType());

		int k = 0;
		if (len1 == len2) {
			return null;
		} else {
			Object newArray = Array.newInstance(commonComponentType, len2
					- len1);
			for (int i = 0; i < len2; i++) {
				boolean canAdd = true;
				for (int j = 0; j < len1; j++) {

					Object obj1 = Array.get(what, j);

					Object obj2 = Array.get(from, i);

					if (obj2.equals(obj1)) {
						canAdd = false;
					}
				}
				if (canAdd) {
					System.arraycopy(from, i, newArray, k++, 1);
				}
			}
			return newArray;
		}
	}

	public static Object moveUp(Object what, Object from) {
		int len1 = (what == null) ? (-1) : Array.getLength(what);

		if (len1 <= 0) {
			return from;
		}

		int len2 = (from == null) ? (-1) : Array.getLength(from);

		if (len2 <= 0) {
			return what;
		}

		Class<?> commonComponentType = commonClass(what.getClass()
				.getComponentType(), from.getClass().getComponentType());

		int k = 0;
		if (len1 == len2) {
			return from;
		} else {
			// Object newArray = Array.newInstance(commonComponentType, len2 -
			// len1);
			boolean breakFlag = false;
			for (int i = 0; i < len1; i++) {
				if (breakFlag) {
					break;
				}
				for (int j = 0; j < len2; j++) {

					Object obj1 = Array.get(what, i);
					Object obj2 = Array.get(from, j);

					/*
					 * if(!obj1.equals(obj2)){ System.arraycopy(from, j,
					 * newArray, k++, 1); }
					 */
					if (obj1.equals(obj2)) {
						if (j == 0) {
							breakFlag = true;
							break;
						}
						Object prevObjet = Array.get(from, j - 1);
						Array.set(from, j, prevObjet);
						Array.set(from, j - 1, obj2);
					}
				}
			}
			return from;
		}
	}

	public static Object moveDown(Object what, Object from) {
		int len1 = (what == null) ? (-1) : Array.getLength(what);

		if (len1 <= 0) {
			return from;
		}

		int len2 = (from == null) ? (-1) : Array.getLength(from);

		if (len2 <= 0) {
			return what;
		}

		Class<?> commonComponentType = commonClass(what.getClass()
				.getComponentType(), from.getClass().getComponentType());

		int k = 0;
		if (len1 == len2) {
			return from;
		} else {
			// Object newArray = Array.newInstance(commonComponentType, len2 -
			// len1);
			boolean breakOuterLoop = false;
			for (int i = len1 - 1; i >= 0; i--) {
				if (breakOuterLoop)
					break;
				for (int j = 0; j < len2; j++) {

					Object obj1 = Array.get(what, i);
					Object obj2 = Array.get(from, j);

					if (obj1.equals(obj2)) {
						if (j + 1 < len2) {
							Object nextObjet = Array.get(from, j + 1);
							Array.set(from, j, nextObjet);
							Array.set(from, j + 1, obj2);
							break;
						} else {
							breakOuterLoop = true;
						}
					}
				}
			}
			return from;
		}
	}

	public static Object concat(Object arr1, Object arr2, Object arr3) {
		return concat(new Object[] { arr1, arr2, arr3 });
	}

	public static Object concat(Object[] arrs) {
		int totalLen = 0;
		Class<?> commonComponentType = null;
		for (int i = 0, len = arrs.length; i < len; i++) {
			// skip all null arrays
			if (arrs[i] == null) {
				continue;
			}

			int arrayLen = Array.getLength(arrs[i]);

			// skip all empty arrays
			if (arrayLen == 0) {
				continue;
			}

			totalLen += arrayLen;

			Class<?> componentType = arrs[i].getClass().getComponentType();
			commonComponentType = (commonComponentType == null) ? componentType
					: commonClass(commonComponentType, componentType);
		}

		if (commonComponentType == null) {
			return null;
		}

		return concat(Array.newInstance(commonComponentType, totalLen),
				totalLen, arrs);
	}

	public static Class commonClass(Class c1, Class c2) {
		if (c1 == c2) {
			return c1;
		}

		if ((c1 == Object.class) || c1.isAssignableFrom(c2)) {
			return c1;
		}

		if (c2.isAssignableFrom(c1)) {
			return c2;
		}

		if (c1.isPrimitive() || c2.isPrimitive()) {
			// REVISIT: we could try to autoconvert to Object or something
			// appropriate
			throw new IllegalArgumentException("incompatible types " + c1
					+ " and " + c2);
		}

		// REVISIT: we could try to find a common supper class or interface
		return Object.class;
	}
}
