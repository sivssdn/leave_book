package classes;

import java.sql.ResultSet;

/**
 * Input : from controller.jsp, student_id, date_out, time_out, date_in, time_in
 * perform : updates the info on table permission
 * first checks if the record already exists, then it updates the previous record and returns __updated__,
 * else in case of addition,inserts the record in table permission and returns __inserted__
 * In case of any exception, returns __failed__
 * Else returns the error message (like __Date in cannot be less than date out__ )
 */


public class Permission {
    private String dateOut;
    private String timeOut;
    private String dateIn;
    private String timeIn;
    public Permission( String dateOut, String timeOut, String dateIn, String timeIn){
        this.dateOut=dateOut;
        this.timeOut=timeOut;
        this.dateIn=dateIn;
        this.timeIn=timeIn;
    }
    public String givePermission(String studentId) {
        String execution = "failed";
        Validate input = new Validate();

        if (input.isStudentId(studentId) && input.isDate(dateOut) && input.isTime(timeOut) && input.isDate(dateIn) && input.isTime(timeIn)) {
            if (input.validateDates(dateOut, dateIn)) {      //   || dateIn.intern() == "0000-00-00"
                try {
                    DataBase permission = new DataBase();
                    if (permission.success.intern() == "success") {
                        //check if the record already exists for today
                        String selectStatement = "SELECT `serial` FROM `permission` WHERE `student_id` LIKE '" + studentId + "' AND `date_out`='" + dateOut + "';";
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
                        if (rows != null) {
                            try {
                                rows.close();
                            } catch (Exception e) {
                                //Ignore
                            }
                        }

                    }
                    permission.close();
                } catch (Exception e) {
                    execution = "failed";
                }
            } else
                execution = "Dates not valid";
        }
        return execution;
    }
}
