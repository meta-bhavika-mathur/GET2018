CREATE DATABASE StoreFront;
USE StoreFront;
SHOW tables;
CREATE TABLE User(  user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
                    email_id VARCHAR(100) NOT NULL UNIQUE, 
                    password VARCHAR(15) NOT NULL, 
                    first_name VARCHAR(100) NOT NULL,
                    last_name VARCHAR(100),
                    user_type VARCHAR(15) CHECK(user_type IN(Administrator, Shopper)));
                    
CREATE TABLE PhoneNumber( phone_number INT ,
                          FOREIGN KEY(user_id) REFERENCES User(user_id) ON 
                          UPDATE CASCADE ON DELETE RESTRICT);
                    
CREATE TABLE Address(  address_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
                       user_id INT NOT NULL, 
                       house_number VARCHAR(20), 
                       street VARCHAR(50),
                       landmark VARCHAR(100), 
                       city VARCHAR(50), 
                       state VARCHAR(100), 
                       country VARCHAR(100),
                       pincode VARCHAR(20));
                       
CREATE TABLE ProductOrder(order_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          order_date DATE NOT NULL,
                          order_time TIME NOT NULL, 
                          order_status VARCHAR(50), 
                          total_bill DOUBLE,
                          total_products INT,
                          FOREIGN KEY user_id(cat_id)
                          REFERENCES categories(cat_id) ON UPDATE CASCADE 
                          ON DELETE RESTRICT);

CREATE TABLE Product(product_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
                     product_name VARCHAR(50),
                     product_price DOUBLE,
                     product_description TEXT(255),
                     available_stock INT,
                     FOREIGN KEY(order_id) REFERENCES ProductOrder(order_id) ON 
                     UPDATE CASCADE ON DELETE RESTRICT);
                          
                     
CREATE TABLE Category( category_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       category_name VARCHAR(50),
                       parent_category_id INT NOT NULL,
                       FOREIGN KEY(product_id) REFERENCES Product(product_id) ON 
                       UPDATE CASCADE ON DELETE RESTRICT);
                       
CREATE TABLE Image(image_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                   image_link VARCHAR(100), 
                   alternative_text VARCHAR(50),
                   FOREIGN KEY(product_id) REFERENCES Product(product_id) ON 
                   UPDATE CASCADE ON DELETE RESTRICT);
                   