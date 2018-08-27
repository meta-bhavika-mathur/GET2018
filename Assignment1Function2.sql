-- --------------------------------------------------------------------------------
-- Function to return month in a year having maximum orders. Year is input parameter.
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `MonthWithMaximumOrders`(year YEAR) RETURNS int(11)
    DETERMINISTIC
BEGIN
    DECLARE max INT(11);
    
    SELECT month INTO max 
    FROM (
            SELECT 
                COUNT(order_id) AS count_order, MONTH(order_date) AS month
            FROM 
                Orders
            WHERE 
                YEAR(order_date) = year
            GROUP BY 
                MONTH(order_date)
         ) AS op HAVING MAX(op.count_order);

    
    RETURN (max);
END
