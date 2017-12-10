package com.abc.test.util.jlib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

/**
 * 
 * 
 * 
 * <pre>
	LINUX
	public port
	sudo vi /etc/redis/redis.conf
	change bind 127.0.0.1 -> 0.0.0.0
	sudo service redis-server restart
	
	
	change maxsize:
	sudo vi /etc/redis/redis.conf
	insert
	maxmemory 1048577
	sudo service redis-server restart
	 or 
	CONFIG SET maxmemory 1mb

	DOWNLOAD
	WIN: https://github.com/MicrosoftArchive/redis/releases
	
	<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
		<dependency>
		    <groupId>redis.clients</groupId>
		    <artifactId>jedis</artifactId>
		    <version>2.9.0</version>
		</dependency>
 * 
 * </pre>
 * 
 * @author thanh
 *
 */
public class Redis {

	private static Properties prop;
	static Jedis jedis = null;
	
	static {
		loadProp();
		jedis = new Jedis(prop.getProperty("redis.host"), Integer.valueOf(prop.getProperty("redis.port")));
		//jedis.auth("");
	}
	
	private static void loadProp() {
		prop = new Properties();
		//prop.put("redis.host", "127.0.0.1");
		prop.put("redis.host", "192.168.253.131");
		prop.put("redis.port", "6379");
	}
	
	public static Object get(String key) {
		return deserialize(jedis.get(SafeEncoder.encode(key)));
	}
	
	public static void put(String key, Object value) {
		jedis.del(key);
		jedis.set(SafeEncoder.encode(key), serialize(value), SafeEncoder.encode("NX"));
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param time time: milliseconds
	 */
	public static void put(String key, Object value, long time) {
		jedis.del(key);
		jedis.set(SafeEncoder.encode(key), serialize(value), SafeEncoder.encode("NX"), SafeEncoder.encode("PX"), time);
	}
	
	
	
	/**
	 * Serialize the given object to a byte array.
	 * @param object the object to serialize
	 * @return an array of bytes representing the object in a portable fashion
	 */
	private static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeUnshared(object);
			oos.flush();
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
		}
		return baos.toByteArray();
	}

	/**
	 * Deserialize the byte array into an object.
	 * @param bytes a serialized object
	 * @return the result of deserializing the bytes
	 */
	private static Object deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return ois.readUnshared();
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Failed to deserialize object", ex);
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Failed to deserialize object type", ex);
		}
		
	}
	
	public static void main(String[] args) {
		
		//put("123", Arrays.asList(1,2,3,4,5,6,7));
		//put("1234", "Anh yêu em");
		System.out.println(get("1234"));
	}
}