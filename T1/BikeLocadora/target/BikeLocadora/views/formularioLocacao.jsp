<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<fmt:bundle basename="message">
	<head>
		<title><fmt:message key="rents.welcome" /></title>
		<link rel="stylesheet" href="../style/styleSheet.css">
	</head>
	<body>
		<%@include file="cabecalho.jsp"%>
		<div align="center">
			<h1><fmt:message key="rents.welcome" /></h1>
			<h2>
				<a href="/<%= contextPath%>/locacao/listaPorCliente"><fmt:message key="rents.list" /></a>
			</h2>
		</div>
		<c:if test="${mensagens.existeErros}">
				<div id="erro">
					<ul>
						<c:forEach var="erro" items="${mensagens.erros}">
							<li> ${erro} </li>
							</c:forEach>
					</ul>
				</div>
		</c:if>
		<div align="center">
			<c:choose>
				<c:when test="${locacao != null}">
					<form action="atualiza" method="post" id="formulario">
						<%@include file="camposLocacao.jsp"%>
					</form>
				</c:when>
				<c:otherwise>
					<form action="insere" method="post" id="formulario">
						<%@include file="camposLocacao.jsp"%>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${!empty requestScope.mensagens}">
			<ul class="erro">
				<c:forEach items="${requestScope.mensagens}" var="mensagem">
					<li>${mensagem}</li>
				</c:forEach>
			</ul>
		</c:if>
	</body>
</fmt:bundle>
</html>