<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: patricktoner
  Date: 4/13/18
  Time: 10:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Player Profile</title>
</head>
<body>

    <h1>${viewModel.first} ${viewModel.last}</h1>

    Team: <a href="/team/show?id=${viewModel.teamId}">${viewModel.teamName}</a> <br />

    Hometown: ${viewModel.hometown} <br />

    Positions:
    <c:forEach items="${viewModel.positions}" var="position">
        <a href="/position/show?id=${position.id}">${position.name}</a>
    </c:forEach>


</body>
</html>
