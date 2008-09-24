<%@ page import="org.example.www.librarian.LibrarianProxy" %>
<%--
  Created by IntelliJ IDEA.
  User: �����
  Date: 28.12.2007
  Time: 10:54:14
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="lp" scope="session" class="org.example.www.librarian.LibrarianProxy" />

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //можно посылать с параметром endpoint
    if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
    lp.setEndpoint(request.getParameter("endpoint"));
%>
<html>
  <head><title>Абонемент</title></head>
  <body>
  <form action="abonement.jsp" method="get">
      <table cellpadding="0" cellspacing="0" border="0">
          <tr>
              <td>ISBN:</td>
              <td><input type="text" name="id" /></td></tr>
          <tr><td colspan="2" align="center"><input type="submit" value="Запросить"/></td></tr>
      </table>
  </form>

  <br/><br/><br/>
  <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr><td>ISBN: </td><td>Ответ</td></tr>
      <tr>
          <td><%=request.getParameter("id")%></td>
          <td><%=lp.getBookByName(request.getParameter("id"))%></td>
      </tr>
  </table>
  </body>
</html>