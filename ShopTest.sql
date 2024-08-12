create database SHOP_TEST

use SHOP_TEST

CREATE TABLE Users (
    id_user BIGINT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(20) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(50) NOT NULL,
    created_at Datetime,
    updated_at DATETIME
);
drop table Users
--CREATE TABLE Customers (
--    id INT PRIMARY KEY IDENTITY(1,1),
--    user_id BIGINT FOREIGN KEY REFERENCES Users(id_user),
--    address NVARCHAR(255),
--    phone_number NVARCHAR(20),
--    -- Các trường thông tin khác của khách hàng
--);

CREATE TABLE Roles (
    id_roles BIGINT PRIMARY KEY IDENTITY(1,1),
    roles_name NVARCHAR(20) UNIQUE NOT NULL,
    descriptions NVARCHAR(100)
);

CREATE TABLE User_Roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES Users(id_user),
    FOREIGN KEY (role_id) REFERENCES Roles(id_roles)
);
