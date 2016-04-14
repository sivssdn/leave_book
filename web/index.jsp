
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.lang.*" %>

<%--
  Controller page
  All the pages are linked to this page and the request to each page is made by this page through get request.
  //dependencies- servelet.jar
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>

  <%
      String openPage=request.getParameter("pageNumber");

    if(openPage==null)
    {
      //to be converted into new page
      out.print("<a href='index.jsp?pageNumber=1'>Warden</a><br>");
      out.print("<a href='index.jsp?pageNumber=2'>Guard</a><br>");
      out.print("<a href='index.jsp?pageNumber=3'>Reports</a><br>");
    }
    else if(openPage.intern()=="1"){
      response.setStatus(response.SC_MOVED_TEMPORARILY);
      response.setHeader("Location","warden.html");
    }
    else if(openPage.intern()=="2"){
      response.setStatus(response.SC_MOVED_TEMPORARILY);
      response.setHeader("Location","guard.html");
    }
    else if(openPage.intern()=="3"){
      response.setStatus(response.SC_MOVED_TEMPORARILY);
      response.setHeader("Location","reports.html");
    }
  %>
  </body>
</html>
