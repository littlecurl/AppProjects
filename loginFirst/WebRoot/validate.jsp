<%@ page language="java" pageEncoding="UTF-8" import="java.sql.*"%>
<jsp:useBean id="MySQLDB" scope="page" class="org.easybooks.test.jdbc.MySQLDBConn" />
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>验证页面</title>
    </head>
    <body>
        <%
        	request.setCharacterEncoding("UTF-8");
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            out.println(username);
            out.println(password);
            boolean validated=false;

            String sql="select * from usertable";
            ResultSet rs=MySQLDB.executeQuery(sql);
            while(rs.next()){
                if((rs.getString("username").trim().compareTo(username)==0)&&(rs.getString("password").compareTo(password)==0)){
                    
                	validated=true;
                }
            }
            rs.close();
            MySQLDB.closeStmt();
            MySQLDB.closeConn();
            if(validated){
                //验证成功跳转到main.jsp
        %>
                <jsp:forward page="main.jsp"/>
        <%
            }else{
                //验证失败跳转到error.jsp
        %>
                <jsp:forward page="error.jsp"/>
        <%
            }
        %>
    </body>
</html>
