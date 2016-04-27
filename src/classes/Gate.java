package classes;

import java.sql.Date;
import java.sql.Time;

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
}
