package com.abc.test.util.sort;

/**
 * Chia làm 2, sort từng phần, merge lại, đệ quy. 
 * @author thanh
 *
 */
public class MergeSort {

	public static void main(String[] args) {

		String[] input = new String[] {};
		mergeSort(input);
		for (int i = 0; i < input.length; i++) {
			System.out.print(input[i] + " ");
		}
	}

	public static void mergeSort(String[] input) {
		mergeSort(input, new String[input.length], 0, input.length);
	}

	public static void mergeSort(String[] input, int fromIndex, int toIndex) {
		mergeSort(input, new String[toIndex], fromIndex, toIndex);
	}

	public static void mergeSort(String[] input, String[] temp, int l, int h) {

		if (h <= l + 1) {
			return;
		}
		int m = (l + h) / 2;
		mergeSort(input, temp, l, m);
		mergeSort(input, temp, m, h);
		merge(input, temp, l, m, h);
	}

	public static void merge(String[] input, String[] temp, int l, int m, int h) {

		for (int i = l, ll = l, hh = m; i < h; i++) {

			if (hh >= h || ll < m && input[ll] != null && input[hh] != null && input[ll].compareTo(input[hh]) < 0) {
				temp[i] = input[ll++];
			} else {
				temp[i] = input[hh++];
			}
		}
		// copy temp to input
		for (int i = l; i < h; i++) {
			input[i] = temp[i];
		}
	}
}
