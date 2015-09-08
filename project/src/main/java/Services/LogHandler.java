package Services;

import Interfaces.*;

import javax.xml.soap.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by Kasper on 07-09-2015.
 */
public class LogHandler implements Interfaces.DatabaseConnection
{
    String url = "jdbc:mysql://localhost:3306/testlog_db";
    String username = "root";
    String password = "test1234";

    public void addNewRow(Date date, String user, String message)
    {
        Connection conn = null;
        Statement stmt = null;

        try
        {
            System.out.println("Connecting database...");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected database successfully...");

            stmt = conn.createStatement();

            String sql = "INSERT INTO log VALUES (' " + user + "', '" + message + "')";
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



       /* try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }*/

    }

}
