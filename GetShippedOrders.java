package jdbctest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class GetShippedOrders
{    
    public static void main(String[] args) 
    {
        String strSelect = "SELECT o.order_id, o.order_date, o.user_id, pi.product_status, pi.product_total FROM Orders As o " +
                           "LEFT JOIN ProductsInline AS pi ON o.order_id = pi.order_id " +
                           "AND  pi.product_status = 'Shipped' ORDER BY o.order_date;" ;
        try (
                
                Connection conn = getConnection();

                PreparedStatement stmt = conn.prepareStatement(strSelect);
            ) 
            {
            ResultSet rset = stmt.executeQuery();

            System.out.println("The records selected are:\n");
            int rowCount = 0;
    
            while (rset.next()) 
            { 
                String orderId = rset.getString("order_id");
                Date date = rset.getDate("order_date");
                int userId = rset.getInt("user_id");
                String productStatus = rset.getString("product_status");
                double totalBill = rset.getDouble("product_total");

                System.out.println(orderId + " " + userId + " " + date + " " + productStatus + " " + totalBill);
                ++rowCount;
            }
            System.out.println("\nTotal number of records = " + rowCount);

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