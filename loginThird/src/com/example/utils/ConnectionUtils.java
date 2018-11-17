package com.example.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 连接工具类
 */
public class ConnectionUtils {
	/**
	 * 首先读取db.properties
	 */
	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static Properties props = new Properties();
	
	/**
	 * 使用线程池，从线程池中获取连接，防止建立多个连接
	 */
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	static { 
		try{
			//类加载器读取文件
			InputStream in = 
				ConnectionUtils.class.getClassLoader().getResourceAsStream("db.properties");
			
			props.load(in);
			 
			driver = props.getProperty("jdbc.Driver");
			url = props.getProperty("jdbc.url");
			username = props.getProperty("jdbc.username");
			password = props.getProperty("jdbc.password");
			//下面这句一定要有，否则会报driver本地变量未被引用
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取连接
	 */
	public static Connection getConn() throws Exception{
		Connection conn = tl.get();
		if(conn == null){
			conn = DriverManager.getConnection(url,username,password);
			tl.set(conn);
		}
		return conn;
	}
	
	/**
	 * 关闭连接
	 */
	public static void closeConn() throws Exception{
		Connection conn = tl.get();
		if(conn != null){
			conn.close();
		}
		tl.set(null);
	}
	
	
	
	
}
