package com.abc.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ListInteger {

	public static void main(String[] args) throws IOException {

		List<Integer> listNumber = Arrays.asList(33,33,33,33);
		//List<Integer> listNumber = Arrays.asList(1);
		
		///compute
		// sumSquares in F
		double sumSquaresStore[] = new double[1];
		// sum >= 25
		int sumgreater25Store[] = new int[1];
		// max in F
		int maxElementStore[] = new int[1];
		// Large even
		int maxEventStore[] = new int[1];
		// Check true all even
		boolean[] trueAllOddStore = new boolean[] {true};
		//
		listNumber.stream().forEach(new A(
				sumSquaresStore, 
				sumgreater25Store, 
				maxElementStore, 
				maxEventStore,
				trueAllOddStore));
		System.out.println("Sum squares of L: " + sumSquaresStore[0]);
		System.out.println("Sum element of L that greater than 25: " + sumgreater25Store[0]);
		System.out.println("The maximum element of L : " + maxElementStore[0]);
		System.out.println("The largest even number in L : " + maxEventStore[0]);
		System.out.println("True if any element of L is odd : " + trueAllOddStore[0]);
		
	}

	public static class A implements Consumer<Integer> {

		double sumSquaresStore[] ;
		// sum > 25
		int sumGreater25Store[] = new int[1];
		// max in F
		int maxElementStore[] = new int[1];
		// Large even
		int maxEventStore[] = new int[1];
		// Check true all even
		boolean trueAllOddStore[] ;
		public A(double[] sumSquaresStore,
				int[] sumGreater25Store, 
				int[] maxElementStore,
				int[] maxEventStore,
				boolean[] trueAllOddStore) {
		
			this.sumSquaresStore = sumSquaresStore;
			this.sumGreater25Store = sumGreater25Store;
			this.maxElementStore = maxElementStore;
			this.maxEventStore = maxEventStore;
			this.trueAllOddStore = trueAllOddStore;
		}
		
		@Override
		public void accept(Integer t) {
			
			sumSquaresStore[0] = sumSquaresStore[0] + Math.sqrt(Double.valueOf(t));
			if (t >= 25) {
				sumGreater25Store[0] = sumGreater25Store[0] + t;
			}
			if (maxElementStore[0] < t) {
				maxElementStore[0] = t;
			}
			if (t % 2 == 0) {
				if (maxEventStore[0] < t) {
					maxEventStore[0] = t;
				}
				trueAllOddStore[0] = false;
			}
		}
	}
}
	