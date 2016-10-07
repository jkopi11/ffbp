<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lineups</title>
</head>
<body>

<div class="container">
	<table class="table-hover">
		<tr>
			<th>Site</th>
			<th>Name</th>
			<th>Projected Score</th>
		</tr>
	<c:forEach items="${rows}" var="row">
		<tr>
			<td><c:out value="${row.siteName}"/></td>
			<td><c:out value="${row.teamName}"/></td>
			<td><c:out value="${row.projectedScore}"/></td>
		</tr>
	</c:forEach>
	</table>
</div>
</body>
<link rel="stylesheet" type="text/css" href="../resources/bootstrap.min.css"/>
</html>