# To display the list of products (Id, Title, Count of Categories) which fall in more than one Categories.

SELECT 
    p.product_id,
    p.product_name,
    COUNT(pc.product_id) AS count_of_categories
FROM
    Product AS p
        LEFT JOIN
    ProductCategory AS pc ON p.product_id = pc.product_id
GROUP BY 
    pc.product_id
HAVING 
    COUNT(pc.product_id) > 1;

# To display Count of products as per given price range..

SELECT 
    p.product_price AS Price_in_Rupees,
    COUNT(*) AS Product_Count
FROM
    (SELECT 
        CASE
                WHEN p.product_price >= 0 AND p.product_price <= 100 THEN '0-100'
                WHEN p.product_price > 100 AND p.product_price <= 500 THEN '100-500'
                ELSE 'Above 500'
            END AS product_price
    FROM
        product) pr
GROUP BY p.product_price;
