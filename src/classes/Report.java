package classes;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Report {

    /**
     * function returns the LIST of students who came in late between the dates specified as input start date and end date,
     * including start and end date.
     * Returns name,email,batch etc along with permission details like date out and date in permitted to the students and also
     * the actual date and and time student got in.
     */
    public List<Student> getLateStudentsList(Date startDate, Date endDate) {
        List<Student> studentWithAllDetails = new LinkedList<>();

        //List<Gate> lateStudentsListAtGate=new LinkedList<>();
        DataBase details = new DataBase();
        if (details.success.intern() == "success") {
            //getting student ids and date_out/date_in timings of the late students
            String selectStatementForGate = "SELECT * FROM gate WHERE `late` LIKE 'yes' AND `date_in` >= '" + startDate + "' AND `date_in` <= '" + endDate + "';";
            ResultSet studentsList = details.select(selectStatementForGate);
            try {
                while (studentsList.next()) {

                    Student lateStudent = new Student();


                    //checking from table gate about late students
                    Gate lateStudentGateDetails = new Gate();
                    lateStudentGateDetails.setSigningDetailsAtGate(studentsList.getString("student_id"), studentsList.getDate("date_out"), studentsList.getTime("time_out"), studentsList.getDate("date_in"), studentsList.getTime("time_in"));
                    //lateStudentsListAtGate.add(lateStudent);

                    Permission lateStudentPermission = new Permission();
                    //getting permission details for the late students corresponding to the date they were late
                    String selectStatementForPermission = "SELECT * FROM permission WHERE `student_id`='" + studentsList.getString("student_id") + "' AND `date_out` >= '" + studentsList.getDate("date_out") + "' AND `date_in` >='" + studentsList.getDate("date_in") + "';";
                    ResultSet studentPermissionDetails = details.select(selectStatementForPermission); //will return only one or zero entry(one time permission)
                    if (studentPermissionDetails.next())
                        lateStudentPermission.setPermissionDetails(studentPermissionDetails.getDate("date_out"), studentPermissionDetails.getTime("time_out"), studentPermissionDetails.getDate("date_in"), studentPermissionDetails.getTime("time_in"));

                    //getting details of the students about their name, contact, batch
                    String selectStatementForDetails = "SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`hid`,`image`,`permission`,`status` FROM master WHERE `student_id`='" + studentsList.getString("student_id") + "';";
                    ResultSet studentDetails = details.select(selectStatementForDetails); //will return only one row because studentId is unique
                    if (studentDetails.next()) {
                        //set all the details belonging to a particular student id along with class Permission and Gate object
                        lateStudent.setStudentDetails(studentDetails.getString("student_id"), studentDetails.getString("name"), studentDetails.getInt("primary_contact"), studentDetails.getInt("secondary_contact"), studentDetails.getInt("batch"), studentDetails.getString("email"), studentDetails.getString("hostel"), studentDetails.getInt("room_number"), studentDetails.getString("hid"), studentDetails.getString("image"), studentDetails.getString("permission"), studentDetails.getInt("status"));

                        //adding Permission and Gate object to a student
                        lateStudent.setPermit(lateStudentPermission);
                        lateStudent.setGateTimings(lateStudentGateDetails);
                    }

                    //adding student object to Student List
                    studentWithAllDetails.add(lateStudent);
                }
            } catch (SQLException e) {

            }
            // mapping ids of late students to their details like name, room number


        }
        details.close();
        return studentWithAllDetails;
    }


    /**
     * This function is used to know about the details of all the students who went out and came in between the start date and end date
     * passed as function parameters.
     * Return list of student with actual time out and time in information
     **/
    public List<Student> gateRegisterEntries(Date startDate, Date endDate) {
        List<Student> studentListAtGate=new LinkedList<>();
        DataBase details=new DataBase();
        if(details.success.intern() == "success"){
            String selectStatementForGate="SELECT * FROM gate WHERE (`date_out` >= '"+startDate+"' AND `date_out` <= '"+endDate+"') OR (`date_in` >= '"+startDate+"' AND `date_in` <= '"+endDate+"');";
            ResultSet entriesAtGate=details.select(selectStatementForGate);

            try {
                while (entriesAtGate.next()) { //looping over each gate entry
                    Student student=new Student();

                    Gate studentGateDetails = new Gate();
                    studentGateDetails.setSigningDetailsAtGate(entriesAtGate.getString("student_id"), entriesAtGate.getDate("date_out"), entriesAtGate.getTime("time_out"), entriesAtGate.getDate("date_in"), entriesAtGate.getTime("time_in"));

                    //fetching details of the student like name and id from student database
                    String selectStatementForDetails = "SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`hid`,`image`,`permission`,`status` FROM master WHERE `student_id`='" + entriesAtGate.getString("student_id") + "';";
                    ResultSet studentDetails = details.select(selectStatementForDetails); //will return only one row because studentId is unique
                    if (studentDetails.next()) {
                        //set all the details belonging to a particular student id along with class Permission and Gate object
                        student.setStudentDetails(studentDetails.getString("student_id"), studentDetails.getString("name"), studentDetails.getInt("primary_contact"), studentDetails.getInt("secondary_contact"), studentDetails.getInt("batch"), studentDetails.getString("email"), studentDetails.getString("hostel"), studentDetails.getInt("room_number"), studentDetails.getString("hid"), studentDetails.getString("image"), studentDetails.getString("permission"), studentDetails.getInt("status"));

                        //adding Permission and Gate object to a student

                        student.setGateTimings(studentGateDetails);
                    }

                    studentListAtGate.add(student);
                }
            }catch(SQLException se){
                return studentListAtGate; //possibly null in case of SQlException
            }
        }
        details.close();


        return studentListAtGate;
    }
    /**
     * Returns the list of all the students present on campus for the current date and time
     * */
    public List<Student> getOnCampusStudents(){
        List<Student> onCampusStudents=new ArrayList<>();

        DataBase details=new DataBase();
        if(details.success.intern() == "success"){

            String selectStatement="SELECT `student_id`,`name`,`primary_contact`,`secondary_contact`,`batch`,`email`,`hostel`,`room_number`,`hid`,`image`,`permission`,`status` FROM master WHERE `status`=1;";
            ResultSet onCampusStudentDetails=details.select(selectStatement);

            try {
                if (onCampusStudentDetails.next()) {
                    Student student=new Student();
                    student.setStudentDetails(onCampusStudentDetails.getString("student_id"), onCampusStudentDetails.getString("name"), onCampusStudentDetails.getInt("primary_contact"), onCampusStudentDetails.getInt("secondary_contact"), onCampusStudentDetails.getInt("batch"), onCampusStudentDetails.getString("email"), onCampusStudentDetails.getString("hostel"), onCampusStudentDetails.getInt("room_number"), onCampusStudentDetails.getString("hid"), onCampusStudentDetails.getString("image"), onCampusStudentDetails.getString("permission"), onCampusStudentDetails.getInt("status"));

                   onCampusStudents.add(student);
                }
            }catch(SQLException se){
                return onCampusStudents;
            }
        }
        return onCampusStudents;
    }
}