package com.redmintie.steelplate.util.array;

public interface Array<E> extends Iterable<E> {
	public int add(E value);
	public E set(int i, E value);
	public E get(int i);
	public int indexOf(Object value);
	public int size();
	public E remove(int i);
	public int remove(Object value);
	public void clear();
}