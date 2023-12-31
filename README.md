# E-Commerce Console Application Documentation
![4d73bf84d64992d08adbcc7168200ccd (1)](https://github.com/Ostrobogulous/E-Commerce_Application/assets/73111142/e62b1851-9bd3-4311-b33b-b38d34b9d7d9)


## Overview
This e-commerce console application supports interactions for both Customers and Sellers with functionalities tailored to each role, focusing on Musical Products, Electronic Products, and Book Products.

### Key Features
- *Customers*: Register, log in, log out, search products, manage a shopping cart, and make payments.
- *Sellers*: Manage their inventory, including product creation, updates, and deletion.
- *Product Categories*: Musical Products, Electronic Products, Book Products.
- *Payment Methods*: PayPal and Credit Card.
- *Data Storage*: SQLite.

## Users of the System

### Customers
- *Registration and Authentication*: Customers can register for an account and log in to access the application.
- *Product Search*: Ability to search products using category, name, or brand.
- *Shopping Cart Management*: Customers can add and remove items from their shopping cart.
- *Checkout and Payment*: Proceed to checkout and make payments using PayPal or credit card.
- *Order Tracking*: View and track their orders post-purchase.

### Sellers
- *Account Management*: Sellers can register, log in, and manage their profiles.
- *Inventory Management*: Ability to create, update, and delete products from their inventory.
- *Sales Monitoring*: Track the sales and performance of their products.
- *Product Management*: Manage details and specifications of their product offerings.

## System Components
### User
User is the base class for both customers and sellers. It stores user-related information like name, email, and password.

### Customer
Customer inherits from User. It manages customer-specific actions like shopping cart management and order checkout.

### Seller
Seller, also derived from User, handles seller-specific functionalities such as inventory management.

### Product
Product is a superclass for various types of products, holding common attributes like name, price, and stock level.

### BookProduct, ElectronicProduct, MusicalProduct
These classes inherit from Product, each adding specific attributes related to their category, like author for books or brand for electronics.

### ShoppingCart
ShoppingCart manages the products a customer intends to purchase, including adding, removing, and processing payment for items.

### Order
Order handles the details of customer orders, including order retrieval and saving order information.

### PaymentStrategy
An interface for different payment methods. Implemented by CreditCardPayment and PayPalPayment to process payments.

### ProductManagement
Central class for managing product-related operations, such as listing, searching, and filtering products.

### UserManagement
Handles user-related functionalities, including login, logout, user creation, and shopping cart management.

## Diagram
<img width="1920" alt="final diagram" src="https://github.com/Ostrobogulous/E-Commerce_Console_Application/assets/73111142/06e7a374-553f-4e0f-8765-ce9ec32dba6c">

---

## Forms and User Input
Each type of input (e.g., login, product details) is handled by specific form classes like LoginForm, ProductForm, ensuring organized and secure data collection.

## Data Storage and Security
- *SQLite*: The application uses SQLite for storing and managing data efficiently.
- *Password Security*: Passwords are securely hashed using org.mindrot.jbcrypt.BCrypt, ensuring that user credentials are safely stored.

## Interactions and Relationships
- *Customer and ShoppingCart*: Customers interact with ShoppingCart to manage their potential purchases.
- *ECommerce and ProductManagement*: ECommerce uses ProductManagement for handling product-related functionalities.
- *ECommerce and UserManagement*: Manages user operations within the application.
- *ProductItem and Product*: ProductItem links to Product to represent individual items in the shopping cart.

## Future Enhancements
- *Admin Role*: Implement administrative functionalities for overseeing the application.
- *Product Reviews*: Add review features for products.
- *Shipping Options*: Incorporate various shipping methods for orders.

## Application Entry Point
- The ECommerce class serves as the main entry point, coordinating various components and user interactions.

## Database Initialization and Setup
- The database is initialized using DatabaseUtils located in the db package, which also contains the SQL schema.

---
