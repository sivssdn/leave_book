/*
This class is used by Tester.java.
Here, the checking of Log at gate is being checked.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;


public class Gatetest {

        Date testdateOut;
        Time testtimeOut;
        Date testdateIn;
        Time testtimeIn;
        String teststudentId;
        Gate check;
        Convert obj;

        public void genRandDateandTime(String studentId)throws IOException
        {
            BufferedReader actualdatentime=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Student leave date");
            this.testdateOut=obj.toSqlDate(actualdatentime.readLine());
            System.out.println("Enter Student leave time");
            this.testtimeOut=obj.toSqlTime(actualdatentime.readLine());
            System.out.println("Enter Student return date");
            this.testdateIn=obj.toSqlDate(actualdatentime.readLine());
            System.out.println("Enter Student return time");
            this.testtimeIn=obj.toSqlTime(actualdatentime.readLine());
            this.teststudentId=studentId;

        }

        //check if callSetter function works with different values
        public boolean callGateSetter()
        {
            check.setSigningDetailsAtGate(teststudentId,testdateOut,testtimeOut,testdateIn,testtimeIn);
            String getId= check.getStudentId();
            Date getDout=check.getDateOut();
            Time getTout=check.getTimeOut();
            Date getDin=check.getDateIn();
            Time getTin=check.getTimein();
            if(getId.equals(teststudentId) && testdateOut==getDout && testdateIn==getDin && testtimeIn==getTin && testtimeOut== getTout)
            {
                return(true);//works
            }
            else
            {
                return(false);
            }

        }

        public boolean logExitWorking(boolean hasPermission)
        {
            String result = check.logStudentExit(teststudentId);
            if(hasPermission)//sending filtered data with different cases of valid inputs
            {
                if(result.equals("no permission") || result.equals("failed"))
                    return(false);
                else
                    return(true);
            }
            else
            {
                if(result.equals("no permission") || result.equals("failed"))
                    return(true);
                else
                    return(false);
            }


        }

}
