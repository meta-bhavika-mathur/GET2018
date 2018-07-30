package shoppingcart;
import java.util.*;

class Product
{
	int pro_id;
	String pro_name;
	int pro_qty;
	double pro_price;

	public Product()
	{
		pro_id=pro_qty=0;
		pro_name="";
		pro_price=0.0;
	}
	public Product(int id,String name,int quantity,double price)
	{
		pro_id=id;
		pro_name=name;
		pro_qty=quantity;
		pro_price=price;
	}

	// Get Methods
	public int getProductId()
	{
		return pro_id;
	}
	public String getProductName()
	{
		return pro_name;
	}
	public int getProductQuantity()
	{
		return pro_qty;
	}
	public double getProductPrice()
	{
		return pro_price;
	}
	public void displayProductInfo()
	{
		System.out.println("Id:"+pro_id);
		System.out.println("Name:"+pro_name);
		System.out.println("Quantity:"+pro_qty);
		System.out.println("Total Price:"+pro_price);
	}

	//Set Methods
	public void setProductId(int id)
	{
		pro_id=id;
	}
	public void setProductName(String name)
	{
		pro_name=name;
	}
	public void setProductQuantity(int qty)
	{
		pro_qty=qty;
	}
	public void setProductPrice(double price)
	{
		pro_price=price;
	}
	public void setProductInfo()
	{
		
		Scanner in=new Scanner(System.in);
		
		System.out.println("Enter product id:");
		int id=in.nextInt();
		System.out.println("Enter product name:");
		String name=in.next();
		System.out.println("Enter product quantity");
		int quantity=in.nextInt();
		System.out.println("Enter product price:");
		double price=in.nextDouble();
		
		pro_id=id;
		pro_name=name;
		pro_qty=quantity;
		pro_price=price;
		
	}
	
}
