package com.abc.test.util.jlib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Workspace;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;

/**
 * download standalone server
 * 
 * http://www.apache.org/dyn/closer.lua/jackrabbit/2.14.4/jackrabbit-standalone-2.14.4.jar
 * 
 * <pre>

	<!-- The JCR API -->
	<dependency>
		<groupId>javax.jcr</groupId>
		<artifactId>jcr</artifactId>
		<version>2.0</version>
	</dependency>

	<!-- Jackrabbit content repository -->
	<dependency>
		<groupId>org.apache.jackrabbit</groupId>
		<artifactId>jackrabbit-core</artifactId>
		<version>2.12.1</version>
	</dependency>

	<!-- Use Log4J for logging -->
	<dependency>
		<groupId>org.slf4j</groupId>
		<artifactId>slf4j-log4j12</artifactId>
		<version>1.5.11</version>
	</dependency>

	<!-- https://mvnrepository.com/artifact/org.apache.jackrabbit/jackrabbit-jcr-rmi -->
	<dependency>
		<groupId>org.apache.jackrabbit</groupId>
		<artifactId>jackrabbit-jcr-rmi</artifactId>
		<version>2.1.0</version>
	</dependency>
 * 
 * </pre>
 * 
 * @author thanh
 *
 */
public class JCR {

