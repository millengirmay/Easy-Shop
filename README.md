# 🛒 EasyShop E-Commerce API – Capstone Project

## 📚 Project Overview

**EasyShop** is an online e-commerce application designed to provide customers with a seamless online shopping experience. This project represents **Version 2** development for EasyShop, where enhancements, bug fixes, and new features were implemented to improve system functionality.

### 💼 Capstone Context

This project was developed as part of the **Year Up Advanced Java Capstone**, assuming the role of a **Backend Developer** for an existing operational website. The focus was on enhancing an existing Spring Boot REST API backed by a MySQL database.

---

## 🎯 Project Goals

✅ **Fix existing bugs** in the Version 1 API  
✅ **Develop new features** to expand application capabilities  
✅ **Write unit tests** and perform manual debugging to ensure stability  
✅ **Test endpoints** using Postman collections  
✅ **Demonstrate backend integration** with the provided frontend website

---

## 🔧 Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Security with JWT**
- **MySQL**
- **Maven**
- **Postman** for API testing
- **Git & GitHub Projects** for source control and agile task management

---

## 🚀 Key Features Developed

### 🐛 Bug Fixes

- Resolved issues in existing product and category retrieval logic
- Fixed shopping cart update and delete bugs for consistent cart state
- Corrected authentication and token generation errors

### 🆕 New Features

1. **Authentication & Registration**

   - Implemented secure login with JWT token generation
   - User registration with automatic profile creation

2. **Products Management**

   - CRUD operations for products
   - Search functionality with filters: categoryId, minPrice, maxPrice, color

3. **Categories Management**

   - CRUD operations for categories
   - Endpoint to retrieve products by category

4. **Shopping Cart Functionality**

   - Add products to cart
   - Update product quantities in cart
   - Remove individual products
   - Clear entire cart
   - View current cart

5. **Order Checkout**

   - Convert shopping cart into orders
   - Create order line items and persist order data with current date and pending status

🔑 API Endpoints Summary
Authentication
Method	Endpoint	Description
POST	/login	Authenticate user and retrieve JWT token
POST	/register	Register a new user

Products
Method	Endpoint	Description
GET	/products	Retrieve all products with optional filters
GET	/products/{id}	Get product by ID
POST	/products	Add new product (Admin only)
PUT	/products/{id}	Update existing product (Admin only)
DELETE	/products/{id}	Delete product (Admin only)

Categories
Method	Endpoint	Description
GET	/categories	Get all categories
GET	/categories/{id}	Get category by ID
GET	/categories/{id}/products	Get products by category
POST	/categories	Add new category (Admin only)
PUT	/categories/{id}	Update category (Admin only)
DELETE	/categories/{id}	Delete category (Admin only)

Shopping Cart
Method	Endpoint	Description
GET	/cart	Retrieve current user's cart
POST	/cart/products/{productId}	Add product to cart
PUT	/cart/products/{productId}	Update product quantity in cart
DELETE	/cart/products/{productId}	Remove product from cart
DELETE	/cart	Clear entire cart

Orders
Method	Endpoint	Description
POST	/orders	Checkout cart and create order

