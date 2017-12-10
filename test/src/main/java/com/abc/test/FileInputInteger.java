package com.abc.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileInputInteger {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		String fileName = "D://F.txt";
		//count next line;
		int numberCountNextLineStore[] = new int[1];
		
		
		///compute
		// Numbers in F
		int numberCountStore[] = new int[1];
		// Numbers unique in F
		Map<Integer, Integer> mapCountNumber = new LinkedHashMap<>();
		// length longest line in F
		int numberMaxlengthStore[] = new int[1];
		// Sum even in line
		Map<Integer, Integer> mapLineNumberSumEven = new LinkedHashMap<>();
		// Check true all even in line.
		Map<Integer, Boolean> mapLineNumberEvenAll = new LinkedHashMap<>();
		//
		Stream<String> stream = Files.lines(Paths.get(fileName));
		stream.forEach(new A(numberCountNextLineStore, 
				numberCountStore, 
				mapCountNumber, 
				numberMaxlengthStore, 
				mapLineNumberSumEven,
				mapLineNumberEvenAll));
		System.out.println("Total Numbers in F: " + numberCountStore[0]);
		System.out.println("______________");
		System.out.println("Numbers unique in F: ");
		for (Integer number : mapCountNumber.keySet()) {
			if (mapCountNumber.get(number) < 2) {
				System.out.print(number + " ");
			}
		}
		System.out.println("\n______________");
		System.out.println("The length of the longest line of F: " + numberMaxlengthStore[0]);
		System.out.println("______________");
		System.out.println("The sum of even numbers in line L: ");
		for (Integer number : mapLineNumberSumEven.keySet()) {
			System.out.println("Line " + number + " :" + mapLineNumberSumEven.get(number));
		}
		System.out.println("______________");
		System.out.println("True if all elements of line L are even");
		for (Integer number : mapLineNumberEvenAll.keySet()) {
			System.out.println("Line " + number + " all elements of line are even : " + mapLineNumberEvenAll.get(number));	
		}
	}

	public static void print(String x) {
		System.out.println(x);
	}
	public static class A implements Consumer<String> {

		//count next line;
		int numberCountNextLineStore[];
		// compute
		//
		private int numberCountStore[];
		//
		private Map<Integer, Integer> mapCountNumber;
		//
		private int numberMaxlengthStore[];
		//
		private Map<Integer, Integer> mapLineNumberSumEven;
		//
		private Map<Integer, Boolean> mapLineNumberEvenAll;
		public A(int numberCountNextLineStore[],
				int[] numberCountStore, 
				Map<Integer, Integer> mapCountNumber,
				int numberMaxlengthStore[],
				Map<Integer, Integer> mapLineNumberSumEven,
				Map<Integer, Boolean> mapLineNumberEvenAll) {
			this.numberCountNextLineStore = numberCountNextLineStore;
			this.numberCountStore = numberCountStore;
			this.mapCountNumber = mapCountNumber;
			this.numberMaxlengthStore = numberMaxlengthStore;
			this.mapLineNumberSumEven = mapLineNumberSumEven;
			this.mapLineNumberEvenAll = mapLineNumberEvenAll;
		}
		
		@Override
		public void accept(String t) {
			
			int line = numberCountNextLineStore[0] + 1;
			numberCountNextLineStore[0] = line;
			
			String[] nums = t.replaceAll("[\\s]", "") .split(",");
			//
			numberCountStore[0] = numberCountStore[0] + nums.length;
			//
			int sumEvent = 0;
			boolean allEvenInLine = true;
			for (String num : nums) {
				Integer number = Integer.valueOf(num);
				Integer count = mapCountNumber.get(number);
				if (count == null) {
					mapCountNumber.put(number, 1);
				} else {
					mapCountNumber.put(number, count + 1);
				}
				if (number % 2 == 0) {
					sumEvent += number;
				} else {
					allEvenInLine = false;
				}
			}
			//
			if (numberMaxlengthStore[0] < t.length()) {
				numberMaxlengthStore[0] = t.length();
			}
			//
			mapLineNumberSumEven.put(line, sumEvent);
			mapLineNumberEvenAll.put(line, allEvenInLine);
		}
	}
}
