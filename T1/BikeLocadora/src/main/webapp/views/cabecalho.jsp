<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="br.ufscar.dc.dsw.domain.Cliente"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
		String contextPath = request.getContextPath().replace("/", "");
%>
<div id="cabecalho">
<table>
    <tr>
        <td>
            <c:if test="${usuarioLogado != null}">
                <!-- <c:choose> 
            		<c:when test="${usuario.cliente.sexo == 'F'}"> 
                        
            			<c:out value="#{gender.female}"/>
            		</c:when>
                    <c:when test="${usuario.cliente.sexo == 'O'}">
            			<c:out value="#{gender.other}"/>
                    </c:when>
            		<c:otherwise> 
            			<c:out value="#{gender.male}"/>
            		</c:otherwise>
				</c:choose> -->
                <c:if test="${usuarioLogado.cliente != null}"><fmt:message key='page.welcome'/>${usuarioLogado.cliente.nome}</c:if><c:if test="${usuarioLogado.locadora != null}"><fmt:message key='page.welcome'/>${usuarioLogado.locadora.nome}</c:if>!
            </c:if>
            <a href="/<%= contextPath%>/locadora/lista"><fmt:message key="rentalscompany.list" /></a>
            <c:if test="${usuarioLogado.cliente != null}">
                <c:if test="${usuarioLogado.cliente.admin == 1}">
                    <a href="/<%= contextPath%>/cliente/lista"><fmt:message key="users.list" /></a>
                </c:if>
            </c:if>
            <c:if test="${usuarioLogado.locadora != null}">
                <a href="/<%= contextPath%>/locacao/listaPorLocadora"><fmt:message key="rents.entity" /></a>
            </c:if>
            <c:if test="${usuarioLogado.cliente != null}">
                <a href="/<%= contextPath%>/locacao/listaPorCliente"><fmt:message key="users.rents" /></a>
            </c:if>
            <c:if test="${usuarioLogado == null}">
                <a href="/<%= contextPath%>/views/login.jsp"><fmt:message key="login.link" /></a>
            </c:if>
            <c:if test="${usuarioLogado != null}">
                <a href="/<%= contextPath%>/auth/logout"><fmt:message key="exit.link" /></a>
            </c:if>
        </td>
    </tr>
</table>
</div>  

