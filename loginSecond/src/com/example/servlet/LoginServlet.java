package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bean.User;
import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * Http传过来的请求，全部封装到了req对象里面了
		 * 设置请求的编码为utf-8
		 */
		req.setCharacterEncoding("utf-8");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("用户名："+username+"\n密码："+password);
		
		/**
		 * 通过text/html进行解析；并且设置字符编码为utf-8
		 */
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUserByUsernameAndPassword(username, password);
		if(user == null){
			out.println("<h1><font color='red'>Login Faild！登录失败！</font></h1>");
		} else {
			out.println("<h1>Login Success！登录成功！</h1>");
		}
		
	}
	
}
