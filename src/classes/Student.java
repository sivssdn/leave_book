package classes;

/**
 * Created by admin on 23-04-2016.
 */
public class Student {
    private String studentId;
    private String name;
    private long primaryContact;
    private long secondaryContact;
    private int batch;
    private String email;
    private String hostel;
    private int roomNumber;
    private String hid;
    private String permission;
    private String image;
    private int status;

    //every student has permit to go out and come in and signs off at the gate, therefore object of class Permission and Gate
    public Permission permit;
    public Gate gateTimings;

    //All in one setter function
    public void setStudentDetails(String studentId,String name,long primaryContact,long secondaryContact,int batch,String email,String hostel,int roomNumber,String hid,String image,String permission,int status){
        this.studentId=studentId;
        this.name=name;
        this.primaryContact=primaryContact;
        this.secondaryContact=secondaryContact;
        this.batch=batch;
        this.email=email;
        this.hostel=hostel;
        this.roomNumber=roomNumber;
        this.hid=hid;
        this.permission=permission;
        this.image=image;
        this.status=status;
    }
    public void setPermit(Permission permit){
        this.permit=permit;
    }
    public void setGateTimings(Gate gateTimings){
        this.gateTimings=gateTimings;
    }
    /*
    getter functions for class student
     */
    public String getStudentId(){
        return this.studentId;
    }
    public String getName(){
        return this.name;
    }
    public long getPrimaryContact(){
        return this.primaryContact;
    }
    public long getSecondaryContact(){
        return this.secondaryContact;
    }
    public int getBatch(){
        return this.batch;
    }
    public String getEmail(){
        return this.email;
    }
    public String getHostel(){
        return this.hostel;
    }
    public int getRoomNumber(){
        return this.roomNumber;
    }
    public String getImage(){
        return this.image;
    }
    public String getPermission(){
        return this.permission;
    }
    public String getHid(){
        return this.hid;
    }
    public int getStatus(){
        return this.status;
    }
    public Permission getPermit(){
        return this.permit;
    }
    public Gate getGateTimings(){
        return this.gateTimings;
    }
}
