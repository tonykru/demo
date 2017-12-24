package com.abc.test.caro;

import java.util.Scanner;

/**
 * 
 * @author thanh
 *
 */
public class Caro {

	private int size = 10;

	private String current = "O";

	private int typingTrues = 0;

	private int numberToWin = 5;

	private String winner;

	private boolean isExit;

	private char[][] content;

	public Caro(int size) {

		this.size = size;
		init();
	}

	public Caro() {

		init();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public char[][] getContent() {
		return content;
	}

	public int getNumberToWin() {
		return numberToWin;
	}

	public void setNumberToWin(int numberToWin) {
		this.numberToWin = numberToWin;
	}

	public String getWinner() {
		return winner;
	}

	public boolean isExit() {
		return isExit;
	}

	private void init() {

		if (size > 99) {
			size = 99;
		}
		content = new char[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				content[i][j] = ' ';
			}
		}
	}

	public void print() {

		if (content == null) {
			return;
		}
		System.out.print("        ");
		for (int i = 0; i < size; i++) {
			System.out.print((i < 10 ? "0" + i : i) + " ");
		}
		System.out.print("\n");
		for (int i = 0; i < size; i++) {
			System.out.println();
			for (int j = 0; j < size; j++) {

				if (j == 0) {
					System.out.print((i < 10 ? "0" + i : i) + "     |");
				}
				System.out.print(content[j][i] + " |");
			}
		}
		System.out.println();
	}

	public boolean[] typing(String xo, int i, int j) {

		if (checkTrueTyping(xo, i, j)) {
			content[i][j] = xo.charAt(0);
			current = xo;
			typingTrues++;
			boolean isWin = checkWin(xo, i, j);
			if (isWin) {
				winner = xo;
				isExit = true;
			} else {
				isExit = checkEquals();
			}
			print();
			return new boolean[] { true, isWin };
		}
		return new boolean[] { false, false };
	}

	private boolean checkTrueTyping(String xo, int i, int j) {

		if (isExit) {
			if (winner != null) {
				System.out.println(winner + " is Winner, please again!");

			} else {
				System.out.println("O and X is Equals, please again!");
			}

			return false;
		}

		if (!"O".equalsIgnoreCase(xo) && !"X".equalsIgnoreCase(xo)) {
			return false;
		}

		if (typingTrues != 0 && current.equals(xo)) {
			if ("X".equalsIgnoreCase(xo)) {
				System.out.println("O please input!");
			} else {
				System.out.println("X please input!");
			}

			return false;
		}
		if (i >= size || j >= size) {
			return false;
		}
		if (i < 0 || j < 0) {
			return false;
		}
		if (content[i][j] != ' ') {
			return false;
		}
		return true;
	}

	boolean checkWin(String xo, int i, int j) {

		// +- 2 cell
		int startX = i - numberToWin / 2;
		int startY = j - numberToWin / 2;
		for (int ii = 0; ii < numberToWin; ii++) {

			if (startX >= 0 && startX < size && checkWinAt(xo, startX, j)) {
				return true;
			}
			if (startY >= 0 && startY < size && checkWinAt(xo, i, startY)) {
				return true;
			}
			if (startX >= 0 && startY >= 0 && startX < size && startY < size && checkWinAt(xo, startX, startY)) {
				return true;
			}
			startX++;
			startY++;
		}
		return false;
	}

	boolean checkWinAt(String xo, int i, int j) {

		// continue +- 2 cell
		boolean x = true;
		boolean y = true;
		boolean xy = true;
		int startX = i - numberToWin / 2;
		int startY = j - numberToWin / 2;
		for (int ii = 0; ii < numberToWin; ii++) {

			// checkX
			if (startX < 0 || startX >= size || content[startX][j] != xo.charAt(0)) {
				x = false;
			}
			// checkY
			if (startY < 0 || startY >= size || content[i][startY] != xo.charAt(0)) {
				y = false;
			}

			// checkXY
			if (startX < 0 || startY < 0 || startX >= size || startY >= size
					|| content[startX][startY] != xo.charAt(0)) {
				xy = false;
			}
			startX++;
			startY++;
		}
		if (x || y || xy) {
			System.out.println(xo + " WIN");
		}
		return x || y || xy;
	}

	boolean checkEquals() {

		if (typingTrues == size * size) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Caro caro = new Caro(15);
		while (true) {

			String line = scanner.nextLine();
			if (!line.trim().matches("[OoXx],[0-9]{1,2},[0-9]{1,2}")) {
				System.out.println("Input again! (Ex: o,1,2)");
				continue;
			}
			String[] ins = line.replaceAll("[\\s]", "").split(",");
			if (ins.length < 3) {
				System.out.println("Input again! ");
				continue;
			}
			if (!caro.typing(ins[0].toUpperCase(), Integer.valueOf(ins[1]), Integer.valueOf(ins[2]))[0]) {
				System.out.println("Input again! ");
				continue;
			} else {
				if (caro.isExit()) {
					break;
				}
			}
		}
	}
}
