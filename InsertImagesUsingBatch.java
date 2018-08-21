package jdbctest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// JDK 1.7 and above
public class InsertImagesUsingBatch
{
    public static void main(String[] args) 
    {
        String strInsert = "INSERT INTO Image (product_id, image_link, alternative_text) VALUES(?,?,?)";
        try (
               
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(strInsert);
            ) 
            {
            try 
            {
                conn.setAutoCommit(false);
                System.out.println("The SQL query is: " + strInsert); 
                System.out.println();
                
               String url[] = new String[6];
               url[1] = "C:ImagePeterEngland789.jpg";
               url[2] = "C:ImagePeterEngland300054.jpg";
               url[3] = "C:ImagePeterEngland78512.jpg";
               url[4] = "C:ImagePeterEngland99.jpg";
               url[5] = "C:ImagePeterEngland1455.jpg";
                
                for (int i = 1; i <= 5; i++) 
                {
                    stmt.setInt(1, 3);
                    stmt.setString(2, url[i]);
                    stmt.setString(3, "Image Not Available");
                    stmt.addBatch();
                }

                int[] result = stmt.executeBatch();
                System.out.println("The number of rows inserted: " + result.length);
                conn.commit();

            } 
            catch (SQLException ex)
            {
                ex.printStackTrace();
                conn.rollback();
            }
        }
       
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static Connection getConnection()
    {
      Connection conn = null;
      try
      {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/storefront";
        conn = DriverManager.getConnection(url, "root", "1234");
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
        System.exit(1);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        System.exit(2);
      }
      return conn;
    }
}