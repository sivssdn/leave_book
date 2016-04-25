<%@ page import="classes.Autocomplete" %>
<%@ page import="classes.Permission" %>
<%@ page import="classes.Student" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    /**
     * This page is responsible to direct the request to the classes responsible for their execution.
     * For example, grant permission request will be served using Permission class, entry exit will be loged in the same way.
     *
     * */
    response.setContentType("application/json");
    response.setHeader("Content-Disposition", "inline");


            /*
            Validate dateTime=new Validate();
            SimpleDateFormat dateout=dateTime.isDate(requestDateOut);
            SimpleDateFormat timeOut=dateTime.isTime(requestTimeOut);
            SimpleDateFormat dateIn=dateTime.isTime(requestDateIn);
            SimpleDateFormat timeIn=dateTime.isTime(requestTimeIn);
            */

    //to check which page made the request
    String autoComplete=request.getParameter("autocomplete");
    String permission = request.getParameter("permission");
    String gate = request.getParameter("gate");


    //request is made by permission page to grant permission correspomding to a student id
    if(autoComplete!=null){





        String searchField=request.getParameter("search_field");
        String searchType=request.getParameter("search_type");

        //pass the field name, id or hid to autocomplete.java
        Autocomplete studentDetails=new Autocomplete();
        List<Student> studentsList=studentDetails.getAllDetails(searchField,searchType);
        //got result in List<Student>, store it in JSONObject array

        JSONArray jsonStudentsList=new JSONArray();

        for(int i=0; i<studentsList.size(); i++)
        {

            Student singleStudent=studentsList.get(i);
            JSONObject jsonStudent=new JSONObject();
            jsonStudent.put("id",i);
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

            jsonStudentsList.put(jsonStudent);
        }

        //print the result
        out.print(jsonStudentsList);
    }
    else if (permission != null) {

        String studentId = request.getParameter("student_id");
        String requestDateOut = request.getParameter("date_out");
        String requestTimeOut = request.getParameter("time_out");
        String requestDateIn = request.getParameter("date_in");
        String requestTimeIn = request.getParameter("time_in");

        String execution = "failed";
        Permission student = new Permission( requestDateOut, requestTimeOut, requestDateIn, requestTimeIn);
        execution = student.givePermission(studentId);

        out.print("{'execution':'" + execution + "'}");
        //will call Class permission and pass the parameters
    } else if (gate != null) {

        //will call class Gate
    }


%>
