package com.redmintie.steelplate.util.array;

import java.util.Arrays;
import java.util.Iterator;

public class StandardArray<E> implements Array<E> {
	private Object[] array = new Object[1];
	private int size;
	private ArrayIterator iterator;
	@Override
	public int add(E value) {
		if (size == array.length) {
			array = Arrays.copyOf(array, size + 1);
		}
		array[size] = value;
		return size++;
	}
	@Override
	@SuppressWarnings("unchecked")
	public E set(int i, E value) {
		if (i >= 0 && i < size) {
			Object old = array[i];
			array[i] = value;
			return (E)old;
		}
		return null;
	}
	@Override
	@SuppressWarnings("unchecked")
	public E get(int i) {
		if (i >= 0 && i < size) {
			return (E)array[i];
		}
		return null;
	}
	@Override
	public int indexOf(Object value) {
		int hash = value == null ? 0 : value.hashCode();
		for (int i = 0; i < array.length; i++) {
			if (value == null) {
				if (array[i] == null) {
					return i;
				}
			} else if (array[i].hashCode() == hash && array[i].equals(value)) {
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
		if (i >= 0 && i < size) {
			Object value = array[i];
			size--;
			for (int j = i; j < size; j++) {
				array[j] = array[j + 1];
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
			return i < size;
		}
		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			return hasNext() ? (E)array[i++] : null;
		}
	}
}