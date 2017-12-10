package com.abc.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc.test.structure.HashTable;

import junit.framework.TestCase;

/**
 * 
 * @author thanh
 *
 */
public class AppTest extends TestCase {

	private static final String INPUT_PATH = System.getProperty("user.home") + "/inputTest.csv";
	private static final String OUTPUT_PATH = System.getProperty("user.home") + "/outputTest.csv";

	public void testApp() throws IOException {
		insertTest();
		HashTable data = App.read(INPUT_PATH);
		App.write(data, OUTPUT_PATH);

		File output = new File(OUTPUT_PATH);
		FileInputStream fis = new FileInputStream(output);
		BufferedReader r = new BufferedReader(new InputStreamReader(fis));
		String line;
		String phoneNumber = "";
		String activationDate = "";
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			if (!line.contains("PHONE_NUMBER")) {
				String[] lineDatas = line.replaceAll("['\r\n\t\\s]", "").split(",");
				phoneNumber = lineDatas[0];
				activationDate = lineDatas[1];
			}
		}
		fis.close();
		assertTrue("0987000001".equals(phoneNumber) && "2016-06-01".equals(activationDate));
	}

	/**
	 * create input test
	 * @throws IOException
	 */
	private void insertTest() throws IOException {

		File outPut = new File(INPUT_PATH);
		if (outPut.exists()) {
			outPut.delete();
		}
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPut)));
		w.write("PHONE_NUMBER,REAL_ACTIVATION_DATE \n");
		w.write("'0987000001,	2016-01-01,	\n");
		w.write("'0987000001,	2016-03-01,	2016-05-01\n");
		w.write("'0987000001,	2016-06-01,	\n");
		w.write("'0987000001,	2016-09-01,	\n");
		w.write("'0987000001,	2016-12-01,	\n");
		w.flush();
		w.close();
	}
	
	public static void main(String[] args) {
		List<String> s = Arrays.asList("a","s","g","h");
		for (String string : s) {
			System.out.println(string);
		}
		
		Map<String, String> a = new HashMap<>();
		a.put("1", "1");
		System.out.println(new ArrayList<>(a.keySet()));
		abc("123");
	}
	
	public static void abc(String x) {
		System.out.println(x);
	}
}
