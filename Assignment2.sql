# To display Id, Title, Category Title, Price of the products which are Active and recently added products should be at top.

SELECT 
    Product.product_id AS pid,
    Product.product_name,
    Product.product_price
FROM
    Product,
    ProductsInline
WHERE
    Product.product_id = ProductsInline.product_id AND ProductsInline.product_status = 'active'
ORDER BY (SELECT 
    order_date
FROM
    Orders
WHERE
    order_id = pid) DESC,(SELECT 
    order_time
FROM
    Orders
WHERE
    order_id = pid) DESC;

# To display the list of products which don't have any images.

SELECT 
    Product.product_id, Product.product_name
from
    Product
WHERE
    Product.product_id IN (SELECT 
        product_id
    FROM
        Image
    WHERE
        image_link IS NOT NULL);

# To display all Id, Title and Parent Category Title for all the Categories listed, sorted by Parent Category Title and 
# then Category Title. (If Category is top category then Parent Category Title column should display “Top Category” as value.)

SELECT 
    c.category_id,
    c.category_name,
    IFNULL(pc.category_name, 'Top Category') AS parent_category_name
FROM
    Category AS c
        LEFT JOIN
    Category AS pc ON c.parent_category_id = pc.category_id ORDER BY parent_category_name, c.category_name;


# Display Id, Title, Parent Category Title of all the leaf Categories (categories which are not parent of any other category)

SELECT 
    Category.category_id, Category.category_name
FROM
    Category AS c
WHERE
    c.category_id NOT IN (SELECT 
        parent_category_id
    FROM
        Category
    WHERE
        parent_category_id IS NOT NULL);
         
# To display Product Title, Price & Description which falls into particular category Title (i.e. “Mobile”) 

SELECT 
    Product.product_name,
    Product.product_price,
    Product.product_description
FROM
    Product
WHERE
    Product.product_id IN (SELECT 
        ProductCategory.product_id
    FROM
        ProductCategory
    WHERE
        category_id = (SELECT 
            Category.category_id
        FROM
            Category
        WHERE
            category_name = 'Mobile Phones'));

# To display the list of Products whose Quantity on hand (Inventory) is under 50.

SELECT 
    *
FROM
    PRODUCT
WHERE
    available_stock < 50;

# To increase the inventory of all the products by 100.

UPDATE Product SET available_stock = available_stock + 100;

