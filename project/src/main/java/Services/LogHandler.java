package Services;

import Interfaces.*;

import javax.xml.soap.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Kasper on 07-09-2015.
 */
public class LogHandler implements Interfaces.DatabaseConnection
{
    private static String url = "jdbc:mysql://localhost:3306/testlog_db";
    private static String username = "root";
    private static String password = "test1234";
    private static String table = "log2";

    public static void addNewRow(String user, String message)
    {
        Connection conn = null;
        Statement stmt = null;

        try
        {
            System.out.println("Connecting database...");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to database successful...");
            UUID id = UUID.randomUUID();
            // Creating a timestamp in SQL format
            java.util.Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());

            stmt = conn.createStatement();

            String sql = "INSERT INTO " + table + " VALUES ('" + id + "', '" + timestamp + "', '" + user + "', '" + message + "')";
            stmt.executeUpdate(sql);

            System.out.println("Inserted records into the table...");

        }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            //finally block used to close resources
            try
            {
                if(stmt!=null)
                    conn.close();
            }
            catch(SQLException se)
            {
            }// do nothing
            try
            {
                if(conn!=null)
                    conn.close();
            }
            catch(SQLException se)
            {
                se.printStackTrace();
            }
        }//end finally try
    }
}
