USE StoreFront;

SHOW tables;
                    
CREATE TABLE Product (  product_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        product_name VARCHAR(50),
                        product_price DOUBLE,
                        product_description TEXT(255),
                        available_stock INT);


CREATE TABLE Image  (   image_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        product_id INT NOT NULL,
                        image_link VARCHAR(100), 
                        alternative_text VARCHAR(50),
                        FOREIGN KEY(product_id) REFERENCES Product(product_id) ON 
                        UPDATE CASCADE ON DELETE RESTRICT);
                        
CREATE TABLE ProductsInline (    order_id INT NOT NULL,
                                 product_id INT NOT NULL,
                                 quantity_ordered INT,
                                 product_total DOUBLE,
                                 product_status VARCHAR(20) NOT NULL,
                                 FOREIGN KEY (order_id)
                                 REFERENCES Orders(order_id) ON UPDATE CASCADE 
                                 ON DELETE RESTRICT,
                                 FOREIGN KEY (product_id)
                                 REFERENCES Product(product_id) ON UPDATE CASCADE 
                                 ON DELETE RESTRICT,
                                 PRIMARY KEY (order_id, product_id));  

CREATE TABLE ProductCategory (  product_id INT NOT NULL,
                                category_id INT NOT NULL,
                                FOREIGN KEY(product_id) REFERENCES Product(product_id) ON 
                                UPDATE CASCADE ON DELETE RESTRICT,
                                FOREIGN KEY(category_id) REFERENCES Category(category_id) ON 
                                UPDATE CASCADE ON DELETE RESTRICT);