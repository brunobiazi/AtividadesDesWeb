<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table>
	<caption>
		<c:choose>
			<c:when test="${cliente != null}">
				<fmt:message key="page.edit" />
                        </c:when>
			<c:otherwise>
				<fmt:message key="page.register" />
                        </c:otherwise>
		</c:choose>
	</caption>
	<c:if test="${cliente != null}">
		<input type="hidden" name="id" value="${cliente.id}" />
	</c:if>
	<tr>
		<td><label for="email"><fmt:message key="user.email" /></label></td>
		<td><input type="text" id="email" name="email" size="45"
			required value="${cliente.email}" /></td>
	</tr>
	<tr>
		<td><label for="senha"><fmt:message key="user.password" /></label></td>
		<td><input type="password" id="senha" name="senha" size="45" required
			value="${cliente.senha}" /></td>
	</tr>
	<tr>
		<td><label for="cpf"><fmt:message key="user.cpf" /></label></td>
		<td><input type="number" id="cpf" name="cpf" size="11" required
			min="0" value="${cliente.cpf}" /></td>
	</tr>
	<tr>
		<td><label for="nome"><fmt:message key="user.name" /></label></td>
		<td><input type="text" id="nome" name="nome" size="45"
			required value="${cliente.nome}" /></td>
	</tr>
	<tr>
		<td><label for="telefone"><fmt:message key="user.phone" /></label></td>
		<td><input type="number" id="telefone" name="telefone" size="12" required
			min="0" value="${cliente.telefone}" /></td>
	</tr>
	<tr>
		<td><label><fmt:message key="user.gender" /></label></td>
		<td><label for="masculino"><fmt:message key="gender.male" /></label>
		<input type="radio" id="homem" name="sexo"
            value="M" ${cliente.sexo=='M' ? 'checked' : '' }/>
        <label for="feminino"><fmt:message key="gender.female" /></label>
		<input type="radio" id="mulher" name="sexo"
            value="F" ${cliente.sexo=='F' ? 'checked' : '' }/>
        <label for="outro"><fmt:message key="gender.other" /></label>
		<input type="radio" id="outro" name="sexo"
            value="O"${cliente.sexo=='O' ? 'checked' : '' }/></td>
	</tr>
    <tr>
        <td><label for="nascimento"><fmt:message key="user.date" /></label></td>
        <td><input type="date" id="nascimento" name="nascimento" required
        value="${cliente.nascimento}"></td>
    </tr>
	<tr>
		<th><label for="admin"><fmt:message key="user.admin" /></label></th>
		<td><label for="nao"><fmt:message key="page.no" /></label>
		<input type="radio" id="nao" name="admin"
            value="0" ${cliente.admin==1 ? 'checked' : '' }/>
        <label for="sim"><fmt:message key="page.yes" /></label>
		<input type="radio" id="sim" name="admin"
            value="1" ${cliente.admin==0 ? 'checked' : '' }/></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit"  value="<fmt:message key='save.link'/>" id="submit"/></td>
	</tr>
</table>