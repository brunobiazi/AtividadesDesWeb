<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <fmt:bundle basename="message">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title><fmt:message key="page.title" /></title>
        </head>
        <body>
            <a href="locadora/lista"><fmt:message key="page.go" /></a>
        </body>
    </fmt:bundle>
</html>