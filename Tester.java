/*This is the main class used for testing of the digital leave_book.
Currently this module can test whether a function is working/giving results as expected on being fed monitored values.
Testing for the correctness of the outputs itself has not been included in this module as the like have been done through the code.
The detail of testing is going to depend on the detail of corner cases covered while giving inputs to this class
 */
package classes;



import java.io.*;


public class Leavetest {
    Student testStudent ;
    Permissiontest perm ;
    Gatetest atgate;
    Database testEntries;

    public static void GenStudent()throws IOException// Taking user input for generation of student
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Student details");
        System.out.println("StudentId");
        String student_id=br.readLine();
        System.out.println("Name");
        String name=br.readLine();
        System.out.println("Primary contact");
        long primary_contact=Long.parseLong(br.readLine());
        System.out.println("Secondary Contact");
        long secondary_contact=Long.parseLong(br.readLine());
        System.out.println("batch");
        int batch = Integer.parseInt(br.readLine());
        System.out.println("email");
        String email=br.readLine();
        System.out.println("hostel");
        String hostel = br.readLine();
        System.out.println("roomNumber");
        int roomNumber= Integer.parseInt(br.readLine());;
        System.out.println("hid");
        String hid=br.readLine();
        String insertStatement = "INSERT INTO `leave_book`.`master` (`student_id`, `name`, `primary_contact`, `secondary_contact`, `batch`, `email`, `hostel`, `room_number`, `hid`, `image`, `permission`, `status`) VALUES (studentId,name,primary_contact,secondary_contact,batch,email,hostel,roomNumber,hid,NULL,NULL,NULL)";//insert statement without image and permissions which will be updated by the SimulateWarden() method.
        String result = testEntries.insert(insertStatement);
        if(result.equals("success"))
        {
            System.out.println("Database insertion works");// checking connection with database
        }





    }
    public static void SimulateWarden(boolean validity)
    {
        String testStudentId;
        testStudentId = testStudent.getStudentId();
        boolean permission_setter_works= perm.callPermissionSetter();//check if setter and getter functions work as expected
        boolean giving_permission_works= perm.checkGivingPermission(testStudentId,validity);//check if Warden can give valid permissions.
        if(permission_setter_works && giving_permission_works)
            System.out.println("Permissions module is fine");



    }

    public static void SimulateGate(boolean haspermission)
    {
        boolean gatesetterworks= atgate.callGateSetter();
        boolean exitlogworks=atgate.logExitWorking(haspermission);//checking if response at gate during exit is what it should be
        if(gatesetterworks && exitlogworks)
        {
            System.out.println("Gate module is fine");
        }



    }


    public static void main(String arg[])throws IOException
    {
        boolean valid;
        BufferedReader ob = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input number of student entries");
        int numEntries= Integer.parseInt(ob.readLine());
        System.out.println("enter first half of the entries valid covering different corner cases");
        System.out.println("Cover invalid cases in the next set of values");

        for(int i=1;i<=numEntries; i++)
        {
            if(i<numEntries/2)
                valid = true;//important to make sure that 1st half of the inputs would expect a valid output
            else
                valid=false;

            GenStudent();
            SimulateWarden(valid);
            SimulateGate(valid);//Checking the working of the modules for every input.
        }






    }
}