	private static Repository repository = null;
	private static Session session = null;
	private static Properties props = null;
	static {
		try {
			props = getProps();
			exec(props);
			repository = new URLRemoteRepository(props.getProperty("jcr.uri"));
			login();
		} catch (Exception e) {
			if (e instanceof NoSuchWorkspaceException) {
				createNewWorkSpace(props.get("jcr.repository").toString());
				try {
					login();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
		}
	}

	private static void login() throws LoginException, NoSuchWorkspaceException, RepositoryException {
		session = repository.login(new SimpleCredentials(props.getProperty("jcr.username"),
				props.getProperty("jcr.password").toCharArray()), props.get("jcr.repository").toString());
	}

	public static void createNewWorkSpace(String name) {

		try {
			Session session = repository.login(new SimpleCredentials(props.getProperty("jcr.username"),
					props.getProperty("jcr.password").toCharArray()));
			Workspace workspace = session.getWorkspace();
			workspace.createWorkspace(name);
		} catch (Exception e) {
			if (e instanceof RepositoryException) {
				System.out.println(e.getMessage());
			} else {
				e.printStackTrace();
			}
		}
	}

	private static void exec(Properties props) throws IOException, InterruptedException {

		String[] args = new String[7];
		args[0] = "java";
		args[1] = "-jar";
		args[2] = props.get("jcr.jackrabbit.standalone").toString();
		args[3] = "-p";
		args[4] = props.get("jcr.port").toString();
		args[5] = "-r";
		args[6] = props.get("jcr.repo").toString();
		ProcessBuilder builder = new ProcessBuilder(args);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			System.out.println(line);
			if (line.contains("Apache Jackrabbit is now running at")) {
				break;
			}
		}
	}

	private static Properties getProps() {

		Properties props = new Properties();
		try {
			InputStream appConfig = Thread.currentThread().getContextClassLoader().getResourceAsStream("props");
			props.load(appConfig);
		} catch (Exception e) {
		}
		if (props.get("jcr.username") == null || props.get("jcr.username").toString().trim().length() == 0) {
			props.put("jcr.username", "admin");
		}
		if (props.get("jcr.password") == null || props.get("jcr.password").toString().trim().length() == 0) {
			props.put("jcr.password", "admin");
		}
		if (props.get("jcr.repo") == null || props.get("jcr.repo").toString().trim().length() == 0) {
			props.put("jcr.repo", "\"D://jcr\"");
		}
		if (props.get("jcr.port") == null || props.get("jcr.port").toString().trim().length() == 0) {
			props.put("jcr.port", "8888");
		}
		if (props.get("jcr.host") == null || props.get("jcr.host").toString().trim().length() == 0) {
			props.put("jcr.host", "http://localhost");
		}
		if (props.get("jcr.jackrabbit.standalone") == null
				|| props.get("jcr.jackrabbit.standalone").toString().trim().length() == 0) {
			props.put("jcr.jackrabbit.standalone", "D:/jackrabbit-standalone.jar");
		}
		if (props.get("jcr.uri") == null || props.get("jcr.uri").toString().trim().length() == 0) {
			props.put("jcr.uri", props.get("jcr.host") + ":" + props.get("jcr.port") + "/rmi");
		}
		if (props.get("jcr.repository") == null || props.get("jcr.repository").toString().trim().length() == 0) {
			props.put("jcr.repository", "content3");
		}

		return props;
	}

	private static Session getSession() {

		if (session != null && session.isLive()) {
			return session;
		}
		try {
			session = repository.login(new SimpleCredentials(props.getProperty("jcr.username"),
					props.getProperty("jcr.password").toCharArray()), props.get("jcr.repository").toString());
			return session;
		} catch (Exception e) {
			return null;
		}
	}

	private static boolean isLive() {

		Session session = getSession();
		if (session == null) {
			System.out.println("Could not connect JCR");
			return false;
		}

		return true;
	}

	public static void putFile(Node nodeParent, String name, String mime, InputStream data) throws RepositoryException {

		if (isLive()) {
			JcrUtils.putFile(nodeParent, getName(name), mime, data);
			session.save();
		}
	}

	public static void putFile(String name, String mime, InputStream data) throws RepositoryException {

		if (isLive()) {
			JcrUtils.putFile(getParentNode(name), getName(name), mime, data);
			session.save();
		}
	}

	public static void putFile(String name, InputStream data) throws RepositoryException {

		if (isLive()) {
			JcrUtils.putFile(getParentNode(name), getName(name), "", data);
			session.save();
		}
	}

	public static void putFile(String name, File absolutePath) throws RepositoryException, FileNotFoundException {

		if (isLive()) {
			JcrUtils.putFile(getParentNode(name), getName(name), "", new FileInputStream(absolutePath));
			session.save();
		}
	}

	public static String getRootPath() throws RepositoryException, FileNotFoundException {

		if (isLive()) {
			return session.getRootNode().getPath();
		}
		return null;
	}

	public static void addFolder(String parent, String folder) throws RepositoryException, FileNotFoundException {

		if (isLive()) {
			Node parentNode = session.getNode(parent);
			JcrUtils.getOrAddFolder(parentNode, folder);
		}
	}

	public static InputStream readFileAsStream(String fileName) throws RepositoryException, FileNotFoundException {

		if (isLive()) {
			Node node = session.getNode(fileName);
			InputStream in = JcrUtils.readFile(node);
			session.save();
			return in;
		}
		return null;
	}
	
	private static Node getParentNode(String name) throws RepositoryException  {
	
		if (isLive()) {
			
			if (!name.contains("/") && !name.contains("\\")) {
				return session.getRootNode();
			}
			String[] nodeNames = name.split("[\\\\/]");
			Node parent = session.getRootNode();
			for (int i = 0; i < nodeNames.length - 1; i++) {
				String nodeName = nodeNames[i];
				parent = JcrUtils.getOrAddFolder(parent, nodeName);
			}
			return parent;
		}
		return null;
	}
	
	private static String getName(String name) {
		
		return name.split("[\\\\/]")[name.split("[\\\\/]").length - 1];
	}

	public static void main(String[] args) throws Exception {

		/*
		 * Repository repository = new URLRemoteRepository("http://localhost:8081/rmi");
		 * Session session = repository.login(new SimpleCredentials("admin",
		 * "admin".toCharArray()), "default"); System.out.println(session.isLive()); if
		 * (session.isLive()) { Node node = session.getRootNode();
		 * System.out.println(session.getRootNode().getPath()); Node test1 =
		 * JcrUtils.getOrAddFolder(node, "TEST1"); JcrUtils.putFile(test1, "XML12", "",
		 * new FileInputStream("D:\\Developer\\Git\\rental\\deploy.bat"));
		 * session.save(); }
		 */
		JCR.createNewWorkSpace(props.get("jcr.repository").toString());
		JCR.putFile("axv/dgdfghd/dfgdg/dfgdf/abc/XML126", new File("D:\\BUS\\Workspaces\\v-key\\abc\\src\\main\\java\\com\\test\\abc\\App.java"));
		InputStream in = JCR.readFileAsStream("/axv/dgdfghd/dfgdg/dfgdf/abc/XML126");
		System.out.println(in);
	}
}