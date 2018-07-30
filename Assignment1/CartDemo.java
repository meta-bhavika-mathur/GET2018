package shoppingcart;
import java.util.*;

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
					case 5:System.out.println("Total bill is:"+ cart1.generateTotalBill());
							break;
					default:System.out.println("Invalid choice:");
							break;
					   
			}
			System.out.println("Do you want to continue??:Type 1 for yes and 0 for No");
			choice=in.nextInt();
	
	   }while(choice!=0);
	
		
	}
}

