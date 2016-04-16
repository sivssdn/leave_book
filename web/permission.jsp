<%@ page import="java.sql.*"%>
<%@ page import="java.sql.Connection"%><%@ page import="classes.DataBase"%>
<%@ page contentType="application/json;charset=UTF-8" language="java" %>
<%@ page import="classes.DataBase" %>
<%
    /*
    * Input : from warden.html, student_id, date_out, time_out, date_in, time_in
    * perform : updates the info on table permission
    *           first checks if the record already exists, if exists return __exists__, else in case of addition return __success__
    *           In case of any exception, return __failed__
    * */

       //Insert(String student_id,String date_out,String time_out,String date_in,String time_in) {
       String studentId=request.getParameter("student_id");
       String dateOut=request.getParameter("date_out");
       String timeOut=request.getParameter("time_out");
       String dateIn=request.getParameter("date_in");
       String timeIn=request.getParameter("time_in");


        /*
        String studentId="1";
        String dateOut="1";
        String dateIn="1";
        String timeOut="1";
        String timeIn="1";
        */
       DataBase db=new DataBase(); //returns String "success" on successful execition
       if(db.success.intern()=="success")
       {
               //String success=db.insert("INSERT INTO employees VALUES(11,11,'first','and_last');");
               ResultSet result=db.select("SELECT * FROM employees;");
               if(result!=null){
                   while(result.next()){
                    out.print(result.getInt("id"));
                    out.print(result.getInt("age"));
                    out.print(result.getString("first"));
                    out.print(result.getString("last")+"<br>");
                   }
               }
               else
               out.print("null from resultset");

               //necessary for closing db connection
               String success=db.close();
       }
    //out.print("{success:"+success+",studentId:"+studentId+"}");
%>