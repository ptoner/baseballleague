<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%--
  Created by IntelliJ IDEA.
  User: patricktoner
  Date: 4/13/18
  Time: 10:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <sf:form action="/player/edit" method="post" modelAttribute="commandModel">
        <sf:hidden path="id" />


        First Name: <sf:input path="first" />    <sf:errors path="first" />    <br />
        Last Name:  <sf:input path="last" />     <sf:errors path="last" />     <br />
        Hometown:   <sf:input path="hometown" /> <sf:errors path="hometown" /> <br />

        Team:
        <sf:select path="teamId">
            <sf:option value="" label="No team" />
            <sf:options items="${viewModel.teams}" itemValue="id" itemLabel="name" />
        </sf:select>
        <sf:errors path="teamId" />


        <br />

        Positions:
        <sf:select path="positionIds">
            <sf:options items="${viewModel.positions}" itemValue="id" itemLabel="name" />
        </sf:select>
        <sf:errors path="positionIds" />

        <br />

        <button type="submit">THE BUTTON THAT DOES THE SAVING WHEN WE PUSH IT</button>

    </sf:form>


</body>
</html>
