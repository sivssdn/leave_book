<%@ page import="classes.*" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFWorkbook" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
    String requestAutoComplete=request.getParameter("autocomplete");
    String requestPermission = request.getParameter("permission");
    String requestGate = request.getParameter("gate");
    String requestReport=request.getParameter("report");

    //request is made by permission page to grant permission correspomding to a student id
    if(requestAutoComplete!=null){


        String searchField=request.getParameter("search_field");
        String searchType=request.getParameter("search_type");

        //pass the field name, id or hid to autocomplete.java
        Autocomplete studentDetails=new Autocomplete();
        List<Student> studentsList=studentDetails.getAllDetails(searchField,searchType);
        //got result in List<Student>, store it in JSONObject array

        //converting to list of students to json format
        Converter studentListToJson=new Converter();
        JSONArray jsonStudentsList=studentListToJson.getJsonArray(studentsList);

        //print the result (json)
        out.print(jsonStudentsList);
    }
    else if (requestPermission!= null) {



        //reading request from browser
        String studentId = request.getParameter("student_id");
        String requestDateOut = request.getParameter("date_out");
        String requestTimeOut = request.getParameter("time_out");
        String requestDateIn = request.getParameter("date_in");
        String requestTimeIn = request.getParameter("time_in");
        String execution = "failed";
            if(requestDateOut.intern() == "" || requestDateIn == "" || requestDateOut == null)
                out.print("[{\"execution\":\"" + execution + "\"}]");
            else {
                //converting the request to Date and time format
                Converter dateTime = new Converter();
                java.sql.Date dateOut = dateTime.toSqlDate(requestDateOut);
                java.sql.Time timeOut = dateTime.toSqlTime(requestTimeOut);
                java.sql.Date dateIn = dateTime.toSqlDate(requestDateIn);
                java.sql.Time timeIn = dateTime.toSqlTime(requestTimeIn);


                Permission student = new Permission();
                student.setPermissionDetails(dateOut, timeOut, dateIn, timeIn);
                execution = student.givePermission(studentId);

                out.print("[{\"execution\":\"" + execution + "\"}]");
            }
        //will call Class permission and pass the parameters
    } else if (requestGate != null) {
        //----------------------GATE PART   -----------------------
        String hid=request.getParameter("hid");

        Autocomplete student=new Autocomplete();
        List<Student> studentDetails=student.getAllDetails(hid,"hid");
        Student studentWithDetails=new Student();

        if(studentDetails.isEmpty())  //hid is not found in the table
            out.print("HID not valid");
        else {
            studentWithDetails=studentDetails.get(0);
            //out.print(studentWithDetails.getName());

            Gate studentAtGate = new Gate();

            //instance of gate created, now check of CHECK OUT request was made or CHECK IN
            String check=request.getParameter("check");
            if(check.intern() == "out") //check out request was made :: student wants to exit from gate
            {
                String message = studentAtGate.logStudentExit(studentWithDetails.getStudentId());
                out.print("[{ \"execution\" : \" " + message+" \" }]");
            }
            else if(check.intern() == "in"){
                String execution= studentAtGate.logStudentEntry(studentWithDetails.getStudentId());
                out.print(" [{ \"execution\" : \""+execution+"\" }]");
            }
        }
        //will call class Gate
    }else if(requestReport!=null) {

        String requestedReport=request.getParameter("type"); //type of report, late students or gate log...
        String format = request.getParameter("format"); //format =json, excel
        //out.print("Report part ");
        Report report = new Report();

        if(requestedReport.intern() == "late_students" || requestedReport.intern() == "gate_log") {
            String startDateInput = request.getParameter("start_date");
            String endDateInput = request.getParameter("end_date");


            Converter stringDates = new Converter();
            java.sql.Date sqlStartDate = stringDates.toSqlDate(startDateInput);
            java.sql.Date sqlEndDate = stringDates.toSqlDate(endDateInput);

            Validate checkDates = new Validate();
            if (checkDates.isDate(sqlStartDate) && checkDates.isDate(sqlEndDate)) {


                //If the request is made to know about the late students
                if (requestedReport.intern() == "late_students") {

                    List<Student> lateStudents = report.getLateStudentsList(sqlStartDate, sqlEndDate);


                        if(format != null && format.intern() == "json") {
                            //converting list to JSON
                            Converter lateStudentsListToJson = new Converter();
                            JSONArray lateStudentsArray = lateStudentsListToJson.getJsonArray(lateStudents);

                            //print the json result
                            out.print(lateStudentsArray);
                        }
                        else if(format != null && format.intern() == "excel"){
                            //user requested for excel report
                            response.setContentType("application/vnd.ms-excel");
                            response.setHeader("Content-Disposition", "attachment; filename=lateStudents"+sqlStartDate+".xlsx");

                            Converter lateStudentsListToExcel = new Converter();
                            XSSFWorkbook excelFile=lateStudentsListToExcel.getExcelWorkbook(lateStudents);
                            try{
                                excelFile.write(response.getOutputStream());
                            } catch (Exception e){
                                out.print("[{\"execution\" : \" failed \"}]");
                            }
                        }

                /*    }else{

                        //user requested for excel report
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment; filename=lateStudents"+sqlStartDate+".xls");

                        Converter lateStudentsListToExcel = new Converter();
                        XSSFWorkbook excelFile=lateStudentsListToExcel.getExcelWorkbook(lateStudents);
                        try{
                            excelFile.write(response.getOutputStream());
                        } catch (Exception e){
                            out.print("[{\"execution\" : \" failed \"}]");
                        }

                    }*/
                } else if (requestedReport.intern() == "gate_log") //if the request is made to know about all the entries made at gate
                {
                    List<Student> allGateEntries = report.gateRegisterEntries(sqlStartDate, sqlEndDate);


                        if(format != null && format.intern() == "json") {
                            //converting list to JSON
                            Converter studentGateEntries = new Converter();
                            JSONArray studentGateEntriesArray = studentGateEntries.getJsonArray(allGateEntries);

                            //print the JSON
                            out.print(studentGateEntriesArray);
                        }
                        else if(format != null && format.intern() == "excel"){
                            //user requested for excel report
                            response.setContentType("application/vnd.ms-excel");
                            response.setHeader("Content-Disposition", "attachment; filename=gateList_"+sqlStartDate+".xlsx");

                            Converter lateStudentsListToExcel = new Converter();
                            XSSFWorkbook excelFile=lateStudentsListToExcel.getExcelWorkbook(allGateEntries);
                            try{
                                excelFile.write(response.getOutputStream());
                            } catch (Exception e){
                                out.print("[{\"execution\" : \" failed \"}]");
                            }
                        }
                }
            } else {
                out.print("[{\"execution\" : \"failed\"}]");
            }
        }else if(requestedReport.intern() == "students_present" || requestedReport.intern() == "students_absent"){
            List<Student> studentsList=new ArrayList<>();

            if(requestedReport.intern() == "students_present")
                 studentsList=report.getAttendanceFromGate(1);
            else if(requestedReport.intern() == "students_absent")
                studentsList=report.getAttendanceFromGate(0);

            if(format != null && format.intern() == "json") {
                //convert to json array
                Converter studentGateEntries = new Converter();
                JSONArray jsonStudentList = studentGateEntries.getJsonArray(studentsList);

                //print json
                out.print(jsonStudentList);
            }else if(format != null && format.intern() == "excel"){
                java.util.Date today=new java.util.Date();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=lateStudents"+today+".xlsx");

                Converter lateStudentsListToExcel = new Converter();
                XSSFWorkbook excelFile=lateStudentsListToExcel.getExcelWorkbook(studentsList);
                try{
                    excelFile.write(response.getOutputStream());
                } catch (Exception e){
                    out.print("[{\"execution\" : \" failed \"}]");
                }

            }
        }
    }


%>
