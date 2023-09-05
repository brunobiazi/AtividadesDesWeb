<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
    <fmt:bundle basename="message">
		<head>
		<title><fmt:message key="rentalscompany.list" /></title>
		<script src="${pageContext.request.contextPath}/script/filter.js"></script>
		<link rel="stylesheet" href="../style/styleSheet.css">
		</head>
		<body>

			<%@include file="cabecalho.jsp"%>
			<div align="center">
				<h1><fmt:message key="rentalscompany.welcome" /></h1>
				<h2>
					<a href="/<%=contextPath%>"><fmt:message key="main.link" /></a> &nbsp;&nbsp;&nbsp; 
					<!-- <a href="/<%= contextPath%>/locadora/cadastro"><fmt:message key="rentalscompany.create" /></a> -->
					<c:if test="${usuarioLogado.cliente.admin == 1}"><a href="/<%= contextPath%>/locadora/cadastro"><fmt:message key="rentalscompany.create" /></a></c:if>
					
						
				</h2>
			</div>

			<div align="center"><form id="filtro">
				<input type="text" class="filtro" id="cidade"onkeyup="filtro(4, 'cidade')" placeholder="<fmt:message key='rental.search'/>">
				<!-- <input type="text" class="filtro" id="cidade"onkeyup="filtro(4, 'cidade')" placeholder="Pesquisar por cidade"> -->
				<input type="reset" value="Limpar Filtro" onclick="limpar()">
				</form></div>

			<div align="center">
				<table id="tabela">
					<caption><fmt:message key="rentalscompany.list" /></caption>
					<tr>
						<th><fmt:message key="rental.ID" /></th>
						<th><fmt:message key="rental.email" /></th>
						<th><fmt:message key="rental.cnpj" /></th>
						<th><fmt:message key="rental.name" /></th>				
						<th><fmt:message key="rental.city" /></th>
						<c:if test="${usuarioLogado.cliente.admin == 1}">
						<th><fmt:message key="actions.link" /></th>
						</c:if>	
					</tr>
					<c:forEach var="locadora" items="${requestScope.listaLocadora}">
						<tr>
							<td>${locadora.id}</td>
							<td>${locadora.email}</td>
							<td>${locadora.cnpj}</td>
							<td>${locadora.nome}</td>
							<td>${locadora.cidade}</td>
							<c:if test="${usuarioLogado.cliente.admin == 1}">
							<td><a href="/<%= contextPath%>/locadora/edicao?id=${locadora.id}" class="acoes"><fmt:message key="page.edit" /></a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a class="acoes"
									href="/<%= contextPath%>/locadora/remove?id=${locadora.id}"
									onclick="return confirm('Tem certeza de que deseja excluir esta locadora? Excluira tambem todas as locacaos desta locadora');">
									<fmt:message key="rentalscompany.delete" /></a>
							</td>
							</c:if>
							
						</tr>
					</c:forEach>
				</table>
			</div>
		</body>
	</fmt:bundle>
</html>