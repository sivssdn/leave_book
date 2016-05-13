package classes;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * This class contains functions to convert List<Student> to json and excel. Also contains function to create Array
 * */
public class Converter {
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
     * Conerts list of students to excel format and returns the workbook object
     * */
    public XSSFWorkbook getExcelWorkbook(List<Student> listOfStudents){
        XSSFWorkbook studentWorkbook=new XSSFWorkbook();
        XSSFSheet sheet=studentWorkbook.createSheet("report");

        //setting column names in excel
        Row row=sheet.createRow(0);
        Cell cell=row.createCell(0);
        cell.setCellValue("Student Id");
        cell=row.createCell(1);
        cell.setCellValue("Name");
        cell=row.createCell(2);
        cell.setCellValue("Primary contact");
        cell=row.createCell(3);
        cell.setCellValue("Secondary contact");
        cell=row.createCell(4);
        cell.setCellValue("Batch");
        cell=row.createCell(5);
        cell.setCellValue("Email");
        cell=row.createCell(6);
        cell.setCellValue("Hostel");
        cell=row.createCell(7);
        cell.setCellValue("Room number");
        cell=row.createCell(8);
        cell.setCellValue("Permission");
        cell=row.createCell(9);
        cell.setCellValue("Status");


        Student firstStudent=listOfStudents.get(0);
        if(firstStudent.permit != null)
        {
            cell=row.createCell(10);
            cell.setCellValue("Date out");
            cell=row.createCell(11);
            cell.setCellValue("Time out");
            cell=row.createCell(12);
            cell.setCellValue("Date in ");
            cell=row.createCell(13);
            cell.setCellValue("Time in");
        }
        if(firstStudent.gateTimings != null && firstStudent.permit == null)
        {
            cell=row.createCell(10);
            cell.setCellValue("Actual Date out");
            cell=row.createCell(11);
            cell.setCellValue("Actual Time out");
            cell=row.createCell(12);
            cell.setCellValue("Actual Date in ");
            cell=row.createCell(13);
            cell.setCellValue("Actual Time in");

        }
        else if(firstStudent.gateTimings != null && firstStudent.permit == null)
        {
            cell=row.createCell(14);
            cell.setCellValue("Actual Date out");
            cell=row.createCell(15);
            cell.setCellValue("Actual Time out");
            cell=row.createCell(16);
            cell.setCellValue("Actual Date in ");
            cell=row.createCell(17);
            cell.setCellValue("Actual Time in");

        }

//        int rowCount=1;
        for(int i=0;i<listOfStudents.size();i++)
        {

            Student singleStudent=listOfStudents.get(i);
            row=sheet.createRow(i+1);
            cell=row.createCell(0);
            cell.setCellValue(singleStudent.getStudentId());
            cell=row.createCell(1);
            cell.setCellValue(singleStudent.getName());
            cell=row.createCell(2);
            cell.setCellValue(singleStudent.getPrimaryContact());
            cell=row.createCell(3);
            cell.setCellValue(singleStudent.getSecondaryContact());
            cell=row.createCell(4);
            cell.setCellValue(singleStudent.getBatch());
            cell=row.createCell(5);
            cell.setCellValue(singleStudent.getEmail());
            cell=row.createCell(6);
            cell.setCellValue(singleStudent.getHostel());
            cell=row.createCell(7);
            cell.setCellValue(singleStudent.getRoomNumber());
            cell=row.createCell(8);
            cell.setCellValue(singleStudent.getPermission());
            cell=row.createCell(9);
            cell.setCellValue(singleStudent.getStatus());
            //rowCount++;



            if(singleStudent.permit != null){

                cell=row.createCell(10);
                cell.setCellValue(singleStudent.permit.getDateOut().toString());
                cell=row.createCell(11);
                cell.setCellValue(singleStudent.permit.getTimeOut().toString());
                cell=row.createCell(12);
                cell.setCellValue(singleStudent.permit.getDateIn().toString());
                cell=row.createCell(13);
                cell.setCellValue(singleStudent.permit.getTimeIn().toString());
            }
            if(singleStudent.gateTimings != null && singleStudent.permit ==null){

                cell=row.createCell(10);
                cell.setCellValue(singleStudent.gateTimings.getDateOut().toString());
                cell=row.createCell(11);
                cell.setCellValue(singleStudent.gateTimings.getTimeOut().toString());
                cell=row.createCell(12);
                cell.setCellValue(singleStudent.gateTimings.getDateIn().toString());
                cell=row.createCell(13);
                cell.setCellValue(singleStudent.gateTimings.getTimeIn().toString());
            }
            else if(singleStudent.gateTimings != null && singleStudent.permit !=null){
                cell=row.createCell(14);
                cell.setCellValue(singleStudent.gateTimings.getDateOut().toString());
                cell=row.createCell(15);
                cell.setCellValue(singleStudent.gateTimings.getTimeOut().toString());
                cell=row.createCell(16);
                cell.setCellValue(singleStudent.gateTimings.getDateIn().toString());
                cell=row.createCell(17);
                cell.setCellValue(singleStudent.gateTimings.getTimeIn().toString());

            }


        }
        return studentWorkbook;
    }
/*
    /**
     * Converts Resultset rows to Student object except Permission and Gate object
     *
    public List<Student> getStudentsFromResultsetbbbb(ResultSet rows){
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

*/
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
