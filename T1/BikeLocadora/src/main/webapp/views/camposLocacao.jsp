<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table>
	<caption>
		<c:choose>
			<c:when test="${locacao != null}">
				<fmt:message key="page.edit" />
                        </c:when>
			<c:otherwise>
				<fmt:message key="page.register" />
                        </c:otherwise>
		</c:choose>
	</caption>
	<c:if test="${locacao != null}">
		<input type="hidden" name="id" value="${locacao.id}" />
	</c:if>
	<input type="hidden" name="cpf" value="${usuario.cliente.cpf}" />
	<input type="hidden" name="ativo" value="1" />	
	<tr>
		<th><fmt:message key="rent.rent" /></th>
	</tr>
	<tr>
		<td><label for="locadora"><fmt:message key="rental.title" /></label></td>
		<td><select name="locadora">
			<c:forEach items="${locadoras}" var="locadora">
				<option value="${locadora.key}"
					${locacao.locadoraCnpj==locadora.value ? 'selected' : '' }>
					${locadora.value}</option>
			</c:forEach>
	</select></td>
	</tr>
	<tr>
		<td><label for="date"><fmt:message key="rent.date" /></label></td>
		<td><input type="date" id="date" name="date" required
			 value="${locacao.date}" /></td>
	</tr>
	<tr>
		<td><label for="time"><fmt:message key="rent.time" /></label></td>
		<td><input type="time" id="time" name="time" required  step="3600"
			value="${locacao.time}"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="<fmt:message key='save.link'/>" id="submit"/></td>
	</tr>
</table>