<%@ include file="/common/taglibs.jsp"%>
<%@ page import="javax.servlet.http.Cookie" %>
<% 
String cb = request.getParameter("callback")  ;
out.print("<script>" + cb +"({");
%>


	<c:if test="${pageContext.request.remoteUser != null}">
	'username': '${pageContext.request.remoteUser}'
	</c:if>
<%
out.print("})</script>");
%>
