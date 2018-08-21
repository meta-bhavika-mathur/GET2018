package jdbctest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// JDK 1.7 and above
public class JDBCSelectTest 
{    
    public static void main(String[] args) 
    {
        String strSelect = "select order_id, total_bill from orders WHERE user_id = 6";
        try (
                
                Connection conn = getConnection();

                PreparedStatement stmt = conn.prepareStatement(strSelect);
            ) 
            {
            
            System.out.println("The SQL query is: " + strSelect); // Echo For debugging
            System.out.println();

            ResultSet rset = stmt.executeQuery();

         
            System.out.println("The records selected are:");
            int rowCount = 0;
            while (rset.next()) 
            { 
                String orderId = rset.getString("order_id");
                double totalBill = rset.getDouble("total_bill");

                System.out.println(orderId + ", " + totalBill );
                ++rowCount;
            }
            System.out.println("Total number of records = " + rowCount);

        } catch (SQLException ex)
        {
            ex.printStackTrace();
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