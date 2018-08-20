-- --------------------------------------------------------------------------------
-- Validation on input dates
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `ValidateDate`(start_date Date, end_date Date) RETURNS date
BEGIN
  
    DECLARE set_start_date DATE;
  
    IF (start_date > end_date) THEN
        RETURN start_date;
    ELSE
        SET set_start_date = DATE_ADD(DATE_ADD(LAST_DAY('2017-07-14'), INTERVAL 1 DAY), INTERVAL - 1 MONTH);
        RETURN set_start_date;
    END IF;
END