package com.redmintie.steelplate.util.array;

public class Stack<E> extends LinkedArray<E> {
	public void push(E value) {
		Element elem = new Element(value);
		elem.next = root.next;
		if (elem.next == null) {
			last = elem.next;
		}
		root.next = elem;
		size++;
	}
	@SuppressWarnings("unchecked")
	public E pop() {
		Element elem = root.next;
		root.next = elem.next;
		if (root.next == null) {
			last = root;
		}
		return (E)elem.value;
	}
}