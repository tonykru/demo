package com.abc.test.something;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.abc.test.util.sort.BubbleSort;

public class CClassLoader extends ClassLoader {

	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) throws Exception {
		Class<BubbleSort> bb1 = BubbleSort.class;
		Class<BubbleSort> bb2 = (Class<BubbleSort>) new CClassLoader().findClass("com.abc.test.util.sort.BubbleSort");
		System.out.println(bb2);
		System.out.println(bb1 == bb2);

		Method m = bb2.getDeclaredMethod("bubbleSort", int[].class);
		int[] src = new int[] { 2, 1, 4, 1, 6, 3 };
		m.invoke(null, src);
		System.out.println(Arrays.toString(src));
	}

	public Class<?> findClass(String name) {

		System.out.println(name);
		try {
			String cName = name.replace(".", "/") + ".class.show";
			cName = cName.replace("util/sort", "something");
			byte[] b = loadClassData(cName);
			return defineClass(name, b, 0, b.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	@SuppressWarnings({ "static-access", "restriction" })
	public CClassLoader() {
		super(com.sun.nio.zipfs.ZipPath.class.getClassLoader().getSystemClassLoader());
	}
	
	public Class<?> loadClass(String name) {

		try {
			if (name.startsWith("java")) {
				return super.	(name);
			}
			String cName = name.replace(".", "/") + ".class.show";
			cName = cName.replace("util/sort", "something");
			byte[] b = loadClassData(cName);
			Class<?> c = defineClass(name, b, 0, b.length);
			resolveClass(c);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	private byte[] loadClassData(String name) throws IOException {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		getClass().getClassLoader().getResourceAsStream("");
		int size = stream.available();
		byte buff[] = new byte[size];
		DataInputStream in = new DataInputStream(stream);
		in.readFully(buff);
		in.close();
		return buff;
	}
}
