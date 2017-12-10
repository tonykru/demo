package com.abc.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.abc.test.structure.HashTable;
import com.abc.test.structure.Item;
import com.abc.test.structure.HashTable.Element;
import com.abc.test.util.sort.MergeSort;

/**
 * 
 * @author thanh
 *
 */
public class App {

	public static final String OUT_PUT_HEADER = "PHONE_NUMBER,REAL_ACTIVATION_DATE \n";
	public static final String OUT_PUT_PATH = "D:/text_out.csv";
	public static final String IN_PUT_PATH = "D:/text.csv";

	public static void main(String[] args) throws IOException {

		HashTable data = read(IN_PUT_PATH);
		write(data, OUT_PUT_PATH);
	}

	/**
	 * read from input file and insert to hash table
	 * @param inputPath
	 * @return
	 * @throws IOException
	 */
	public static HashTable read(String inputPath) throws IOException {

		FileInputStream fis = new FileInputStream(inputPath);
		BufferedReader r = new BufferedReader(new InputStreamReader(fis));
		File outPut = new File(OUT_PUT_PATH);
		if (outPut.exists()) {
			outPut.delete();
		}
		String line;
		HashTable hashTable = new HashTable();
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			createItem(hashTable, line);
		}
		fis.close();
		return hashTable;
	}

	/**
	 * search and write output
	 * @param data
	 * @param outputPath
	 * @throws IOException
	 */
	public static void write(HashTable data, String outputPath) throws IOException {

		File outPut = new File(outputPath);
		if (outPut.exists()) {
			outPut.delete();
		}
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPut)));
		Element[] elements = data.getElements();
		w.write(OUT_PUT_HEADER);
		for (int i = 0; i < elements.length; i++) {
			Element e = elements[i];
			Item item = (Item) e.getValue();
			MergeSort.mergeSort(item.getActivationDate(), 0, item.getActivationDateSize());
			MergeSort.mergeSort(item.getDeActivationDate(), 0, item.getDeActivationDateSize());
			w.write("'" + item.getPhoneNumber() + ", " + searchRealActivationDate(item) + "\n");
		}
		w.flush();
		w.close();
	}

	/**
	 * find real activation Date </br>
	 * if (deactionvation not exists) return minimum activation date </br>
	 * if (deactionvation exists) return (minimum activation date) < (maximum deactivation date)
	 * @param item
	 * @return
	 */
	private static String searchRealActivationDate(Item item) {

		if (item.getActivationDateSize() == 0) {
			return "";
		}
		String maxActivationDate = item.getActivationDate()[item.getActivationDateSize() - 1];
		String minActivationDate = item.getActivationDate()[0];
		String maxDeActivationDate = null;
		if (item.getDeActivationDateSize() > 0) {
			maxDeActivationDate = item.getDeActivationDate()[item.getDeActivationDateSize() - 1];
		}
		if (maxDeActivationDate == null) {
			return maxActivationDate;
		}
		if (maxActivationDate.compareTo(maxDeActivationDate) < 0) {
			return "";
		}
		if (minActivationDate.compareTo(maxDeActivationDate) > 0) {
			return minActivationDate;
		}
		for (int i = 0; i < item.getActivationDateSize(); i++) {
			String activationDate = item.getActivationDate()[i];
			if (maxDeActivationDate.compareTo(activationDate) < 0) {
				return item.getActivationDate()[i];
			}
		}
		return "";
	}

	/**
	 * process line data input
	 * @param hashTable
	 * @param line
	 */
	private static void createItem(HashTable hashTable, String line) {

		if (line.contains("PHONE_NUMBER")) {
			return;
		}
		String[] lineDatas = line.replaceAll("['\r\n\t\\s]", "").split(",");
		if (lineDatas.length < 2) {
			return;
		}
		String phoneNumber = lineDatas[0];
		String activateDate = lineDatas[1];
		String deActivateDate = "";
		if (lineDatas.length > 2) {
			deActivateDate = lineDatas[2];
		}
		Item item = (Item) hashTable.get(phoneNumber);
		if (item == null) {
			item = new Item();
			item.setPhoneNumber(phoneNumber);
			hashTable.put(phoneNumber, item);
		}
		item.addActivationDate(activateDate);
		if (!"".equals(deActivateDate)) {
			item.addDeActivationDate(deActivateDate);
		}
	}
}
