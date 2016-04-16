package classes;

import javax.xml.transform.Result;
import java.sql.*;
/**
 * Created by admin on 15-04-2016.
 */

public class DataBase {
    /*
    public String Insert(String student_id,String date_out,String time_out,String date_in,String time_in) {
        String success = "failed";
        //getting from other page

        String studentId = student_id;
        String dateOut = date_out;
        String timeOut = time_out;

        //only proceed if the id, date_out and timeout is received
        if ((studentId != null && studentId != "") && dateOut != null && timeOut != null) {

            String dateIn = (date_in == null) ? "0000-00-00" : date_in;
            String timeIn = (time_in == null) ? "0000-00-00" : time_in;

            //SQL specific variables

            Connection con = null;
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                success = "failed";
            }

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp", "root", "");
                con.setAutoCommit(false); //to enable transactions

                //checking if the record already exist
                st = con.prepareStatement("SELECT id FROM employees WHERE age=12;");
                rs = st.executeQuery();
                success = "exists";
                if (!(rs.getFetchSize() > 0)) //if the row does not exists
                {
                    //Inserting into tables
                    st = con.prepareStatement("INSERT INTO employees VALUES(1,2,'Atif ','Aslam5');");
                    st.executeUpdate();
                    success = "success";
                }
                con.commit(); //committing to the db
            } catch (SQLException e) {
                if (con != null)
                {
                    try{
                        con.rollback();
                    }catch(Exception e1)
                    {
                        success="failed";
                    }
                }
                e.printStackTrace(); //TO BE REMOVED
                success = "failed";
            } finally {
                //closing all the connections
                if (con != null)
                {
                    try{
                        con.close();
                    }catch(Exception e){
                        // success=e.getMessage();
                        //ignore
                    }
                }

                if (st != null)
                {
                    try{
                        st.close();
                    }catch (Exception e){
                        // success=e.getMessage();
                        //ignore
                    }
                }

                if(rs!=null)
                {
                    try {
                        rs.close();
                    }
                    catch(Exception e ){
                        //ignore
                    }
                }

            }
        }
        return success;
    }*/
    //takes in sql insert command as string and return String success=success if the operation is performed uninterrupted
    Connection con;
    ResultSet rs;
    PreparedStatement st;
    public String success;

    /**
     * constructor method sets up connection to db and gets into transaction mode
     * sets up the  success variable as failed in case of failure
    */
    public DataBase(){
        con=null;
        st=null;
        rs=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            success="failed";
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp", "root", ""); //url of the db with username and password
            con.setAutoCommit(false); //to enable transactions
        }catch (SQLException e){
            success="failed";
        }
        success="success";
    }

    /**
     * performs insert query on database specified by sqlStatement query
     * returns String "success" on completion without interruption and String "failed" in case of any Exception
     * */
    public String insert(String sqlStatement){
        String success="failed";

         try{
             //preparing insert statement
             st = con.prepareStatement(sqlStatement);
             st.executeUpdate();

             success = "success";
         }catch(SQLException e){
             if (con != null)
             {
                 try{con.rollback();}catch(Exception e1) {
                         //cannot rollback
                         success="failed";
                         return success;
                 }
             }
            // e.printStackTrace(); //TO BE REMOVED
             success = "failed";
             return success; //terminate the function
         }
             return success;
    }

    //for select statement on mysql db
    public ResultSet select(String sqlStatement){

        try {
            //preparing insert statement
            st = con.prepareStatement(sqlStatement);
            rs=st.executeQuery();
        }catch(Exception e){
            return rs; //return null if unable to perform select on the data
        }

        return rs;
    }

    /**
     *  close() required to commit all changes to database
     *  closes all the open connection in the db
     * */
    //to close db connections
    public String close(){

        try{con.commit();}catch(SQLException  e){return "failed";}


        if(con!=null)
        {   //closong connection
            try{con.close();}catch(Exception e){
                //Ignore
            }
        }
        if(st!=null){
            try{st.close();}catch (Exception e){
                //Ignore
            }
        }
        if(rs!=null){
            try{rs.close();}catch (Exception e){
                //Ignore
            }
        }
        return "success";
    }
}
