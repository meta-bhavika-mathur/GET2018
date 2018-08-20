-- --------------------------------------------------------------------------------
-- Function to return month in a year having maximum orders. Year is input parameter.
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER =`root`@`localhost` FUNCTION `MonthWithMaximumOrders`(year YEAR) RETURNS int(11)
    DETERMINISTIC
BEGIN
    DECLARE max INT;

    SELECT month INTO max 
    FROM
        (
            SELECT 
            COUNT(order_id) AS count_order, MONTH(order_date) AS month
            FROM orderproduct
            WHERE YEAR(order_date) = year
            GROUP BY MONTH(order_date)
        ) op
    HAVING MAX(op.count_order);

RETURN (max);
END