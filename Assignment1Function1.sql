-- --------------------------------------------------------------------------------
-- Function to calculate number of orders in a month. 
-- Month and year will be input parameter to function.
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER =`root`@`localhost` FUNCTION `GetMonthlyOrders`(month INT, year YEAR) RETURNS double
    DETERMINISTIC
BEGIN
    DECLARE monthlyOrders DOUBLE;
    
    SELECT 
        COUNT(o.order_id) INTO monthlyOrders FROM Orders AS o 
    WHERE 
        MONTH(o.order_date) = month AND YEAR(o.order_date) = year;
    RETURN (monthlyOrders);
END