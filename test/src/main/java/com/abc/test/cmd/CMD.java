package com.abc.test.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CMD {

	private static String currentDir = System.getProperty("user.home").replaceAll("[\\\\]", "/");
	private final static Scanner s = new Scanner(System.in);
	private static boolean isWindows = System.getenv("OS").contains("Windows");

	public static void main(String... a) throws Exception {

		exec("echo #############Java Terminal#############");
		run();
	}
	
	/**
	 * run multi command type input with stop by \q
	 * @throws Exception
	 */
	public static synchronized void run() throws Exception {
		while (true) {
			if (!exec()) {
				exec("echo #############Stop Terminal#############");
				break;
			}
		}
	}
	
	/**
	 * execution command from type input.
	 * @return
	 * @throws Exception
	 */
	public static synchronized boolean exec() throws Exception {

		System.out.print(/*); exec("echo " + */System.getenv("USERNAME").toLowerCase() + "@" + System.getProperty("sun.desktop") + ":" + currentDir + "$ ");
		Object[] reads = null;
		while (true) {
			reads = scan();
			if ((boolean) reads[0]) {
				break;
			}
			if (reads[1] != null) {
				currentDir = reads[1] + "";
			}
			System.out.print(/*); exec("echo " + */System.getenv("USERNAME").toLowerCase() + "@" + System.getProperty("sun.desktop") + ":" + currentDir + "$ ");
		}
		if ("\\q".equals(reads[1])) {
			return false;
		}

		String cmd = (String) reads[1];
		return exec(cmd, null);
	}
	
	/**
	 * execution with hard command and current dir 
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	public static synchronized boolean exec(String cmd) throws Exception {
		return exec(cmd, null);
	}
	
	/**
	 * execution with hard command and dir 
	 * @param cmd
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public static synchronized boolean exec(String cmd, String dir) throws Exception {

		List<String> commands = buildCommand(cmd.trim().split("[\\s]"));
		ProcessBuilder builder = new ProcessBuilder(commands);
		if (dir != null) {
			builder.directory(new File(dir));
		} else {
			builder.directory(new File(currentDir));
		}

		builder.redirectErrorStream(true);
		Process p = null;
		try {
			p = builder.start();
		} catch (Exception e) {
			System.out.println(commands);
			System.out.println(e.getMessage());
			return true;
		}

		final InputStream ins = p.getInputStream();
		new Thread(new Runnable() {
			public void run() {
				BufferedReader input = new BufferedReader(new InputStreamReader(ins));
				String line = null;
				try {
					while ((line = input.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		p.waitFor();
		return true;
	}

	/**
	 * Console scan type input
	 * @return
	 */
	private static Object[] scan() {
		String cmd = s.nextLine();
		String[] cmds = cmd.trim().split("[\\s]+");
		if (cmds.length == 0) {
			return new Object[] { false, null };
		}
		if ("cd".equals(cmds[0])) {
			if (cmds.length == 1) {
				return new Object[] { false, currentDir };
			}

			if ("..".equals(cmds[1])) {
				if (currentDir.indexOf("/") == -1) {
					return new Object[] { false, currentDir };
				}
				return new Object[] { false, currentDir.substring(0, currentDir.lastIndexOf("/")) + "/"};
			}
			if ("/".equals(cmds[1]) || "\\".equals(cmds[1])) {
				if (currentDir.indexOf("/") == -1) {
					return new Object[] { false, currentDir };
				}
				return new Object[] { false, currentDir.substring(0, currentDir.indexOf("/"))  + "/" };
			}

			String dir = getDir(cmds[1]);
			File f = new File(dir);
			if (f.isDirectory()) {
				return new Object[] { false, dir };
			} else {
				System.out.println("Cannot find folder " + dir);
				return new Object[] { false, null };
			}
		}
		return new Object[] { true, cmd };
	}

	/**
	 * for cd command
	 * @param dir
	 * @return
	 */
	private static String getDir(String dir) {

		if (isWindows) {
			if (dir.matches("[a-zA-Z]:[\\\\/].*")) {
				return dir.replaceAll("[/]{2,}", "/");
			}
			dir = (currentDir + "/" + dir.replaceAll("\\\\", "/")).replaceAll("[/]{2,}", "/");
			return dir;
		} else {
			if (dir.startsWith("/")) {
				return dir.replaceAll("[/]{2,}", "/");
			}
			dir = (currentDir + "/" + dir.replaceAll("\\\\", "/")).replaceAll("[/]{2,}", "/");
			return dir;
		}
	}

	private static List<String> buildCommand(String... cmds) {
		List<String> commands = new ArrayList<>();
		if (isWindows && (cmds.length < 2 || !cmds[0].equalsIgnoreCase("cmd") || !cmds[1].equalsIgnoreCase("/c"))) {
			commands.add("cmd");
			commands.add("/c");
		}
		for (String cmd : cmds) {
			commands.add(cmd);
		}
		return commands;
	}
}
