
/*
This class is called by Tester.java.
It checks the functioning of Permissions.java. It is invoked by SimulateWarden() function which is supposed to work as a warden.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;

public class Permissiontest {
    Date testdateOut;
    Time testtimeOut;
    Date testdateIn;
    Time testtimeIn;
    Permission check;
    Convert obj;

    public void genRandDateandTime()throws IOException
    {
        BufferedReader datenTime= new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Date out by warden");
        this.testdateOut= obj.toSqlDate(datenTime.readLine());//converting string input to date type using class Convert
        System.out.println("Enter Time out by warden");
        this.testtimeOut=obj.toSqlTime(datenTime.readLine());;
        System.out.println("Enter Date in by warden");
        this.testdateIn=obj.toSqlDate(datenTime.readLine());;
        System.out.println("Enter Time in by warden");
        this.testtimeIn=obj.toSqlTime(datenTime.readLine());;

    }

    //check if callSetter function works with different values
    public boolean callPermissionSetter()
    {
        check.setPermissionDetails(testdateOut,testtimeOut,testdateIn,testtimeIn);
        Date getDout=check.getDateOut();
        Time getTout=check.getTimeOut();
        Date getDin=check.getDateIn();
        Time getTin=check.getTimein();
        if(testdateOut==getDout && testdateIn==getDin && testtimeIn==getTin && testtimeOut== getTout)
        {
            return(true);//works
        }
        else
        {
            return(false);
        }

    }

    public boolean checkGivingPermission(String testStudentId,boolean isValid)
    {
        String result = check.givePermission(testStudentId);
        //first half of the array are invalid inputs
        if(isValid==true)//if valid inputs give valid outputs then return(true).
        {
            if(result.equals("Dates not valid") || result.equals("failed"))
            {
                return(false);
            }
            else
                return(true);
        }
        else //invalid inputs give invalid outputs return(true)
        {
            if(result.equals("Dates not valid") || result.equals("failed"))
            {
                return(true);
            }
            else
                return(false);
        }

        // inputs are feeded to the module from two distinct set of valid inputs and invalid inputs. divided in half
    }


}
