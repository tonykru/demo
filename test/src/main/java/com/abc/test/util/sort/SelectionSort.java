package com.abc.test.util.sort;

import java.util.Arrays;

/**
 * với mỗi lần lặp i tìm vị trí của phần tử nhỏ nhất, đổi chỗ với i
 * 
 * @author thanh
 *
 */
public class SelectionSort {

	public static void main(String[] args) {

		int[] src = new int[] { 2, 3, 1, 4, 6, 33, 7 };
		selectionSort(src);
		System.out.println(Arrays.toString(src));
	}

	public static void selectionSort(int[] src) {

		for (int i = 0; i < src.length; i++) {
			if (i == src.length - 1) {
				break;
			}
			int indexMin = i;
			for (int j = i; j < src.length; j++) {
				if (src[j] < src[indexMin]) {
					indexMin = j;
				}
			}
			if (indexMin != i) {
				int temp = src[i];
				src[i] = src[indexMin];
				src[indexMin] = temp;
			}
		}
	}
}
