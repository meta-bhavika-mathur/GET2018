CREATE DATABASE StoreFront;
USE StoreFront;

 CREATE TABLE User         (user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
                            email_id VARCHAR(100) NOT NULL UNIQUE, 
                            password VARCHAR(15) NOT NULL, 
                            first_name VARCHAR(100) NOT NULL,
                            last_name VARCHAR(100),
                            user_type VARCHAR(15) CHECK (user_type = 'Administrator' OR user_type = 'Shopper') );
                    
CREATE TABLE PhoneNumber  ( user_id INT NOT NULL,
                            phone_number INT NOT NULL,
                            FOREIGN KEY(user_id) REFERENCES User(user_id) ON 
                            UPDATE CASCADE ON DELETE RESTRICT);
                    
CREATE TABLE Address      ( address_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
                            user_id INT NOT NULL,
                            house_number VARCHAR(20), 
                            street VARCHAR(50),
                            landmark VARCHAR(100), 
                            city VARCHAR(50), 
                            state VARCHAR(100), 
                            country VARCHAR(100),
                            pincode INT,
                            FOREIGN KEY(user_id) REFERENCES User(user_id) ON 
                            UPDATE CASCADE ON DELETE RESTRICT);
                       
CREATE TABLE Orders      (  order_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            user_id INT NOT NULL,
                            order_date DATE NOT NULL,
                            order_time TIME NOT NULL, 
                            order_status VARCHAR(50) NOT NULL, 
                            total_bill DOUBLE,
                            total_products INT,
                            FOREIGN KEY (user_id)
                            REFERENCES User(user_id) ON UPDATE CASCADE 
                            ON DELETE RESTRICT);
                            
 CREATE TABLE Product    (  product_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            product_name VARCHAR(50),
                            product_price DOUBLE,
                            product_description TEXT(255),
                            available_stock INT);
                            
 CREATE TABLE ProductsInline (   order_id INT NOT NULL,
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
                     
CREATE TABLE Category   (   category_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            parent_category_id INT,
                            category_name VARCHAR(50),
                            FOREIGN KEY(parent_category_id) REFERENCES Category(category_id) ON 
                            UPDATE CASCADE ON DELETE RESTRICT);
                            
 CREATE TABLE ProductCategory ( product_id INT NOT NULL,
                                category_id INT NOT NULL,
                                FOREIGN KEY(product_id) REFERENCES Product(product_id) ON 
                                UPDATE CASCADE ON DELETE RESTRICT,
                                FOREIGN KEY(category_id) REFERENCES Category(category_id) ON 
                                UPDATE CASCADE ON DELETE RESTRICT);
                       
CREATE TABLE Image  (   image_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        product_id INT NOT NULL,
                        image_link VARCHAR(100), 
                        alternative_text VARCHAR(50),
                        FOREIGN KEY(product_id) REFERENCES Product(product_id) ON 
                        UPDATE CASCADE ON DELETE RESTRICT);
