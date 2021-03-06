<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<html>
<head>
<title>JSTL sql:query Tag</title>
</head>
<body>

<sql:query dataSource="jdbc/ffbp" var="result">
SELECT *
FROM players
LEFT JOIN projections
ON players.P_ID=projections.P_ID ORDER BY projections.ESPN_PROJ DESC;
</sql:query>
 
<div class="container">
	<table class="table-hover">
		<tr>
			<th>Player ID</th>
			<th>Name</th>
			<th>Week</th>
			<th>Projection</th>
		</tr>
	<c:forEach var="row" items="${result.rows}">
		<tr>
			<td><c:out value="${row.P_ID}"/></td>
			<td><c:out value="${row.PNAME}"/></td>
			<td><c:out value="${row.WEEK}"/></td>
			<td><c:out value="${row.ESPN_PROJ}"/></td>
		</tr>
	</c:forEach>
	</table>
</div>


</body>
<link rel="stylesheet" type="text/css" href="../resources/bootstrap.min.css"/>
</html>