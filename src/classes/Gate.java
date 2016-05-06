package classes;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class represent the Gate object which is used by each student while coming in or going out of campus.
 */
public class Gate {
    private String studentId;
    private Date dateOut;
    private Time timeOut;
    private Date dateIn;
    private Time timeIn;

    //all in one setter function
    public void setSigningDetailsAtGate(String studentId,Date dateOut,Time timeOut,Date dateIn,Time timeIn){
        this.studentId=studentId;
        this.dateOut=dateOut;
        this.timeOut=timeOut;
        this.dateIn=dateIn;
        this.timeIn=timeIn;
    }

    //getter functions
    public String getStudentId(){
        return this.studentId;
    }
    public Date getDateOut(){
        return this.dateOut;
    }
    public Time getTimeOut(){
        return this.timeOut;
    }
    public Date getDateIn(){
        return this.dateIn;
    }
    public Time getTimeIn(){
        return this.timeIn;
    }

    public String logStudentExit(String studentId){
        String execution="failed";

        Validate input=new Validate();
        //java.sql.date and time
        if (input.isStudentId(studentId)) {

            Converter dateAndTime=new Converter();

            //convert present date to sql date format
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilPresentDate=new java.util.Date();
            java.sql.Date sqlPresentDate=dateAndTime.toSqlDate(dateFormat.format(utilPresentDate));

            //convert present time to sql time
            SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
            java.util.Date utilPresentTime=new java.util.Date();
            java.sql.Time sqlPresentTime=dateAndTime.toSqlTime(timeFormat.format(utilPresentTime.getTime()));

            //student id, date_out and time_out prepared, now insert into database
            DataBase gate=new DataBase();
            if(gate.success.intern() == "success") {
                String insertStatement = "INSERT INTO `gate` (`serial`, `student_id`, `date_out`, `time_out`, `date_in`, `time_in`,`late`) VALUES (NULL, '"+studentId+"', '"+sqlPresentDate+"', '"+sqlPresentTime+"', '0000-00-00', '00:00','0');";
                execution=gate.insert(insertStatement);
            }

            gate.close();
        }

        return execution;
    }
}
