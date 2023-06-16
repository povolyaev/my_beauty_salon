<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p><c:out value = "login: ${sessionScope.get('login')}"/><br>
<c:out value = "email: ${sessionScope.get('email')}"/><br>
<form method="post" action="http://localhost:8080/beauty_salon/service">
    <button>Услуги</button>
</form>
</body>
</html>