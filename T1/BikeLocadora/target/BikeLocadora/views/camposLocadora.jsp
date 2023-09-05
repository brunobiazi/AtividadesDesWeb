<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table>
	<caption>
		<c:choose>
			<c:when test="${locadora != null}">
				<fmt:message key="page.edit" />
                        </c:when>
			<c:otherwise>
				<fmt:message key="page.register" />
                        </c:otherwise>
		</c:choose>
	</caption>
	<c:if test="${locadora != null}">
		<input type="hidden" name="id" value="${locadora.id}" />
	</c:if>
	<tr>
		<td><label for="email"><fmt:message key="rental.email" /></label></td>
		<td><input type="text" id="email" name="email" size="45"
			required value="${locadora.email}" /></td>
	</tr>
	<tr>
		<td><label for="senha"><fmt:message key="rental.password" /></label></td>
		<td><input type="password" id="senha" name="senha" size="45" required
			value="${locadora.senha}" /></td>
	</tr>
	<tr>
		<td><label for="cnpj"><fmt:message key="rental.cnpj" /></label></td>
		<td><input type="number" id="cnpj" name="cnpj" size="20" required
			min="0" value="${locadora.cnpj}" /></td>
	</tr>
	<tr>
		<td><label for="nome"><fmt:message key="rental.name" /></label></td>
		<td><input type="text" id="nome" name="nome" size="45" required
			value="${locadora.nome}" /></td>
	</tr>
	<tr>
		<td><label for="cidade"><fmt:message key="rental.city" /></label></td>
		<td><input type="text" id="cidade" name="cidade" size="45" required
			value="${locadora.cidade}" /></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="<fmt:message key='save.link'/>" id="submit"/></td>
	</tr>
</table>