package shoppingcart;

import java.util.*;

class ShoppingCart
{
	List<Product> list;  // List of products
	int todaysDate;		 // Current date
	int promoCode = 123;
	boolean hasPromoCode;
	FixedOrderPromotion fixedOrderPromotion = new FixedOrderPromotion();
	FixedProductPromotion fixedProductPromotion = new FixedProductPromotion();
	
	// Constructor for shopping cart
	ShoppingCart()
	{
		list = new ArrayList<Product>();
		todaysDate = 20;
		hasPromoCode = true;
	}
	
	/**
	 * To add product to list of products in cart
	 * @param product, product to be added to the list
	 */
	public void addProductToCart(Product product)
	{
		list.add(product);
	}
	
	/**
	 * To delete a product from the shopping cart
	 * @param id, product id to identify the product to be deleted
	 */
	public void deleteProductFromCart(int id)
	{
		for(Iterator<Product> i = list.iterator(); i.hasNext(); )
		{
			Product item = (Product)i.next();
			if(item.getProductId() == id)
			{
				list.remove(item);
			}
		}
	}
	
	/**
	 * To update quantity of product in shopping cart
	 * @param product_id, id of the product whose quantity is to be updated
	 * @param new_quantity, new quantity for updation
	 */
 	public void updateProductQuantity(int product_id, int new_quantity)
	{
 		for(Iterator<Product> i = list.iterator(); i.hasNext();)
		{
			Product item = (Product)i.next();
			if( item.getProductId() == product_id )
			{
				if( product_id <= 0 )
					list.remove(item);	
				else
					item.setProductQuantity(new_quantity);
			}
		}
 			
	} 
 	
 	/**
 	 * To generate total bill of current shopping cart after applying the promotions if applicable
 	 * @return final bill of the shopping cart
 	 */
	public double generateTotalBill()
	{
		double total_bill = 0.0;
		double priceAfterDiscount;
		double total_price;
		
		// Generate total bill by reading each item of shopping cart one by one
		for( Iterator<Product> i = list.iterator(); i.hasNext(); )
		{
			Product item =(Product)i.next();
			
			// To apply fixed discount applicable on the product, requires that its id match with the product in offer
			if(fixedProductPromotion.isPromotionApplicable(item.getProductId()))
			{	
				priceAfterDiscount = (( 100.00 - fixedProductPromotion.getFixedDiscount()) * item.getProductPrice()) / 100.00;
				total_price = priceAfterDiscount * item.getProductQuantity();
			}
			else
			{
				total_price = item.getProductPrice() * item.getProductQuantity();
			}
			total_bill = total_bill + total_price;
		}
		
		// To check if promotion code is available and valid
		if (( hasPromoCode )  && fixedOrderPromotion.isPromotionApplicable( promoCode ))
		{			 
			// Apply fixed order discount if total bill amount exceeds the minimum amount for discount
			if( total_bill >= fixedOrderPromotion.getMinimumPrice())
				total_bill = (( 100.00 - fixedOrderPromotion.getFixedDiscount())* total_bill) / 100.00;		
		}
		
		return total_bill;
	}
	
	/**
	 * To display items in cart one by one
	 */
	public void displayCartItems()
	{
		for(Iterator<Product> i = list.iterator();i.hasNext();)
		{
			Product item = (Product)i.next();
			item.displayProductInfo();
		}
	}
}