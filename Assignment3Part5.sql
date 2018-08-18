# To create a view displaying the order information (Id, Title, Price, Shopper’s name, Email, Orderdate, Status)
# with latest ordered items should be displayed first for last 60 days.

CREATE VIEW OrderInformation AS
SELECT 
    o.order_id AS Id,
    p.product_name AS Title,
    p.product_price AS Price,
    u.first_name AS Shopper_Name,
    u.email_id AS Email,
    o.order_date AS Order_Date,
    o.order_status AS Status
FROM
    User AS u
LEFT JOIN 
    Orders AS o ON u.user_id = o.user_id
LEFT JOIN 
    ProductsInline AS pi ON o.order_id = pi.order_id
LEFT JOIN 
    Product AS p ON pi.product_id = p.product_id 
WHERE 
    o.order_date > DATE_SUB(CURDATE(), INTERVAL 60 DAY)
ORDER BY 
    order_date DESC;

SELECT * FROM OrderInformation;

# Use the above view to display the Products(Items) which are in ‘shipped’ state.

SELECT DISTINCT
    Title, Status
FROM
    OrderInformation
WHERE
    Status = 'Shipped';

# To use the above view to display the top 5 most selling products.

SELECT 
    Title, COUNT(Title) AS quantity_sold
FROM
    OrderInformation
GROUP BY 
    Title
ORDER BY 
    Quantity_Sold DESC
LIMIT 5;