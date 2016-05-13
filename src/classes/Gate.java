package classes;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * This class represent the Gate object which is used by each student while coming in or going out of campus.
 */
public class Gate {
    private String studentId;
    private Date dateOut;
    private Time timeOut;
    private Date dateIn;
    private Time timeIn;


    //all in one setter function
    public void setSigningDetailsAtGate(String studentId,Date dateOut,Time timeOut,Date dateIn,Time timeIn){
        this.studentId=studentId;
        this.dateOut=dateOut;
        this.timeOut=timeOut;
        this.dateIn=dateIn;
        this.timeIn=timeIn;
    }

    //getter functions
    public String getStudentId(){
        return this.studentId;
    }
    public Date getDateOut(){
        return this.dateOut;
    }
    public Time getTimeOut(){
        return this.timeOut;
    }
    public Date getDateIn(){
        return this.dateIn;
    }
    public Time getTimeIn(){
        return this.timeIn;
    }

    public String logStudentExit(String studentId){
        String message="failed";

        Validator input=new Validator();
        //java.sql.date and time
        if (input.isStudentId(studentId)) {

            Converter dateAndTime=new Converter();

            //convert present date to sql date format
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilPresentDate=new java.util.Date();
            java.sql.Date sqlPresentDate=dateAndTime.toSqlDate(dateFormat.format(utilPresentDate));

            //convert present time to sql time
            SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
            java.util.Date utilPresentTime=new java.util.Date();
            java.sql.Time sqlPresentTime=dateAndTime.toSqlTime(timeFormat.format(utilPresentTime.getTime()));

            //student id, date_out and time_out prepared, now insert into database
            DataBase gate=new DataBase();
            if(gate.success.intern() == "success") { //connected to database
                //check if student has permission
                String selectPermissionStatement="SELECT * FROM `permission` WHERE `student_id`='"+studentId+"' ORDER BY `date_out` DESC LIMIT 5;";
                ResultSet studentPermissions=gate.select(selectPermissionStatement);

                //avoid exit multiple time with out entry
                String multipleExitSelectStatement="SELECT * FROM `gate` WHERE `student_id`='"+studentId+"' && `date_in`='0000-00-00' ORDER BY `date_out` DESC,`time_out` DESC LIMIT 1;";
                ResultSet multipleExitEntries=gate.select(multipleExitSelectStatement);

                boolean permissionFound=false;
                try {
                    //if permission exists and their are no multiple entries
                    if(studentPermissions != null && multipleExitEntries == null) {
                        while (studentPermissions.next()) {

                            //permission for date out should lie before or be same as today's date and date_in in permissions should be greater than equal to today's date
                            //comparison type (date & date) & (time)

                            //System.out.print("inside permisison");
                            if ((studentPermissions.getDate("date_out").compareTo(sqlPresentDate) <= 0 && studentPermissions.getDate("date_in").compareTo(sqlPresentDate) >= 0) && (studentPermissions.getTime("time_out").compareTo(sqlPresentTime) <= 0)) {
                                permissionFound = true;
                                String insertStatement = "INSERT INTO `gate` (`serial`, `student_id`, `date_out`, `time_out`, `date_in`, `time_in`,`late`) VALUES (NULL, '" + studentId + "', '" + sqlPresentDate + "', '" + sqlPresentTime + "', '0000-00-00', '00:00','0');";
                                message = gate.insert(insertStatement);


                                //update master to say the student is not on campus
                                String updateStatement = "UPDATE master SET status=0 WHERE student_id='"+studentId+"'";
                                gate.update(updateStatement);

                                break;
                            }
                        }

                        if (!permissionFound) //in case of permission not found
                            message = "no permission";
                    }else

                        message="no permission";

                        message="inserted";

                }catch(SQLException se){
                    message="failed";
                    return message;
                }

            }

            gate.close();
        }

        return message;
    }

    public String logStudentEntry(String studentId){
        String execution="failed";

        Validator input=new Validator();
        //java.sql.date and time
        if (input.isStudentId(studentId)) {

            Converter dateAndTime = new Converter();

            //convert present date to sql date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilPresentDate = new java.util.Date();
            java.sql.Date sqlPresentDate = dateAndTime.toSqlDate(dateFormat.format(utilPresentDate));

            //convert present time to sql time
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            java.util.Date utilPresentTime = new java.util.Date();
            java.sql.Time sqlPresentTime = dateAndTime.toSqlTime(timeFormat.format(utilPresentTime.getTime()));


            //student id, date_out and time_out prepared, now insert into database
            DataBase gate = new DataBase();
            if (gate.success.intern() == "success"){

                //check if the student had gone out, update date_in time_in fields
                //get last entry where a particular student went out
                String selectStatement="SELECT * FROM `gate` WHERE `student_id`='"+studentId+"' ORDER BY `date_out` DESC LIMIT 1;";
                ResultSet studentLog=gate.select(selectStatement);
                try {
                    if (studentLog.next()) {

                        //check if the student is late
                        String permissionSelectStatement="SELECT `date_in`,`time_in` FROM permission WHERE `student_id`='"+studentId+"' ORDER BY `date_out` DESC LIMIT 1;";
                        String late="no";
                        ResultSet studentPermissionDetail=gate.select(permissionSelectStatement);
                        if(studentPermissionDetail.next())
                        {
                            java.util.Date permissionDateIn=studentPermissionDetail.getDate("date_in");
                            java.util.Date permissionTimeIn=studentPermissionDetail.getTime("time_in");
                            if(!utilPresentDate.before(permissionDateIn) && !utilPresentTime.before(permissionTimeIn) )
                                late="yes";
                        }
                        //record found, update the date_in time_in  details (only update last entry)

                        String updateStatement="UPDATE `gate` SET `date_in` = '"+sqlPresentDate+"' , `time_in` = '"+sqlPresentTime+"' , `late` = '"+late+"' WHERE `student_id`='"+studentId+"'  ORDER BY `date_out` DESC,`time_out` DESC LIMIT 1;";
                        execution=gate.update(updateStatement);

                        //set status in master to present on campus
                        String updateMasterStatement = "UPDATE master SET status=1 WHERE student_id='"+studentId+"'";
                        gate.update(updateMasterStatement);
                    }
                    else
                        execution="Log does not exists";
                }catch(SQLException se){
                    execution="failed";
                }
            }
            gate.close();
        }
        return execution;
    }
}
