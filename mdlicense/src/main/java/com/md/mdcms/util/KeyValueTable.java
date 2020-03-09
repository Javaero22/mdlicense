package com.md.mdcms.util;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class KeyValueTable {

	private Map<String, String> map;

	public KeyValueTable() {
		map = new HashMap<String, String>();
	}

	public KeyValueTable(String[] keys, String[] values)
			throws InvalidParameterException {
		this();
		if (keys == null || values == null) {
			throw new InvalidParameterException(
					"both keys and values must be not null");
		}
		if (keys.length < 1 || values.length < 1) {
			throw new InvalidParameterException(
					"both keys and values length must be > 0");
		}
		if (keys.length != values.length) {
			throw new InvalidParameterException(
					"array size for keys and values must be equal");
		}
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
	}

	public int size() {
		return map.size();
	}

	public void add(String key, String value) {
		map.put(key, value);
	}

	public String getValue(String key) {
		return map.get(key);
	}

}
