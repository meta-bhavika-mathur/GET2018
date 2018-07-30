package shoppingcart;

public class FixedOrderPromotion implements Promotion {

	double minimumPrice;
	double discount;
	int todaysDate;
	
	// Constructor Fixed Order Promotion
	public FixedOrderPromotion()
	{
		minimumPrice = 2000.00;
		discount = 20.00;
		todaysDate = 18;	
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
	public boolean isPromotionApplicable(int code)
	{
		
		boolean result;
		
		// To check validity of promotion code
		if (( PromotionCode.code.getInfo() == code ) 
				&& ( todaysDate >= PromotionCode.startDate.getInfo() 
				&& todaysDate <= PromotionCode.endDate.getInfo()))
		{
			
			result = true;
		}
		else 
			result = false;
		
		return result;
	}

}