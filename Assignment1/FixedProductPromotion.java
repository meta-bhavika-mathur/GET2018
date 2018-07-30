package shoppingcart;

public class FixedProductPromotion implements Promotion {
	
	double minimumPrice;
	double discount;
	int todaysDate; 
	int productIdOnPromotion;
	
	// Constructor for fixed order promotion
	public FixedProductPromotion()
	{
		minimumPrice = 2000.00;
		discount = 20.00;
		todaysDate = 18;
		productIdOnPromotion = 555;
	}
	
	/**
	 * To return minimum price or bill of shopping cart for promotion code to be applicable
	 */
	@Override
	public double getMinimumPrice() {
		return minimumPrice;
	}

	/**
	 * 	To set minimum price for application of promotion code
	 */
	@Override
	public void setMinimumPrice(double price) {
		minimumPrice = price;
	}

	/**
	 * To get applicable discount on purchase over minimum required price
	 */
	@Override
	public double getFixedDiscount() {
		return discount;
	}

	/**
	 * To set/update discount allowed on shopping above specified minimum price
	 */
	@Override
	public void setFixedDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * To check whether promotion is applicable on the given product
	 */
	@Override
	public boolean isPromotionApplicable(int productId) {
	
		boolean result;
		
		if ( productId == productIdOnPromotion )
			result = true;
		else 
			result = false;
		
		return result;
		
	}
	
	/**
	 * To set id of the product in promotion
	 */
	public void setProductInPromotion(int id)
	{
		productIdOnPromotion = id;
	}
	
	/**
	 * To get id of product on which promotion discount is available
	 */
	public int getProductInPromotion()
	{
		return productIdOnPromotion;
	}
	
	/**
	 * To set id of the product in promotion
	 */
	public void setCurrentDate(int date)
	{
		productIdOnPromotion = date;
	}
	
	/**
	 * To get id of product on which promotion discount is available
	 */
	public int getCurrentDate()
	{
		return todaysDate;
	}

}
