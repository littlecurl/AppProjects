package com.example.dao;

import com.example.bean.User;

public interface UserDao {
	public User getUserByUsernameAndPassword(String username,String password);
}
