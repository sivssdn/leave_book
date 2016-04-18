package classes;

import java.sql.*;

/**
 * Created by admin on 15-04-2016.
 */

public class DataBase {

    private Connection con;
    private ResultSet rs;
    private PreparedStatement st;
    public String success;

    /**
     * constructor method sets up connection to db and gets into transaction mode
     * sets up the  success variable as failed in case of failure
     */
    public DataBase() {
        con = null;
        st = null;
        rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            //success="failed";
            success = e.getMessage();
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leave_book", "root", ""); //url of the db with username and password
            con.setAutoCommit(false); //to enable transactions
        } catch (SQLException e) {
            //success="failed";
            success = e.getMessage();
        }
        success = "success";
    }

    /**
     * performs insert query on database specified by sqlStatement query
     * returns String "success" on completion without interruption and String "failed" in case of any Exception
     */
    public String insert(String insertStatement) {
        String success = "inserted";
        st = null;
        try {
            //preparing insert statement
            st = con.prepareStatement(insertStatement);
            st.executeUpdate();
        } catch (Exception e) {
            //SQLException
            if (con != null) {
                try {
                    con.rollback();
                } catch (Exception e1) {
                    //cannot rollback
                    success = "failed";
                    return success;
                }
            }
            // e.printStackTrace(); //TO BE REMOVED
            //success = "failed";
            success = e.getMessage();
            return success; //terminate the function
        }
        return success;
    }

    //for select statement on mysql db
    public ResultSet select(String selectStatement) {
        st = null;
        rs = null;
        try {
            //preparing insert statement
            st = con.prepareStatement(selectStatement);
            rs = st.executeQuery();
        } catch (Exception e) {
            return rs; //return null if unable to perform select on the data
        }

        return rs;
    }

    /**
     * Used to perform update operation on database. Takes input update sql statement and return success in case of execution without interruption.
     */
    public String update(String updateStatement) {
        String success = "success";
        st = null;
        try {
            st = con.prepareStatement(updateStatement);
            st.executeUpdate();
        } catch (Exception e) {
            //probably SQLException
            success = "failed";
            return success;
        }


        return success;
    }

    /**
     * close() required to commit all changes to database
     * closes all the open connection in the db
     */
    //to close db connections
    public String close() {

        //REQUIRED for committing to the database.(for query to complete.)
        try {
            con.commit();
        } catch (SQLException e) {
            return "failed";
        }


        if (con != null) {   //closing connection
            try {
                con.close();
            } catch (Exception e) {
                //Ignore
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                //Ignore
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                //Ignore
            }
        }
        return "success";
    }
}
