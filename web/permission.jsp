<%@ page import="classes.DataBase"%>
<%@ page import="classes.Validate"%>

<%@ page import="java.sql.ResultSet" %>

<%

    /**
    * Input : from warden.html, student_id, date_out, time_out, date_in, time_in
    * perform : updates the info on table permission
    *           first checks if the record already exists, then it updates the previous record and returns __updated__,
     *           else in case of addition,inserts the record in table permission and returns __inserted__
    *            In case of any exception, returns __failed__
    *            Else returns the error message (like __Date in cannot be less than date out__ )
    * */

    //specifying output as json format
    response.setContentType("application/json");
    response.setHeader("Content-Disposition", "inline");

       //Insert(String student_id,String date_out,String time_out,String date_in,String time_in) {
       String execution="failed";
       String studentId=request.getParameter("student_id");
       String dateOut=request.getParameter("date_out");
       String timeOut=request.getParameter("time_out");
       String dateIn=request.getParameter("date_in");
       String timeIn=request.getParameter("time_in");


    Validate input=new Validate();
    //validating inputs
    if((studentId.length()>0 && studentId.intern() != " ")  && input.isDate(dateOut) && input.isDate(dateIn) && input.isTime(timeOut) && input.isTime(timeIn))
    {
        //if the date-in is not specified than enter's the loop or else the date in must be greater than equal to date out
        if(input.validateDates(dateOut,dateIn) || dateIn.intern()=="0000-00-00") {
            DataBase permission = new DataBase();
            if (permission.success.intern() == "success") {
                //check if the record already exists for today
                String selectStatement = "SELECT `serial` FROM `permission` WHERE `student_id` LIKE '"+studentId+"' AND `date_out`='"+dateOut+"';";
                ResultSet rows = permission.select(selectStatement);
                if (rows.next()) { //checks if the result is empty
                    //if entry on a date specified exists, then update the record
                    String updateStatement = "UPDATE `leave_book`.`permission` SET `time_out` = '" + timeOut + "', `date_in` = '" + dateIn + "', `time_in` = '" + timeIn + "' WHERE `permission`.`student_id` = '" + studentId + "' AND `permission`.`date_out` = '" + dateOut + "';";
                    permission.update(updateStatement);
                    execution = "updated";
                } else {
                    //if the record doesn't exists then insert into table permission
                    String insertStatement = "INSERT INTO `leave_book`.`permission` (`serial`, `student_id`, `date_out`, `time_out`, `date_in`, `time_in`) VALUES (NULL, '" + studentId + "', '" + dateOut + "', '" + timeOut + "', '" + dateIn + "', '" + timeIn + "');";
                    execution = permission.insert(insertStatement);
                }

                    //closing resultset used for select statement
                    if(rows!=null){
                        try{rows.close();}catch(Exception e){
                            //Ignore
                        }
                    }

            }
            permission.close();

        }
        else
            execution="Date in cannot be less than date out";
    }
    out.print("{success:'"+execution+"'}");

%>