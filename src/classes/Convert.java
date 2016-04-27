package classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains functions to convert List<Student> to json and excel. Also contains function to create Array
 * */
public class Convert {
    //convert to excel or to excel format
    /**
     * Input : List<Student> and binds it to JSON Object Array
     * Output : JSONObject Array
     * */
    public JSONArray getJsonArray(List<Student> listOfStudents){
        JSONArray jsonStudentsList=new JSONArray();

        for(int i=0; i<listOfStudents.size(); i++)
        {

            Student singleStudent=listOfStudents.get(i);
            JSONObject jsonStudent=new JSONObject();
            jsonStudent.put("id",singleStudent.getStudentId());
            jsonStudent.put("name",singleStudent.getName());
            jsonStudent.put("primary",singleStudent.getPrimaryContact());
            jsonStudent.put("secondary",singleStudent.getSecondaryContact());
            jsonStudent.put("batch",singleStudent.getBatch());
            jsonStudent.put("email",singleStudent.getEmail());
            jsonStudent.put("hostel",singleStudent.getHostel());
            jsonStudent.put("room",singleStudent.getRoomNumber());
            jsonStudent.put("image",singleStudent.getImage());
            jsonStudent.put("permission",singleStudent.getPermission());
            jsonStudent.put("status",singleStudent.getStatus());

            if(singleStudent.permit!=null)
            {
                jsonStudent.put("date_out",singleStudent.permit.getDateOut());
                jsonStudent.put("time_out",singleStudent.permit.getTimeOut());
                jsonStudent.put("date_in",singleStudent.permit.getDateIn());
                jsonStudent.put("time_in",singleStudent.permit.getTimeIn());
            }
            if(singleStudent.gateTimings!=null)
            {
                jsonStudent.put("actual_date_out",singleStudent.gateTimings.getDateOut());
                jsonStudent.put("actual_time_out",singleStudent.gateTimings.getTimeOut());
                jsonStudent.put("actual_date_in",singleStudent.gateTimings.getDateIn());
                jsonStudent.put("actual_time_in",singleStudent.gateTimings.getTimeIn());
            }
            jsonStudentsList.put(jsonStudent);
        }

        return jsonStudentsList;

    }


    /**
     * Converts Resultset rows to Student object except Permission and Gate object
     * */
    public List<Student> getStudentsFromResultset(ResultSet rows){
        List<Student> studentList=new LinkedList<>();

        try {

            while (rows.next()) { //while records exists returned from select query
                Student studentFound=new Student();
                //studentFound.setStudentDetails(rows.getString("student_id"),rows.getString("name"),rows.getInt("primary_contact"),rows.getInt("secondary_contact"),rows.getInt("batch"),rows.getString("email"),rows.getString("hostel"),rows.getInt("room_number"),rows.getString("hid"),rows.getString("image"),rows.getString("permission"),rows.getInt("status"));
                //String studentId,String name,long primaryContact,long secondaryContact,int batch,String email,String hostel,int roomNumber,String hid,String image,String permission,int status
                studentFound.setStudentDetails(rows.getString("student_id"),rows.getString("name"),rows.getInt("primary_contact"),rows.getInt("secondary_contact"),rows.getInt("batch"),rows.getString("email"),rows.getString("hostel"),rows.getInt("room_number"),rows.getString("hid"),rows.getString("image"),rows.getString("permission"),rows.getInt("status"));
                studentList.add(studentFound);

            }

        } catch (SQLException e) {
            //possibly null
            return studentList;
        }
        return studentList;
    }


    //convert String to Java.sql.Date
    public java.sql.Date toSqlDate(String date){
        java.sql.Date sqlDate=null;
        java.util.Date utilDate;
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            utilDate = dateFormat.parse(date);
        }catch(ParseException pe){
            return sqlDate; //null
        }
        sqlDate=new java.sql.Date(utilDate.getTime());
        return sqlDate; //returns Date in sql format
    }

    //converts String to sql time
    public java.sql.Time toSqlTime(String time){
        java.sql.Time sqlTime=null;
        java.util.Date utilTime;
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
        try{
           utilTime=timeFormat.parse(time);
        }catch(ParseException pe){
            return sqlTime; //null
        }
        sqlTime=new java.sql.Time(utilTime.getTime());
        return sqlTime;
    }

}
