<%@ page import="java.sql.*"%>
<%@ page import="java.sql.Connection"%>
<%@ page contentType="application/json;charset=UTF-8" language="java" %>
<%
    /*
    * Input : from warden.html, student_id, date_out, time_out, date_in, time_in
    * perform : updates the info on table permission
    *           first checks if the record already exists, if exists return __exists__, else in case of addition return __success__
    *           In case of any exception, return __failed__
    * */

    String success="failed";
    //getting from other page
    String studentId=request.getParameter("student_id");
    String dateOut=request.getParameter("date_out");
    String timeOut=request.getParameter("time_out");

    //only proceed if the id, dateout and timeout is recieved
    if((studentId!=null && studentId!="") && dateOut!=null && timeOut!=null)
    {

            String dateIn=(request.getParameter("date_in")==null)?"0000-00-00":request.getParameter("date_in");
            String timeIn=(request.getParameter("time_in")==null)?"0000-00-00":request.getParameter("time_in");

            //SQL specific variables

            Connection con=null;
            PreparedStatement st=null;
            ResultSet rs=null;
            try{
            Class.forName("com.mysql.jdbc.Driver");
            }catch(ClassNotFoundException e){
                success="failed";
            }
            try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp", "root", "");
            con.setAutoCommit(false); //to enable transactions

            //checking if the record already exist
            st=con.prepareStatement("SELECT id FROM employees WHERE age=12;");
            rs=st.executeQuery();
            success="exists";
            if(!(rs.getFetchSize()>0)) //if the row does not exists
            {
                //Inserting into tables
                st=con.prepareStatement("INSERT INTO employees VALUES(1,2,'Atif ','Aslam4');");
                st.executeUpdate();
                success="success";
            }
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