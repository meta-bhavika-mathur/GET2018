SELECT  DISTINCT GetMonthlyOrders(8,2018) FROM Orders;
 
 SELECT MonthWithMaximumOrders(2018) FROM Orders;

CALL averageSales(8, 2018);
 
CALL FindOrdersBetweenGivenDates('2018-07-16', '2018-08-18');