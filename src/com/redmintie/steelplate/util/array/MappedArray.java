package com.redmintie.steelplate.util.array;

import java.util.Arrays;
import java.util.Iterator;

public class MappedArray<E> implements Array<E> {
	private Object[] array = new Object[1];
	private int i;
	private int size;
	private ArrayIterator iterator;
	@Override
	public int add(E value) {
		while (i < array.length && array[i] != null) {
			i++;
		}
		if (i == array.length) {
			array = Arrays.copyOf(array, array.length + 1);
		}
		array[i] = value;
		if (value != null) {
			size++;
		}
		return i++;
	}
	@Override
	@SuppressWarnings("unchecked")
	public E set(int i, E value) {
		if (i >= 0) {
			if (i >= array.length) {
				array = Arrays.copyOf(array, i + 1);
			}
			Object old = array[i];
			array[i] = value;
			if (old == null && value != null) {
				size++;
			}
			if (old != null && value == null) {
				size--;
			}
			return (E)old;
		}
		return null;
	}
	@Override
	@SuppressWarnings("unchecked")
	public E get(int i) {
		if (i >= 0 && i < array.length) {
			return (E)array[i];
		}
		return null;
	}
	@Override
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		int hash = value.hashCode();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].hashCode() == hash && array[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	@Override
	public int size() {
		return size;
	}
	@Override
	@SuppressWarnings("unchecked")
	public E remove(int i) {
		if (i >= 0 && i < array.length) {
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
		return null;
	}
	@Override
	public int remove(Object value) {
		int i = indexOf(value);
		remove(i);
		return i;
	}
	@Override
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