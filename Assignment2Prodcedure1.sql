-- --------------------------------------------------------------------------------
-- Stored procedure to retrieve average sales of each product in a month.
-- Month and year will be input parameter to function.
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetMonthlyAverageSales`(month INT, year YEAR)
BEGIN

    SELECT 
        SUM(pi.quantity_ordered * p.product_price) / DAY(LAST_DAY(o.order_date)) AS monthly_avg_sales, product_name 
    FROM 
        ProductsInline as pi
    LEFT JOIN 
        Product AS p ON pi.product_id = p.product_id
    LEFT JOIN 
        Orders AS o ON pi.order_id = o.order_id
    WHERE 
        MONTH(o.order_date) = month
    AND 
        YEAR(o.order_date) = year
    GROUP BY pi.product_id;

END
