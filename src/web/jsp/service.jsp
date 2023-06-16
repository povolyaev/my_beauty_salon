<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p><c:out value = "Услуги пользователя ${sessionScope.get('login')}"/>: <c:forEach var="service" items="${services}"><br><c:out value="${service}"/>
</c:forEach></p>
</body>
</html>