<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<s:i18n name="viewResources.login">
	<!DOCTYPE html>
	<html>
		<head>
		<title><s:text name="loginPage" /></title>
		</head>
		<body>
			<h2><s:text name="h2" /></h2>
			<s:form>
				<s:textfield name="username" key="username"/>
				<s:textfield name="password" key="password"/>
				<s:submit key="login" />
			</s:form>
		</body>
	</html>
</s:i18n>