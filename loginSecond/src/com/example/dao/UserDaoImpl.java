package com.example.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.example.bean.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UserDaoImpl implements UserDao{

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		User user = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/mydb";
			String sql_user = "root";
			String sql_passwd = "123456";
			Connection conn = (Connection) DriverManager.getConnection(url,sql_user,sql_passwd);
			
			String sql = "select id,username,password from usertable where username = ? and password = ?";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				
			}
			//下面这个一点要写，否则生成User类的实例的时候，会一直空指针
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
