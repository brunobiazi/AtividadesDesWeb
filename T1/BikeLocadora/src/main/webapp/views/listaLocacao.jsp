<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
	<fmt:bundle basename="message">
<head>
<title>Lista de Pacotes de Viagem</title>
<script src="${pageContext.request.contextPath}/script/filter.js"></script>
<link rel="stylesheet" href="../style/styleSheet.css">
</head>
<body>

	<%@include file="cabecalho.jsp"%>
	<div align="center">
		<h1><fmt:message key="rents.of" /><c:if test="${filtrado}">${usuarioLogado.cliente.nome}</c:if></h1>
		<h2>
			<a href="/<%=contextPath%>"><fmt:message key="main.link" /></a> &nbsp;&nbsp;&nbsp;
			<c:if test="${usuario.cliente != null}"><a href="/<%= contextPath%>/locacao/cadastro"><fmt:message key="rents.create" /></a></c:if>
		</h2>
	</div>

	<!-- <div align="center"><form id="filtro">
		<input type="text" class="filtro" id="id" onkeyup="filtro(0,'id')" placeholder="Pesquisar por id">
		<input type="text" class="filtro" id="cidade" onkeyup="filtro(1,'cidade')" placeholder="Pesquisar por cidade">
		<input type="text" class="filtro" id="estado"onkeyup="filtro(2, 'estado')" placeholder="Pesquisar por estado">
		<input type="text" class="filtro" id="pais"onkeyup="filtro(3, 'pais')" placeholder="Pesquisar por país">
		<input type="date" class="filtro" id="dataInicio" onchange="filtroData(4)" placeholder="Pesquisar por data">
		<input type="date" class="filtro" id="dataFim" onchange="filtroData(4)" placeholder="Pesquisar por data">
		<input type="number" class="filtro" id="duracao" onchange="filtroNumero(5, 'duracao')" placeholder="Pesquisar por duracao" min="1">
		<input type="number" class="filtro" id="valor" onchange="filtroNumero(6, 'valor')" placeholder="Pesquisar por valor" min="0.01" step="any" size="5">
		<c:if test="${!filtrado}">
		<label for="locadora"><fmt:message key="rental.title" /></label>
		<select class="filtroSelect" id="locadora" name="locadora" onchange="filtro(8, 'locadora')">
			<option value="" selected><fmt:message key="page.all" /></option>
			<c:forEach items="${listaLocadora}" var="locadora">
				<option value="${locadora.cnpj}">
					${locadora.nome}</option>
			</c:forEach>
		</select>
		</c:if>
		<input type="button" value="Pesquisar por data vigente" onclick="filtroVigente(4)">
		<input type="reset" value="Limpar Filtro" onclick="limpar()">
		</form></div> -->

	<div align="center">
		<table id="tabela">
			<caption><fmt:message key="rents.list" /></caption>
			<tr>	
				<th><fmt:message key="rent.ID" /></th>
				<th><fmt:message key="rent.rental.cnpj" /></th>
				<th><fmt:message key="rent.user.cpf" /></th>
				<th><fmt:message key="rent.date" /></th>
				<th><fmt:message key="rent.time" /></th>
				<th><fmt:message key="rent.active" /></th>
				<!-- <c:if test="${usuarioLogado.cliente != null}">
					<th><fmt:message key="actions.link"/></th>
				</c:if>	 -->
			</tr>
			<c:forEach var="locacao" items="${requestScope.listaLocacao}">
				<tr
					<c:if test="${locacao.ativo != 1}">class="cancelado"</c:if>>
						<td>${locacao.id}</td>
						<td>${locacao.locadoraCnpj}</td>
						<td>${locacao.clienteCpf}</td>
						<td>${locacao.dataLocacao}</td>
						<td>${locacao.horarioLocacao}</td>
						<c:if test="${locacao.ativo == 1}"><td><fmt:message key="rent.active"/></td></c:if><c:if test="${locacao.ativo != 1}"><td colspan="2"><fmt:message key="rents.finished"/></td></c:if>
						<!-- <c:if test="${locacao.ativo == 1}">
						<td><a class="acoes" href="/<%= contextPath%>/locacao/cancelar?id=${locacao.id}" onclick="return confirm('Tem certeza de que deseja cancelar esta locacao?');"><fmt:message key="rent.cancel"/></a></td>
						</c:if> -->
					</tr>
			</c:forEach>
		</table>
	</div>
</body>
</fmt:bundle>
</html>