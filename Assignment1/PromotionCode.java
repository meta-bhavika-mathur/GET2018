package shoppingcart;

public enum PromotionCode {
	//  Enumeration to set values to set start and end date for the validity of coupon and promotion code
	startDate (16), endDate (25) , code(123);
	
	private int promoCodeData;
	
	PromotionCode(int data)
	{
		promoCodeData = data;
	}
	int getInfo()
	{
		return promoCodeData;
	}
}