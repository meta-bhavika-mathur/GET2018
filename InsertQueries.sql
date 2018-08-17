USE StoreFront;

INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('bhavikam08@gmail.com', '1234', 'Bhavika', 'Mathur', 'Administrator');
                 
INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('Rachna@gmail.com', '124', 'Rachna', 'Jadam', 'Shopper');
                 
INSERT INTO User (email_id, password, first_name, last_name, user_type) values 
                 ('xyz@gmail.com', '1234567', 'XYZ', 'ABC', 'Shopper');


INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (1, '64-A', 'Lotus Pond Street', 'School', 'Ahemdabad', 'Gujarat', 'India', 622020);
                 
INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (2, '74/20', 'Shipra Path', 'School', 'Jaipur', 'Rajasthan', 'India', 302020);
                 
INSERT INTO Address (user_id, house_number, street, landmark, city, state, country, pincode)  VALUES 
                 (3, '74/20', 'Shipra Path', 'School', 'Jaipur', 'Rajasthan', 'India', 302020);

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

INSERT INTO Product(product_name, product_price, product_description, available_stock) VALUES
                ('Moto G', 15000, 'Mobile by motorola', 5);
                 
INSERT INTO Product(product_name, product_price, product_description, available_stock) VALUES
                ('Panasonic LED', 45000, 'PANASONIC LED', 2);
    
INSERT INTO Orders(user_id, order_date, order_time, order_status, total_bill, total_products)  VALUES
                (2, '2018-08-15', '10:15:00' , 'active', 60000.00, 2);
                
INSERT INTO Orders(user_id, order_date, order_time, order_status, total_bill, total_products)  VALUES
                (3, '2018-08-16', '10:15:00' , 'active', 45000.00, 1);
                
INSERT INTO PhoneNumber(user_id, phone_number)  VALUES
                (3, '215645');
                 
INSERT INTO PhoneNumber(user_id, phone_number)  VALUES
                (2, '8245645');

INSERT INTO Image (product_id, image_link, alternative_text)  VALUES 
                 (1, 'C:\Users\Bhavika\Pictures\motoG5SPlus.jpg', 'Image Not Avaiable');
                 
INSERT INTO Image (product_id, image_link, alternative_text)  VALUES 
                 (2, 'C:\Users\Bhavika\Pictures\panasonicLED.jpg', 'Image Not Avaiable');
    
INSERT INTO ProductCategory(product_id, category_id) VALUES
                    (1,2);
                 
INSERT INTO ProductCategory(product_id, category_id) VALUES
                    (2,3);
                    
INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (1, 1, 1, 15000, 'active');

INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (1, 2, 1, 45000, 'active');
   
INSERT INTO ProductsInline( order_id, product_id, quantity_ordered, product_total, product_status) VALUES
                (2, 2, 1, 45000, 'active');             
                
                
                
                