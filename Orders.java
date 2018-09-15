package jdbctest;

import java.util.*;

public class Orders
{
    int orderId, userId;
    Date orderDate;
    double totalBill;
    String orderStatus;
    
    // Constructor for Orders
    public Orders(int orderId, int userId,  Date orderDate, String orderStatus, double totalBill)
    {
        this.orderId = orderId; 
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalBill = totalBill;     
    }
    
    // To get order ID
    public int getID()
    {
        return orderId;
    }
    
    // To get order date
    public Date getOrderPlacingDate()
    {
        return orderDate;
    }
    
    // To get order total
    public double getTotal()
    {
        return totalBill;
    }
    
    // To get order status
    public String getOrderStatus()
    {
        return orderStatus;
    }
}