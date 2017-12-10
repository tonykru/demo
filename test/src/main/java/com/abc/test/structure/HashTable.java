package com.abc.test.structure;

import java.util.Arrays;

/**
 * 
 * @author thanh
 *
 */
public class HashTable {

	private static final int MAX_ARRAY_SIZE = 2147483639;
	Element[] elements = new Element[10];
	
	private int capacity = 10;
	private int count = 0;

	public Object get(String key) {

		if (capacity == 0) {
			return null;
		}
		int hash = hashCode(key);
		while (elements[hash] != null) {
			Element e = elements[hash];
			if (e.getKey().equals(key)) {
				return e.getValue();
			}
			hash++;
			hash = (hash & 0x7FFFFFFF) % capacity;
		}
		return null;
	}

	private int hashCode(String key) {
		return (key.hashCode() & 0x7FFFFFFF) % capacity;
	}

	public void put(String key, Object value) {

		if (count >= elements.length) {
			rehash();
		}
		int hash = hashCode(key);
		while (elements[hash] != null) {
			hash++;
			hash = (hash & 0x7FFFFFFF) % capacity;
		}
		elements[hash] = new Element(key, value);
		count++;
	}

	private void rehash() {

		int oldCapacity = elements.length;
		int newCapacity = (oldCapacity << 1) + 1;
		if (newCapacity - MAX_ARRAY_SIZE > 0) {
			if (elements.length == MAX_ARRAY_SIZE)
				return;
			newCapacity = MAX_ARRAY_SIZE;
		}
		capacity = newCapacity;
		Element[] oldElements = elements;
		Element[] newElements = new Element[newCapacity];
		elements = newElements;
		for (int i = oldCapacity; i-- > 0;) {
			Element e = oldElements[i];
			if (e != null) {
				int hash = hashCode(e.key);
				while (elements[hash] != null) {
					hash++;
					hash = (hash & 0x7FFFFFFF) % capacity;
				}
				elements[hash] = e;
			}
		}
	}

	public Element[] getElements() {
		
		Element[] values = new Element[count];
		int j = 0;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null) {
				values[j++] = elements[i];
			}
		}
		return values;
	}

	public static class Element {

		private String key;
		private Object value;

		public Element(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "Element [key=" + key + ", value=" + value + "]";
		}

	}

	public int size() {
		return count;
	}
	
	@Override
	public String toString() {
		return "HashTable [elements=" + Arrays.toString(elements) + ", count=" + count + "]";
	}
}
