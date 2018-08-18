# Display Shopper’s information along with number of orders he/she placed during last 30 days.

SELECT 
    u.user_id,
    u.first_name,
    u.last_name,
    COUNT(o.user_id) AS number_of_orders_placed
FROM
    USER AS u
        LEFT JOIN
    Orders AS o ON u.user_id = o.user_id
WHERE
    o.order_date > DATE_SUB(CURDATE(), INTERVAL 30 DAY)
GROUP BY o.user_id;

# To display the top 10 Shoppers who generated maximum number of revenue in last 30 days.

SELECT 
    u.user_id,
    u.first_name,
    u.last_name,
    SUM(o.total_bill) AS Total_Revenue
FROM
    User AS u
        LEFT JOIN
    Orders AS o ON u.user_id = o.user_id
WHERE
    o.order_date > DATE_SUB(CURDATE(), INTERVAL 30 DAY)
GROUP BY u.user_id
ORDER BY o.total_bill DESC
LIMIT 2;

# To display top 20 Products which are ordered most in last 60 days along with numbers.

SELECT 
    p.product_id,
    p.product_name,
    SUM(pi.quantity_ordered) AS total_ordered
FROM
    Product AS p
        LEFT JOIN
    ProductsInline AS pi ON p.product_id = pi.product_id
        LEFT JOIN
    Orders AS o ON pi.order_id = o.order_id
WHERE
    o.order_date > DATE_SUB(CURDATE(), INTERVAL 60 DAY)
GROUP BY pi.product_id
ORDER BY pi.quantity_ordered DESC
LIMIT 20;

# To display monthly sales revenue of the StoreFront for last 6 months. It should display each month’s sale.

SELECT 
    MONTH(o.order_date) AS month,
    SUM(o.total_bill) AS monthly_sales_revenue
FROM
    Orders AS o
WHERE
    o.order_date > DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
GROUP BY month
ORDER BY month DESC;

# To mark the products as Inactive which are not ordered in last 90 days. 

UPDATE ProductsInline AS pi 
LEFT JOIN 
    Orders AS o ON pi.order_id = o.order_id
SET 
    pi.product_status = 'Inactive' 
WHERE
    o.order_status NOT IN ('ordered', 'Shipped', 'delivered')
AND 
    o.order_date < DATE_SUB(CURDATE(), INTERVAL 90 DAY) ;

# Given a category search keyword, to display all the Products present in this category/categories.

SELECT 
    p.product_id, p.product_name, c.category_id, c.category_name
FROM
    Product AS p
        LEFT JOIN
    ProductCategory AS pc ON p.product_id = pc.product_id
        LEFT JOIN
    Category AS c ON pc.category_id = c.category_id
WHERE
    c.category_id IN (SELECT 
        category_id
    FROM
        Category
    WHERE
        category_name IN ('Formal'));

# To display top 10 Items which were cancelled most.

SELECT 
    p.product_name,
    COUNT(pi.product_status) AS count_of_cancellation
FROM
    Product AS p
        LEFT JOIN
    ProductsInline AS pi ON p.product_id = pi.product_id
WHERE
    pi.product_status = 'cancelled'
GROUP BY p.product_id
ORDER BY count_of_cancellation DESC
LIMIT 10;