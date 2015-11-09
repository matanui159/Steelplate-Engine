package com.redmintie.steelplate.util.array;

import java.util.Iterator;

public class LinkedArray<E> implements Array<E> {
	protected Element root = new Element(null);
	protected Element last = root;
	protected int size;
	private ArrayIterator iterator;
	private boolean compare(Element a, Object b, int hash) {
		if (a == null) {
			return false;
		} else if (a.value == null) {
			return b == null;
		} else {
			return a.value.hashCode() == hash && a.value.equals(b);
		}
	}
	@Override
	public int add(E value) {
		last.next = new Element(value);
		last = last.next;
		return size++;
	}
	@Override
	public E set(int i, E value) {
		throw new RuntimeException("Array.set() not supported by LinkedArray.");
	}
	@Override
	@SuppressWarnings("unchecked")
	public E get(int i) {
		Element elem = root.next;
		for (int j = 0; j < i; j++) {
			if (elem == null) {
				return null;
			}
			elem = elem.next;
		}
		return elem == null ? null : (E)elem.value;
	}
	@Override
	public int indexOf(Object value) {
		int hash = value == null ? 0 : value.hashCode();
		Element elem = root.next;
		int i = 0;
		while (!compare(elem, value, hash)) {
			if (elem == null) {
				return -1;
			}
			elem = elem.next;
			i++;
		}
		return i;
	}
	@Override
	public int size() {
		return size;
	}
	@Override
	@SuppressWarnings("unchecked")
	public E remove(int i) {
		Element prev = root;
		Element elem = root.next;
		for (int j = 0; j < i; j++) {
			if (elem == null) {
				return null;
			}
			prev = elem;
			elem = elem.next;
		}
		if (elem == null) {
			return null;
		}
		prev.next = elem.next;
		if (prev.next == null) {
			last = prev;
		}
		size--;
		return (E)elem.value;
	}
	@Override
	public int remove(Object value) {
		int hash = value == null ? 0 : value.hashCode();
		Element prev = root;
		Element elem = root.next;
		int i = 0;
		while (!compare(elem, value, hash)) {
			if (elem == null) {
				return -1;
			}
			prev = elem;
			elem = elem.next;
			i++;
		}
		prev.next = elem.next;
		if (prev.next == null) {
			last = prev;
		}
		size--;
		return i;
	}
	@Override
	public void clear() {
		root.next = null;
		last = root;
		size = 0;
	}
	@Override
	public Iterator<E> iterator() {
		if (iterator == null) {
			iterator = new ArrayIterator();
		}
		iterator.elem = root;
		return iterator;
	}
	protected class Element {
		public Object value;
		public Element next;
		public Element(Object value) {
			this.value = value;
		}
	}
	private class ArrayIterator implements Iterator<E> {
		private Element elem;
		@Override
		public boolean hasNext() {
			return elem.next != null;
		}
		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			return hasNext() ? (E)(elem = elem.next).value : null;
		}
	}
}
