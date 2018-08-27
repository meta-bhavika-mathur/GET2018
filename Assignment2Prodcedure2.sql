-- --------------------------------------------------------------------------------
-- Stored procedure to retrieve table having order detail with status for a given period. 
-- Start date and end date will be input parameter. 
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `FindOrdersBetweenGivenDates`(start_date DATE, end_date DATE)
BEGIN

    SET start_date = validateDate(start_date, end_date);

    SELECT 
        order_id, user_id, order_date, total_products, order_status
    FROM 
        Orders 
    WHERE 
        order_date BETWEEN start_date AND end_date;

END