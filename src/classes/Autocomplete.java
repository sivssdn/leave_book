package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Autocomplete {

    public List<Student> getAllDetails(String searchField, String searchType) {

        List<Student> students = new LinkedList<>();


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
                //connected to db


                //applying select statement
                rows = check.select(selectStatement);
                //preparing JSON output
                try {

                    while (rows.next()) { //while records exists returned from select query
                        Student studentFound=new Student();
                        //studentFound.setStudentDetails(rows.getString("student_id"),rows.getString("name"),rows.getInt("primary_contact"),rows.getInt("secondary_contact"),rows.getInt("batch"),rows.getString("email"),rows.getString("hostel"),rows.getInt("room_number"),rows.getString("hid"),rows.getString("image"),rows.getString("permission"),rows.getInt("status"));
                        //String studentId,String name,long primaryContact,long secondaryContact,int batch,String email,String hostel,int roomNumber,String hid,String image,String permission,int status
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