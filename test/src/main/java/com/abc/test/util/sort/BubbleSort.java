package com.abc.test.util.sort;

import java.util.Arrays;

/**
 * 
 * @author thanh
 *
 */
public class BubbleSort {

	public static void main(String[] args) {

		int[] src = new int[] {2,3,1,4,6,33,7};
		bubbleSort(src);
		System.out.println(Arrays.toString(src));
	}

	/**
	 * sau mỗi lần lặp (i), số lớn nhất có thể sẽ lên đầu.
	 * vòng lặp j sẽ move số lớn nhất có thể lên trên.
	 * @param src
	 */
	public static void bubbleSort(int[] src) {

		for (int i = 0; i < src.length - 1; i++) {
			for (int j = 0; j < src.length - 1 - i; j++) {
				if (src[j] > src[j + 1]) {
					int temp = src[j];
					src[j] = src[j + 1];
					src[j + 1] = temp;
				}
			}
		}
	}

}
