package bhavika;

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

	//get Methods
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
class ShoppingCart
{
	List<Product> list;
	ShoppingCart()
	{
		list=new ArrayList<Product>();
	}
	public void addProductToCart(Product product)
	{
		list.add(product);
	}
	public void deleteProductFromCart(int id)
	{
		for(Iterator<Product> i=list.iterator();i.hasNext();)
		{
			Product item=(Product)i.next();
			if(item.getProductId()==id)
			{
				list.remove(item);
			}
		}
	}
 	public void updateProductQuantity(int product_id,int new_quantity)
	{
		
 		for(Iterator<Product> i=list.iterator();i.hasNext();)
		{
			Product item2=(Product)i.next();
			if(item2.getProductId()==product_id)
			{
				if(product_id<=0)
					list.remove(item2);	
				else
					item2.setProductQuantity(new_quantity);
			}
		}
 		
		
	} 
	public double generateTotalBill()
	{
		double total_bill=0.0;
		for(Iterator<Product> i=list.iterator();i.hasNext();)
		{
			Product item=(Product)i.next();
			double total_price=item.getProductQuantity()*item.getProductPrice();
			total_bill=total_bill+total_price;
		}
		return total_bill;
	}
	public void displayCartItems()
	{
		for(Iterator<Product> i=list.iterator();i.hasNext();)
		{
			Product item=(Product)i.next();
			item.displayProductInfo();
		}
	}
		
		
		
	
}

class CartDemo
{
	public static void main(String args[])
	{
		
		ShoppingCart cart1=new ShoppingCart();
		Scanner in=new Scanner(System.in);
		int choice;
		System.out.println("Enter you choice:");
	
		do
		{
			System.out.println("1.Add item to cart");
			System.out.println("2.Remove item from cart");
			System.out.println("3.Update quantity in cart:");
			System.out.println("4.Display Cart Items:");
			System.out.println("5.Generate Bill:");
			
			choice=in.nextInt();
			
			switch(choice)
			{
					case 1:Product p1=new Product();
						   p1.setProductInfo();
						   cart1.addProductToCart(p1);
						   break;
				    case 2:System.out.println("Enter id of item to delete:");
				    		int id=in.nextInt();
				    	   cart1.deleteProductFromCart(id);
						   break;
					case 3:Scanner s1=new Scanner(System.in);
						   System.out.println("Enter product id for updation:");
						   int id_to_update=s1.nextInt();
						   System.out.println("Enter new quantity:");
						   int new_qty=s1.nextInt();
						   cart1.updateProductQuantity(id_to_update,new_qty);
						   break;
					case 4:System.out.println("Cart items are:");
					       cart1.displayCartItems();
					       break;
					case 5:System.out.println("Total bill is:"+cart1.generateTotalBill());
							break;
					default:System.out.println("Invalid choice:");
							break;
					   
			}
			System.out.println("Do you want to continue??:Type 1 for yes and 0 for No");
			choice=in.nextInt();
	
	   }while(choice!=0);
	
		
	}
}




























