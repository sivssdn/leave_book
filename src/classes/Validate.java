package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 */
public class Validate {
    public boolean isDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFormat.parse(date); //check if the input matches the formate specified
        } catch (ParseException pe) {
            //date != format specified
            return false;
        }
        return true;
    }

    public boolean isTime(String time) {
        String pattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";  //24 hour time format
        if (time.matches(pattern))
            return true;
        return false;
    }

    public boolean isStudentId(String id) {
        boolean isId = false;
        if (id != null && id.intern() != " ") {
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

    public boolean validateDates(String startDate, String endDate) {
        startDate = startDate.replaceAll("\\D+", ""); //coverts 2014-12-30 to 20141230
        int startDateValue = Integer.parseInt(startDate); //converting startDate to type int
        endDate = endDate.replaceAll("\\D+", "");
        int endDateValue = Integer.parseInt(endDate);
        if (startDateValue <= endDateValue)
            return true;
        return false;
    }

}
