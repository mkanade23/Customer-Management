# Customer Management


## Project Details
This project provides functionality to perform CRUD operations on Customers. 
Additionally, it supports features like searching a certain customer by given fields, sorting etc.
1. The landing page is the login screen, where user can enter details to log in. 
1. On the dashboard, you will see the list of customers added to the database.
2. [Add Customer] button -> with this, you can add customers to the database.
3. Search -> You can search customers with any filter by choosing the field from the dropdown and adding the filter criteria.
4. Edit -> Using edit button from a specific row, you can update the corresponding details  of the customer.
5. Remove -> This deletes the particular customer from the database.
6. Sync Customers -> This functionality calls a remote API to fetch the list of customers and upserts them in the database.
7. Sorting -> You can click on any column name to sort the data in ascending/descending order according to that field.

## Technologies used:
* SpringBoot
* Java
* MySQL
* JSP
* Bootstrap


## Getting Started with Local Development

#### 1. Prerequisites
* [Amazon Corretto Java 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/what-is-corretto-11.html)

* [Maven 3.6.3 OR later](https://maven.apache.org/download.cgi)

* [Mysql 8.0.27 or later](https://dev.mysql.com/downloads/mysql/)


#### 2. Configure the Passwords
Open [application.properties]([application.properties](src%2Fmain%2Fresources%2Fapplication.properties)) file and update the below fields with your credentials.
1. spring.datasource.username - Your database username
2. spring.datasource.password - Your database password
3. app.config.http-client-user-name - Username to fetch the bearer token for remote calls
4. app.config.http-client-user-password - Password to fetch the bearer token for remote calls
5. Update the database name and mySQL port (if not 3306) in spring.datasource.url

#### 3. Create tables
Create below tables in your database.
1. User table: This table stores the user details for login/signup

`CREATE TABLE user (
id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
email VARCHAR(255), -- Optional, if you want to collect email
first_name VARCHAR(255), -- Optional, if you want to collect first name
last_name VARCHAR(255)
);`

2. Customer table: Customer table  stores the customer related information.

`CREATE TABLE customer (
customer_id CHAR(36) PRIMARY KEY,
first_name VARCHAR(255),
last_name VARCHAR(255),
street VARCHAR(255),
address VARCHAR(255),
city VARCHAR(255),
state VARCHAR(255),
email VARCHAR(255),
phone VARCHAR(20)
);`

#### 4. From Terminal line run this command:
    mvn clean spring-boot:run

#### 5. Visit http://localhost:8082/ and continue with signup.






