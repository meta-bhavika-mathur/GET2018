# To display Recent 50 Orders placed (Id, Order Date, Order Total).

SELECT 
    order_id, order_date, total_bill
FROM
    Orders
ORDER BY order_date DESC ,order_time DESC
LIMIT 50;


# To display 10 most expensive Orders.

SELECT 
    o.order_date, o.order_id, o.total_bill
FROM
    Orders AS o
ORDER BY 
    o.total_bill DESC LIMIT 10 ;

# To display all the Orders which are placed more than 10 days old and one or more items 
# from those orders are still not shipped.

SELECT 
    pi.order_id,
    pi.product_id,
    p.product_name,
    pi.product_status
FROM
    Orders AS o
        LEFT JOIN
    ProductsInline AS pi ON o.order_id = pi.order_id
        LEFT JOIN
    Product AS p ON pi.product_id = p.product_id
WHERE
    o.order_date < DATE_SUB(CURDATE(), INTERVAL 10 DAY) AND pi.product_status NOT IN ('Shipped', 'Delivered');


# To display list of shoppers which haven't ordered anything since last month.

SELECT 
    s.user_id, s.first_name, s.last_name
FROM
    User AS s
        LEFT JOIN
    Orders AS o ON s.user_id = o.user_id
WHERE
    o.order_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH);


# To display list of shoppers along with orders placed by them in last 15 days. 

SELECT 
    s.user_id,
    s.first_name,
    s.last_name,
    pi.order_id,
    pi.product_id,
    p.product_name
FROM
    User AS s
        LEFT JOIN
    Orders AS o ON s.user_id = o.user_id
        LEFT JOIN
    ProductsInline AS pi ON o.order_id = pi.order_id
        LEFT JOIN
    Product AS p ON pi.product_id = p.product_id
WHERE
    o.order_date >= DATE_SUB(CURDATE(), INTERVAL15DAY);


# To display list of order items which are in “shipped” state for particular Order Id (i.e.: 1020))

SELECT 
    pi.order_id,
    pi.product_id,
    p.product_name,
    pi.quantity_ordered,
    pi.product_total,
    pi.product_status
FROM
    ProductsInline AS pi
        LEFT JOIN
    Product AS p ON pi.product_id = p.product_id
WHERE
    pi.order_id = 2 AND pi.product_status = 'Shipped';


#To display list of order items along with order placed date which fall between Rs 20 to Rs 50 price.

SELECT 
    o.order_id,
    o.order_date,
    p.product_total,
    pro.product_name,
    pro.product_id
FROM
    Orders AS o
        LEFT JOIN
    ProductsInline AS p ON o.order_id = p.order_id
        LEFT JOIN
    Product AS pro ON p.product_id = pro.product_id
WHERE
    p.product_total BETWEEN 20 AND 50; 
 

#To update first 20 Order items status to “Shipped” which are placed today.

UPDATE 
    ProductsInline AS pi 
LEFT JOIN
    Orders AS o 
ON 
    pi.order_id = o.order_id
SET 
    pi.product_status = 'Shipped'
WHERE 
    pi.order_id IN (SELECT o.order_id FROM Orders AS o WHERE o.order_date = CURDATE() ORDER BY o.order_time)
LIMIT 20;
