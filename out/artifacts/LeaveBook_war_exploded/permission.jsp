<%@ page import="java.sql.*"%>
<%@ page import="java.sql.Connection"%>
<%@ page contentType="application/json;charset=UTF-8" language="java" %>
<%
    /*
    * Input : from warden.html, student_id, date_out, time_out, date_in, time_in
    * perform : updates the info on table permission
    *
    * */

    String success="failed";
    //getting from other page
    String studentId=request.getParameter("student_id");
    String dateOut=request.getParameter("date_out");
    String timeOut=request.getParameter("time_out");

    //only proceed if the id, dateout and timeout is recieved
    if((studentId!=null && studentId!="") && dateOut!=null && timeOut!=null)
    {
            success="success";
            String dateIn=(request.getParameter("date_in")==null)?"0000-00-00":request.getParameter("date_in");
            String timeIn=(request.getParameter("time_in")==null)?"0000-00-00":request.getParameter("time_in");

            //SQL specific variables

            Connection con=null;
            PreparedStatement st=null;

            try{
            Class.forName("com.mysql.jdbc.Driver");
            }catch(ClassNotFoundException e){
                success="failed";
            }
            try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp", "root", "");
            con.setAutoCommit(false); //to enable transactions
            st=con.prepareStatement("INSERT INTO employees VALUES(1,2,'Atif ','Aslam3');");
            st.executeUpdate();
            con.commit(); //commiting to the db
            }catch(SQLException e){
            if(con!=null)
            con.rollback();
            e.printStackTrace(); //TO BE REMOVED
            success="failed";
            }finally{
                //closing all the connections
                if(con!=null)
                con.close();

                if(st!=null)
                st.close();
            }
    }


    out.print("{success:"+success+",studentId:"+studentId+"}");
%>