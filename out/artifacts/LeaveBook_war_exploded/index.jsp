
<%--
  Controller page
  All the pages are linked to this page and the request to each page is made by this page through get request.
  //dependencies- servelet.jar
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Leave book</title>
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width" name="viewport">

    <link href="material/css/base.min.css" rel="stylesheet">
    <link href="material/css/project.min.css" rel="stylesheet">

    <style>
      .header{
        box-shadow: 0 1px 10px rgba(0,0,0,0.5);
      }
      html,body{
        height:100%;
      }
      .first{
        display:inline-block;
        float:left;
        width:30%;
        height:35%;
        margin-left:2%;
        margin-top:10%;
      }
      .card-side-img{
        height:100%;
      }
      .card-heading{
        font-size:2vw;
        margin-left:40%;
        margin-top:20%;
      }
      .head{
        font-size:1.4vw;
        color:#ffffff;
        padding-left:2vw;
      }
      a{
        color:#000000;
      }
    </style>
  </head>
  <body>

  <header class="header header-waterfall header-red "><br>
    <span class="head">ASHOKA UNIVERSITY </span>
  </header>
  <br>

  <br>
  <br>
  <%
    //when page is open for the first time
    String openPage=request.getParameter("pageNumber");
    if(openPage==null) {

  %>

  <a href='index.jsp?pageNumber=1'>
  <div class="card first waves-attach waves-effect">
    <aside class="card-side pull-left card-side-img">
      <img src="material/images/samples/portrait.jpg">
    </aside>

    <div class="card-main ">
      <div class="card-inner ">
        <p class="card-heading"><span style="margin-left:2%;"> Warden </span></p>
      </div>
    </div>
  </div>
  </a>

  <a href='index.jsp?pageNumber=2'>
  <div class="card first waves-attach waves-effect">
    <aside class="card-side pull-left card-side-img">
      <img src="material/images/samples/portrait.jpg">
    </aside>

    <div class="card-main ">
      <div class="card-inner ">

        <p class="card-heading"><span style="margin-left:2%;"> Gate </span></p>

      </div>
    </div>
  </div>
  </a>


  <a href='index.jsp?pageNumber=3'>
  <div class="card first waves-attach waves-effect">
    <aside class="card-side pull-left card-side-img">
      <img src="material/images/samples/portrait.jpg">
    </aside>

    <div class="card-main ">
      <div class="card-inner ">
        <p class="card-heading"><span style="margin-left:2%;"> Report </span></p>
      </div>
    </div>
  </div>
  </a>

    <%
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



  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
  <script src="material/js/base.min.js"></script>
  <script src="material/js/project.min.js"></script>

</html>
