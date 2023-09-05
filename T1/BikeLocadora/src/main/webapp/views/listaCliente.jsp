<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
	<fmt:bundle basename="message">
		<head>
			<title><fmt:message key="users.list"/></title>
			<script src="${pageContext.request.contextPath}/script/filter.js"></script>
			<link rel="stylesheet" href="../style/styleSheet.css">
			</head>
			<body>
			
			
				<%@include file="cabecalho.jsp"%>
				<div align="center">
					<h1><fmt:message key="users.welcome"/></h1>
					<h2>
						<a href="/<%=contextPath%>"><fmt:message key="main.link"/></a> &nbsp;&nbsp;&nbsp; <a
							href="/<%= contextPath%>/cliente/cadastro"><fmt:message key="users.create"/></a>
					</h2>
				</div>
			
				<!-- <div align="center"><form id="filtro">
					<input type="text" class="filtro" id="id" onkeyup="filtro(0,'id')" placeholder="Pesquisar por id">
					<input type="text" class="filtro" id="email" onkeyup="filtro(1,'email')" placeholder="Pesquisar por email">
					<input type="text" class="filtro" id="cpf"onkeyup="filtro(2, 'cpf')" placeholder="Pesquisar por cpf">
					<input type="text" class="filtro" id="nome"onkeyup="filtro(3, 'nome')" placeholder="Pesquisar por nome">
					<input type="text" class="filtro" id="telefone"onkeyup="filtro(4, 'telefone')" placeholder="Pesquisar por telefone">
					<input type="text" class="filtro" id="sexo"onkeyup="filtro(5, 'sexo')" placeholder="Pesquisar por sexo">
					<input type="date" class="filtro" id="dataInicio" onchange="filtroData(6)" placeholder="Pesquisar por data">
					<input type="date" class="filtro" id="dataFim" onchange="filtroData(6)" placeholder="Pesquisar por data">
					<input type="reset" value="Limpar Filtro" onclick="limpar()">
					</form></div> -->
			
				<div align="center">
					<table id="tabela">
						<caption><fmt:message key="users.list"/></caption>
						<tr>
							<th><fmt:message key="user.ID"/></th>
							<th><fmt:message key="user.email"/></th>
							<th><fmt:message key="user.cpf"/></th>
							<th><fmt:message key="user.name"/></th>
							<th><fmt:message key="user.phone"/></th>
							<th><fmt:message key="user.gender"/></th>
							<th><fmt:message key="user.date"/></th>
							<th><fmt:message key="actions.link"/></th>
						</tr>
						<c:forEach var="cliente" items="${requestScope.listaCliente}">
							<tr>
								<td>${cliente.id}</td>
								<td>${cliente.email}</td>
								<td>${cliente.cpf}</td>
								<td>${cliente.nome}</td>
								<td>${cliente.telefone}</td>
								<td>${cliente.sexo}</td>
								<td>${cliente.nascimento}</td>
								<td><a href="/<%= contextPath%>/cliente/edicao?id=${cliente.id}" class="acoes"><fmt:message key="page.edit"/></a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a class="acoes"
									href="/<%= contextPath%>/cliente/remove?id=${cliente.id}"
									onclick="return confirm('Tem certeza de que deseja excluir este cliente? Tambem excluira todos as locacaos deste cliente');">
									<fmt:message key="users.delete"/> </a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</body>
	</fmt:bundle>
</html>