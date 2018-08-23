# Tables for a form where providing a Zip Code populates associated City and
# State. 
# To create appropriate tables and relationships for the same and write a SQL
# query for that returns a Resultset containing Zip Code, City Names and
# states ordered by State Name and City Name.

CREATE TABLE ZipcodeCity
(
    zipcode INT PRIMARY KEY,
    city VARCHAR(50) NOT NULL
);

CREATE TABLE CityState
(
    city VARCHAR(50) PRIMARY KEY,
    state VARCHAR(50) NOT NULL
);

# Insert values

INSERT INTO ZipcodeCity VALUES
(302019, 'Jaipur'),
(302020, 'Jaipur'), 
(313001, 'Udaipur'),
(560076, 'Bangalore'),
(400011, 'Mumbai');

INSERT INTO CityState VALUES
('Jaipur', 'Rajasthan'),
('Udaipur', 'Rajasthan'), 
('Bangalore', 'Karnataka'), 
('Mumbai', 'Maharashtra');

SELECT * FROM ZipcodeCity;
SELECT * FROM CityState;


# Create view to obtain the required ResultSet

CREATE VIEW ZipcodeCityCountry AS
SELECT z.zipcode, z.city, c.state FROM zipcodecity AS z
INNER JOIN citystate AS c ON z.city = c.city;

# Obtain data from ResultSet Sorted By State And then By City

SELECT zipcode, city, state FROM ZipcodeCityCountry ORDER BY state, city;