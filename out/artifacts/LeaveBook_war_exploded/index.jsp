
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
      out.print("<a href='index.jsp?pageNumber=2'>Gate</a><br>");
      out.print("<a href='index.jsp?pageNumber=3'>Reports</a><br>");
    }
    else if(openPage.intern()=="1"){
      response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
      //redirects to page which allows permission to 'email permission' students
      response.setHeader("Location","permission.html");
    }
    else if(openPage.intern()=="2"){
      response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
      response.setHeader("Location","gate.html");
    }
    else if(openPage.intern()=="3"){
      response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
      response.setHeader("Location","reports.html");
    }



/*
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=filename.xls");

    MakeExcel make=new MakeExcel();
    XSSFWorkbook resultWorkbook=make.write();
    resultWorkbook.write(response.getOutputStream());
    //resultWorkbook.close();

    out.print("<a href='trial.xlsx'>file</a>");
*/
  %>
  </body>
</html>
