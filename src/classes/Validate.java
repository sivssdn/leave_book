package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */

public class Validate {
    /*
    public SimpleDateFormat isDate(String date) {
        SimpleDateFormat dateFormat=null;
        if(date.intern() == "")
            return dateFormat;
        try {
            dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.parse(date); //check if the input matches the formate specified
            return dateFormat;
        } catch (ParseException pe) {
            //date != format specified
            return dateFormat;
        }

    }
*/
    public boolean isDate(java.sql.Date dateInput) {
        if(dateInput == null)
            return false;

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormat.format(dateInput);
        //SimpleDateFormat dateFormat=null;

        try {
            //dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            //java.util.Date date1=dateFormat.parse(date);
            //java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
            dateFormat.parse(date); //check if the input matches the format specified
            return true;
        } catch (ParseException pe) {
            //date != format specified
            return false;
        }

    }
/*
    public SimpleDateFormat isTime(String time){
        SimpleDateFormat timeFormat=null;
        if(time.intern() == "")
            return timeFormat;
        try{
            timeFormat=new SimpleDateFormat("HH:mm");
            timeFormat.parse(time);
            return timeFormat;
        }catch(ParseException pe){
            //time != HH:mm format
            return timeFormat;
        }
    }
*/
    public boolean isTime(Time timeInput){
        if(timeInput == null)
            return false;
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
        String time=timeFormat.format(timeInput);

        try{
            //timeFormat=new SimpleDateFormat("HH:mm");
            timeFormat.parse(time);
            return true;
        }catch(ParseException pe){
            //time != HH:mm format
            return false;
        }
    }

    /*
    public boolean isTime(String time) {
        String pattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";  //24 hour time format
        if (time.matches(pattern))
            return true;
        return false;
    }
    */
    public boolean isStudentId(String id) {
        boolean isId = false;
        if (id != null && id.intern() != "") {
            DataBase checkId = new DataBase();
            if (checkId.success.intern() == "success") {
                String selectStatement = "SELECT `student_id` FROM master WHERE `student_id`='" + id + "';";
                ResultSet student = checkId.select(selectStatement);
                try {
                    if (student.next()) isId = true;
                } catch (SQLException e) {
                    isId = false;
                }
            }
            checkId.close();
        }
        return isId;
    }

    //use to check if dateout is less than datein and both dates are greater than or equal to today's date
    public boolean validateDates(java.sql.Date startDateInput, java.sql.Date endDateInput) {

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String startDate=dateFormat.format(startDateInput);
        String endDate=dateFormat.format(endDateInput);
        //getting present date
        //DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date presentDate=new java.util.Date();

        //getting present dat in string format, useful for making only date comparison as date.equals also matches time-----
        String presentDateString=dateFormat.format(presentDate);

        //SimpleDateFormat formatStartDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat formatEndDate=new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date date1 = dateFormat.parse(startDate);
            Date date2 = dateFormat.parse(endDate);
            // (first block) if the startDate and the end dates are more than or equal to today's date

            //if((presentDate.compareTo(date1)<=0 && presentDate.compareTo(date2)<=0) && date1.compareTo(date2)<=0)
            if((presentDate.before(date1) || presentDateString.equals(startDate)) && (presentDate.before(date2) || presentDateString.equals(endDate)) && (date1.before(date2) || date1.equals(date2)))
                return true;
            else
                return false;
        }catch(ParseException e){
                return false;
        }
        /*
        startDate = startDate.replaceAll("\\D+", ""); //coverts 2014-12-30 to 20141230
        int startDateValue = Integer.parseInt(startDate); //converting startDate to type int
        endDate = endDate.replaceAll("\\D+", "");
        int endDateValue = Integer.parseInt(endDate);
        if (startDateValue <= endDateValue)
            return true;
        return false;
        */
    }

}
