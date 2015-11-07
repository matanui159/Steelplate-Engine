package com.redmintie.steelplate.util;

import java.util.Iterator;

public class Map<K, V> implements Iterable<K> {
	private Array<K> keys = new Array<K>();
	private Array<V> values = new Array<V>();
	public V set(K key, V value) {
		int index = keys.indexOf(key);
		if (index == -1) {
			index = keys.add(key);
		}
		return values.set(index, value);
	}
	public V get(Object key) {
		return values.get(keys.indexOf(key));
	}
	public Array<K> getKeys() {
		return keys;
	}
	public Array<V> getValues() {
		return values;
	}
	public boolean containsKey(Object key) {
		return keys.indexOf(key) != -1;
	}
	public boolean containsValue(Object value) {
		return values.indexOf(value) != -1;
	}
	public V remove(Object key) {
		int index = keys.remove(key);
		return values.remove(index);
	}
	public void clear() {
		keys.clear();
		values.clear();
	}
	@Override
	public Iterator<K> iterator() {
		return keys.iterator();
	}
}