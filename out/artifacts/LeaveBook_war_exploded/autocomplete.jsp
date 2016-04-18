<%@ page import="classes.DataBase" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 17-04-2016
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%  //input parameters type(name id), name
    /**
     * This page is used for suggesting name or student id.
     * Input : String with the type(name or id), searches for the string in database and returns strings
     * similar to the name or the id along with
     * primary contact, secondary contact, batch, email, hostel, room number, image, permission,status
     *
     * */
    response.setContentType("application/json");
    response.setHeader("Content-Disposition", "inline");
    String type=request.getParameter("type"); //name or id


    if(type!=null && type.intern()!="")
    {
        String selectStatement=null; //always false, to make sure no rows are returned in case both studentId and
        String name=request.getParameter("name");
        String studentId=request.getParameter("studentId");
        int flag=0;
        //preparing statement
        if(name !=null && name.length()>0 && name.intern()!="") //if search by name is performed
        {
            selectStatement="SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`image`,`permission`,`status` FROM master WHERE `name` LIKE '%"+name+"%';";
            flag=1;
        }
        else if(studentId !=null && studentId.length()>0 && studentId.intern() != "") //if search by student id is performed
        {
            selectStatement = "SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`image`,`permission`,`status` FROM master WHERE `student_id` LIKE '%" + studentId + "%';";
            flag=1;
        }
        if(flag==1) {

                DataBase check = new DataBase();
                ResultSet rows = null;
                if (check.success.intern() == "success") {
                    //connected to db


                    //applying select statement
                    rows = check.select(selectStatement);

                    //preparing JSON output
                    out.print("[");
                    while (rows.next()) { //while records exists returned from select query
                        out.print("\n{");
                        out.print("\"id\":\"" + rows.getString("student_id") + "\",");
                        out.print("\"name\":\"" + rows.getString("name") + "\",");
                        out.print("\"primary\":\"" + rows.getString("primary_contact") + "\",");
                        out.print("\"secondary\":\"" + rows.getString("secondary_contact") + "\",");
                        out.print("\"batch\":\"" + rows.getString("batch") + "\",");
                        out.print("\"email\":\"" + rows.getString("email") + "\",");
                        out.print("\"hostel\":\"" + rows.getString("hostel") + "\",");
                        out.print("\"room\":\"" + rows.getString("room_number") + "\",");
                        out.print("\"image\":\"" + rows.getString("image") + "\",");
                        out.print("\"permission\":\"" + rows.getString("permission") + "\",");
                        out.print("\"status\":\" " + rows.getString("status") + "\" ");
                        out.print("},");
                    }
                    out.print("{}]");

                }
                if (rows != null) {
                    try {
                        rows.close();
                    } catch (Exception e) {//Ignore, we can't close it manually  :-O
                    }
                }
                check.close();
        }
    }

%>
