package jdbctest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCDemo
{
    public static void main(String[] args)
    {
        DatabaseQueries msq = new DatabaseQueries();
        
        for(Orders o: msq.getShippedOrdersOfUser(2, "shipped"))
        {
            System.out.println(o.getID() + " " + o.getOrderPlacingDate() + " " + o.getTotal());
        }
        
        System.out.println();
        
 /*       String url[] = new String[6];
        
       
        url[1] = "C:image1.jpg";
        url[2] = "C:Image00054.jpg";
        url[3] = "C:Image78512.jpg";
        url[4] = "C:Imaged99.jpg";
        url[5] = "C:Image1455.jpg";
       
        
        System.out.println(msq.addProductImages(5, url)); */
    }
}
        
   /*     System.out.println(msq.changeInactiveProductStatus());
        
        System.out.println();
        
        for(Category c: msq.getTopCategoryWithChildrenCount())
        {
            System.out.println(c.getName() + " " + c.getSubCategoryCount());
        }
    }
}*/