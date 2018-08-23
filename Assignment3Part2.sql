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

# To display Count of products as per given price range.

SELECT 
    pr.priceRange, COUNT(*) AS ProductCount
FROM
    (SELECT 
        CASE
                WHEN product_price > 0 AND product_price <= 100 THEN '0-100'
                WHEN product_price > 100 AND product_price <= 500 THEN '100-500'
                ELSE 'Above 500'
            END AS priceRange
    FROM
        Product) AS pr
GROUP BY pr.priceRange;

# To display categories along with number of products under each category

SELECT 
    c.category_name, COUNT(*) AS NumberOfProducts
FROM 
    Category AS c
LEFT JOIN 
    ProductCategory AS pc ON c.category_id = pc.category_id 
GROUP BY pc.category_id;
