USE StoreFront;

# Insert data into User table

INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('bhavikam08@gmail.com', '1234', 'Bhavika', 'Mathur', 'Administrator');
                 
INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('Rachna@gmail.com', '124', 'Rachna', 'Jadam', 'Shopper');
                 
INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('xyz@gmail.com', '1234567', 'XYZ', 'ABC', 'Shopper');
    
INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('peter@gmail.com', '1234567', 'Peter', 'Nelson', 'Shopper');

INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('neha@gmail.com', '123456787', 'Neha', 'Sharma', 'Shopper');
    
INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('shreyas@gmail.com', '1237', 'Shreyas', 'Saxena', 'Shopper');
                 

# Insert data into Address table

INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (1, '64-A', 'Lotus Pond Street', 'School', 'Ahemdabad', 'Gujarat', 'India', 622020);
                 
INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (2, '74/20', 'Shipra Path', 'School', 'Jaipur', 'Rajasthan', 'India', 302020);
                 
INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (3, '74/20', 'Shipra Path', 'School', 'Jaipur', 'Rajasthan', 'India', 302020);

INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (4, '80', 'Green Lane', 'Public Park', 'Bangalore', 'Karnataka', 'India', 701020);

INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (5, '123-D', 'Raja Park', 'Restuarant', 'Jaipur', 'Rajasthan', 'India', 301020);
                 
INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (6, '100-A', 'Gaytri Nagar', 'Flower Shop', 'Jaipur', 'Rajasthan', 'India', 701020);
                 
# Insert data into Category table
                 
INSERT INTO Category ( parent_category_id, category_name)  VALUES 
                 ( null, 'Electronics');

INSERT INTO Category ( parent_category_id, category_name)  VALUES 
                 (1, 'Mobile Phones');

INSERT INTO Category ( parent_category_id, category_name)  VALUES 
                 (1, 'Television');
                 
INSERT INTO Category ( parent_category_id, category_name)  VALUES 
                 ( null, 'Clothing');

INSERT INTO Category (parent_category_id, category_name)  VALUES 
                 (4,'Shirts');

INSERT INTO Category (parent_category_id, category_name)  VALUES 
                 ( 4, 'Trousers');
                
INSERT INTO Category (parent_category_id, category_name)  VALUES 
                 ( 5, 'Formal');
    
INSERT INTO Category (parent_category_id, category_name)  VALUES 
                 ( 5, 'Casual');
                 
INSERT INTO Category ( parent_category_id, category_name)  VALUES 
                 ( null, 'Home Furnishing');

INSERT INTO Category ( parent_category_id, category_name)  VALUES 
                 (9, 'Furniture');
                 
INSERT INTO Category ( parent_category_id, category_name)  VALUES 
                 (9, 'Bedsheets');
                 
# Insert data into Product table

INSERT INTO Product(product_name, product_price, product_description, available_stock) VALUES
                ('Moto G', 15000, 'Mobile by motorola', 5);
                 
INSERT INTO Product(product_name, product_price, product_description, available_stock) VALUES
                ('Panasonic LED', 45000, 'PANASONIC LED', 2);

INSERT INTO Product(product_name, product_price, product_description, available_stock) VALUES
                ('Peter England Shirt', 40000, 'Shirt for both casual and formal occasions', 10);
                
# Insert data into Orders table             
    
INSERT INTO Orders(user_id, order_date, order_time, order_status, total_bill, total_products)  VALUES
                (2, '2018-08-15', '10:15:00' , 'active', 60000.00, 2);
                
INSERT INTO Orders(user_id, order_date, order_time, order_status, total_bill, total_products)  VALUES
                (3, '2018-08-16', '10:15:00' , 'active', 45000.00, 1);
            
INSERT INTO Orders(user_id, order_date, order_time, order_status, total_bill, total_products)  VALUES
                (6, '2018-08-18', '08:15:00' , 'active', 65000.00, 6);  
                
INSERT INTO Orders(user_id, order_date, order_time, order_status, total_bill, total_products)  VALUES
                (6, '2018-08-17', '06:15:00' , 'active', 16000.00, 6);   
                
                
# Insert data into Phone Number table

INSERT INTO PhoneNumber(user_id, phone_number)  VALUES
                (3, '215645');
                 
INSERT INTO PhoneNumber(user_id, phone_number)  VALUES
                (2, '8245645');

# Insert data into Image table

INSERT INTO Image (product_id, image_link, alternative_text)  VALUES 
                 (1, 'C:\Users\Bhavika\Pictures\motoG5SPlus.jpg', 'Image Not Avaiable');
                 
INSERT INTO Image (product_id, image_link, alternative_text)  VALUES 
                 (2, 'C:\Users\Bhavika\Pictures\panasonicLED.jpg', 'Image Not Avaiable');
                 
# Insert data into ProductCategory table
    
INSERT INTO ProductCategory(product_id, category_id) VALUES
                    (1,2);
                 
INSERT INTO ProductCategory(product_id, category_id) VALUES
                    (2,3);
                    
# Insert data into ProductsInline table
                    
INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (1, 1, 1, 15000, 'active');

INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (1, 2, 1, 45000, 'active');
   
INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (2, 2, 1, 45000, 'active');  
                
INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (3, 3, 5, 20000, 'active');     
                
INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (3, 2, 1, 45000, 'active'); 

INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (4, 3, 4, 16000, 'active'); 

                    
# Insert data into ProductCategory table
                    
INSERT INTO ProductCategory(product_id, category_id) VALUES
                    (3,7);
        
INSERT INTO ProductCategory(product_id, category_id) VALUES
                    (3,8);