package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Autocomplete {

    /**
     * Input: searchField - like name or the id that is required to searched
     *        searchType - whether the searchFields is name or Id
     * Output: Class Student object with parameters name, primary and secondary contact, batch, email, hostel, room number, image, permission
     * and status fields belonging to the searchField
     * output does not contain student's permission details and signing at gate details
     *
     * */

    public List<Student> getAllDetails(String searchField, String searchType) {

        List<Student> students = new ArrayList<>();


        int flag = 0;
        String selectStatement = null;
        if (searchType.intern() == "hid") {
            selectStatement = "SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`hid`,`image`,`permission`,`status` FROM master WHERE `hid` LIKE '" + searchField + "';";
            flag = 1;
        } else if (searchType.intern() == "name") {
            selectStatement = "SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`hid`,`image`,`permission`,`status` FROM master WHERE `name` LIKE '%" + searchField + "%';";
            flag = 1;
        } else if (searchType.intern() == "id") {
            selectStatement = "SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`hid`,`image`,`permission`,`status` FROM master WHERE `student_id` LIKE '" + searchField + "';";
            flag = 1;
        }

        if (flag == 1) {
            //flag to ensure that if function is called without any parameter than function terminates without connecting to database
            DataBase check = new DataBase();
            ResultSet rows = null;
            if (check.success.intern() == "success") {

                //applying select statement
                rows = check.select(selectStatement);
                //binding student object fetched from database


                try {

                    while (rows.next()) { //while records exists returned from select query
                        Student studentFound=new Student();
                        studentFound.setStudentDetails(rows.getString("student_id"),rows.getString("name"),rows.getInt("primary_contact"),rows.getInt("secondary_contact"),rows.getInt("batch"),rows.getString("email"),rows.getString("hostel"),rows.getInt("room_number"),rows.getString("hid"),rows.getString("image"),rows.getString("permission"),rows.getInt("status"));
                        students.add(studentFound);

                    }

                } catch (SQLException e) {
                    //possibly null
                    return students;
                }

            }
        }

        return students;
    }
}