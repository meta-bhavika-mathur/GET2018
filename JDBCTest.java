package jdbctest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JDBCTest
{
    DatabaseQueries msq = new DatabaseQueries();
    
    @Test
    public void testGetAllOrdersByState()
    {
        List<Orders> orderList= msq.getShippedOrdersOfUser(6, "Shipped");
        
        int[] expectedOutput = {4, 3};
        int i = 0;
        for(Orders order: orderList)
        {
            int actualOutput = order.getID();
            assertEquals(expectedOutput[i], actualOutput);
            i++;
        }
    }

    @Test
    public void testAddImagesWithListOfProductUrl()
    {
        int productId = 2;
        String url[] = new String[5];
        
        url[0] = "C:ImagePeterEngland1455.jpg";
        url[1] = "C:ImagePeterEngland789.jpg";
        url[2] = "C:ImagePeterEngland300054.jpg";
        url[3] = "C:ImagePeterEngland78512.jpg";
        url[4] = "C:ImagePeterEngland99.jpg";
        
        int expectedOutput = 5;
        int actualOutput = msq.addProductImages(productId, url);
        
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void testUpdateStatusOfProduct()
    {
        int expectedOutput = 0;
        int actualOutput = msq.changeInactiveProductStatus();
        
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetTopCategoryWithChildrenCount()
    {
        List<Category> categoryList = msq.getTopCategoryWithChildrenCount();
        
        String[] expectedOutput = {"Electronics 4", "Clothing 4"};
                                   
        int i = 0;
        for(Category category: categoryList)
        {
            String actualOutput = category.getName() + " " + category.getSubCategoryCount();
            
            assertEquals(expectedOutput[i], actualOutput);
            i++;
        }
    }

    @Test
    public void testCountSubCategories()
    {
        int expectedOutput = 4;
        int actualOutput = msq.countSubCategories(1);
        
        assertEquals(expectedOutput, actualOutput);
    }

}
