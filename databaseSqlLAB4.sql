CREATE DATABASE IF NOT EXISTS CarAdsDB;

USE CarAdsDB;

CREATE TABLE IF NOT EXISTS CarOwners(
    ownerID INT AUTO_INCREMENT PRIMARY KEY,
    ownerName VARCHAR(50),
    ownerNationality VARCHAR(50),
    ownerPhoneNumber INT,
    ownerPersonalEmail VARCHAR(50)
);

INSERT INTO CarOwners(ownerName, ownerNationality, ownerPhoneNumber, ownerPersonalEmail)
VALUES ('Warren Buffet','US',0123456789,"wolfofwallstreet@trashmail.com");

CREATE TABLE IF NOT EXISTS CarAds(
    carAdID INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50),
    manufacturer VARCHAR(50),
    price INT,
    quality VARCHAR(50),
    yearOfMake INT,
    ownerID INT
);

INSERT INTO CarAds(model, manufacturer, price, quality, yearOfMake, ownerID) VALUES ('model1', 'Dacia', 1234, 'decent', 1998, 1);
INSERT INTO CarAds(model, manufacturer, price, quality, yearOfMake, ownerID) VALUES ('model2', 'Ferrari', 12345, 'great', 2011, 1);
INSERT INTO CarAds(model, manufacturer, price, quality, yearOfMake, ownerID) VALUES ('model3', 'Lamborghini', 9999, 'excellent', 2019, 1);