<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <fmt:bundle basename="message">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" href="../style/styleSheet.css">
            <title><fmt:message key="user.authentication" /></title>
        </head>
        <body>
            <%
            String contextPath = request.getContextPath().replace("/", "");
            %>
            <h1><fmt:message key="user.authentication" /></h1>
            <c:if test="${mensagens.existeErros}">
                <div id="erro">
                    <ul>
                        <c:forEach var="erro" items="${mensagens.erros}">
                            <li> ${erro} </li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <div id="loginDiv">
            <form method="post" action="/<%= contextPath%>/auth/login" id="loginForm">
                <table>
                    <tr>
                        <td colspan="2" align="center"><label for="cliente"><fmt:message key="user.customer" /></label>
                        <input type="radio" id="cliente" name="tipo"
                            value="Cliente" checked/>
                        <label for="locadora"><fmt:message key="rental.title" /></label>
                        <input type="radio" id="locadora" name="tipo"
                            value="Locadora" /></td></tr>
                    <tr>
                        <th><fmt:message key="user.email" /> </th>
                        <td><input type="text" name="email"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="user.password" /> </th>
                        <td><input type="password" name="senha" /></td>
                    </tr>
                    <tr>
                        <td colspan="2"> 
                            <input type="submit" name="OK" value="<fmt:message key="login.link"/>">
                        </td>
                    </tr>
                </table>
            </form>
            </div>
        </body>
    </fmt:bundle>
    
</html>