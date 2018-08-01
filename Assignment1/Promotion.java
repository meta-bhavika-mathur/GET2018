package shoppingcart;

public interface Promotion{
	
	double getMinimumPrice();
	void setMinimumPrice(double price);
	double getFixedDiscount();
	void setFixedDiscount(double discount);
	boolean isPromotionApplicable(int code);

}
