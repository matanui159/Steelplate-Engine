package com.redmintie.steelplate.util;

import java.util.Arrays;
import java.util.Iterator;

public class Array<E> implements Iterable<E> {
	private Object[] array = new Object[2];
	private int i;
	private int size;
	private ArrayIterator iterator;
	public int add(E value) {
		if (value == null) {
			return -1;
		}
		while (i < array.length && array[i] == null) {
			i++;
		}
		if (i == array.length) {
			array = Arrays.copyOf(array, array.length + 2);
		}
		array[i] = value;
		size++;
		return i++;
	}
	@SuppressWarnings("unchecked")
	public E get(int i) {
		if (i >= 0 && i < array.length) {
			return (E)array[i];
		}
		return null;
	}
	public int indexOf(Object value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	public int size() {
		return size;
	}
	@SuppressWarnings("unchecked")
	public E remove(int i) {
		Object value = array[i];
		array[i] = null;
		if (value != null) {
			size--;
		}
		if (i < this.i) {
			this.i = i;
		}
		return (E)value;
	}
	public int remove(Object value) {
		int i = indexOf(value);
		remove(i);
		return i;
	}
	public void clear() {
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
		size = 0;
	}
	@Override
	public Iterator<E> iterator() {
		if (iterator == null) {
			iterator = new ArrayIterator();
		}
		iterator.i = 0;
		return iterator;
	}
	private class ArrayIterator implements Iterator<E> {
		private int i;
		@Override
		public boolean hasNext() {
			while (i < array.length && array[i] == null) {
				i++;
			}
			return i < array.length;
		}
		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			return hasNext() ? (E)array[i++] : null;
		}
	}
}