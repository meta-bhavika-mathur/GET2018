package jdbctest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DatabaseQueries 
{
    JDBCConnectivity jdbcConnect = new JDBCConnectivity();
    
    /**
     * Returns all orders of a user which are in particular state
     * @param userID userID of user
     * @param state order state
     * @return orderID, orderPlacingDate, orderTotal
     */
    public List<Orders> getShippedOrdersOfUser(int userId, String state)
    {
        List<Orders> orderList = new ArrayList<Orders>();
        
        String query =          "SELECT o.order_id, o.order_date, o.total_bill" + 
                                "FROM Orders AS o " + 
                                "LEFT JOIN ProductsInline AS pi ON o.order-id = opr.orderID " + 
                                "LEFT JOIN product AS p ON opr.productID = p.productID " + 
                                "WHERE o.userID = ? AND o.overallStatus = ? " + 
                                "GROUP BY o.orderID " + 
                                "ORDER BY o.orderPlacingDate DESC ";
        
        try (
                
                Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "StoreFront");
                PreparedStatement stmt = connection.prepareStatement(query);
            ) 
            {
                stmt.setInt(1, userId);
                stmt.setString(2, state);
                
                ResultSet rset = stmt.executeQuery();
    
                while (rset.next()) 
                { 
                    int orderId = rset.getInt("order_id");
                    Date date = rset.getDate("order_date");
                    int uid = rset.getInt("user_id");
                    String productStatus = rset.getString("product_status");
                    double totalBill = rset.getDouble("product_total");

                    Orders order = new Orders(orderId , uid, date,  productStatus, totalBill);
                    orderList.add(order);
                }

            } 
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }    
            return orderList;
    }
    
    /**
     * Adds multiple images
     * @param imageList list of image objects
     */
    public int addProductImages(int productId, String[] imageUrl)
    {
        String strInsert = "INSERT INTO Image (product_id, image_link, alternative_text) VALUES(?,?,?)";
        int length = 0;
        try (
               
                Connection connection = jdbcConnect. getMysqlConnection("root", "1234", "StoreFront");
                PreparedStatement stmt = connection.prepareStatement(strInsert);
            ) 
            {
            try 
            {
                connection.setAutoCommit(false);
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
                    stmt.setInt(1, productId);
                    stmt.setString(2, url[i]);
                    stmt.setString(3, "Image Not Available");
                    stmt.addBatch();
                }

                int[] result = stmt.executeBatch();
                System.out.println("The number of rows inserted: " + result.length);
                length = result.length;
                connection.commit();

            } 
            catch (SQLException ex)
            {
                ex.printStackTrace();
                connection.rollback();
            }
        }
       
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return length;
    }
    
    
    /**
     * Updates product status to deleted 
     * for all those products which were not ordered by any Shopper in last 1 year. 
     * @return number of products updated
     */
    public int changeInactiveProductStatus()
    {
        int numberOfProductsDeleted = 0;
        
        Connection connection = jdbcConnect.getMysqlConnection("root", "1234", "StoreFront");
        
        String query =  "UPDATE Product SET product_status = 'inactive' WHERE product_id NOT IN ( " +
                        "SELECT pi.product_id FROM ProductsInline AS pi " +
                        "LEFT JOIN Orders as o ON o.order_id = pi.order_id " +
                        "WHERE o.order_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) GROUP BY product_id);";
        
        try
        (
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        )
        {
            numberOfProductsDeleted = preparedStatement.executeUpdate();
        } 
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return numberOfProductsDeleted;
    }
    
    /**
     * Returns all categories which have no parent category
     * along with number of children categories it have.
     * @return list of top categories along with count of its sub categories
     */
    public List<Category> getTopCategoryWithChildrenCount()
    {
        Connection connection = jdbcConnect.getMysqlConnection("root", "1234", "storeFront");
        
        List<Category> categoryList = new ArrayList<Category>();
        
        String query = "SELECT category_id, category_name " + 
                       "FROM Category " + 
                       "WHERE parent_category_id IS NULL " + 
                       "ORDER BY category_name";
        
        try
        (
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        )
        {
            ResultSet topCategories = preparedStatement.executeQuery();
            while(topCategories.next())
            {
                int topCategoryID = topCategories.getInt("category_id");
                String topCategoryName = topCategories.getString("category_name");
                int subCategoriesCount = countSubCategories(topCategoryID);
                
                Category category = new Category(topCategoryID, topCategoryName, subCategoriesCount);
                categoryList.add(category);
            }
        } 
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return categoryList;
    }
    
    /**
     * Recursively counts sub categories of parent category
     * @param categoryID
     * @return
     */
    public int countSubCategories(int categoryID)
    {
        int count = 0;
        Connection connection = jdbcConnect.getMysqlConnection("root", "1234", "storeFront");
        try
        {
            String subCategories = "SELECT category_id " + 
                                   "FROM Category " + 
                                   "WHERE parent_category_id = ?";

            PreparedStatement subCategoriesStatement = 
                    connection.prepareStatement(subCategories);

            subCategoriesStatement.setInt(1, categoryID);
            ResultSet subCategoriesSet = subCategoriesStatement.executeQuery();
            
            while(subCategoriesSet.next())
            {
                int subCategoryID = subCategoriesSet.getInt("category_id");
                
                count = count + 1 + countSubCategories(subCategoryID);
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
}

