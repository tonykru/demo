package com.abc.test.util.sort;

import java.util.Arrays;

/**
 * 
 * @author thanh
 *
 */
public class InsertionSort {

	public static void main(String[] args) {

		int[] src = new int[] { 2, 3, 1, 4, 6, 33, 7 };
		insertionSort(src);
		System.out.println(Arrays.toString(src));
	}

	/**
	 * vòng lặp j tìm ra vị trí sắp chưa đúng tại vòng ngoài bằng i
	 * bắt đầu tại j, move số xuống dưới nếu chưa đúng.
	 * @param src
	 */
	public static void insertionSort(int[] src) {

		for (int i = 0; i < src.length; i++) {
			for (int j = i; j > 0; j--) {
				if (src[j] < src[j - 1]) {
					int temp = src[j];
					src[j] = src[j - 1];
					src[j - 1] = temp;
				}
			}
		}
	}
}
