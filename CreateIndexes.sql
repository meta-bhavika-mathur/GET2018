# To create and use indexes in tables of storefront database

ALTER TABLE Orders ADD INDEX OrderTableIndex(order_id);
ALTER TABLE Product ADD INDEX ProductTableIndex(product_id);
ALTER TABLE Category ADD INDEX CategoryTableIndex(category_id);

SHOW INDEX FROM Orders;

SHOW INDEX FROM Product;

SHOW INDEX FROM Category;