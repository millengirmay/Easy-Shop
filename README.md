# 🛒 EasyShop E-Commerce API – Capstone Project


![image](https://github.com/user-attachments/assets/bb6e6427-b571-4551-a62b-6d289b1961ba)




## 📚 Project Overview

**EasyShop** is an online e-commerce application designed to provide customers with a seamless online shopping experience. This project represents **Version 2** development for EasyShop, where enhancements, bug fixes, and new features were implemented to improve system functionality.

### 💼 Capstone Context

This project was developed assuming the role of a **Backend Developer** for an existing operational website. The focus was on enhancing an existing Spring Boot REST API backed by a MySQL database.

---

## 🎯 Project Goals

✅ **Fix existing bugs** in the Version 1 API  
✅ **Develop new features** to expand application capabilities  
✅ **Write unit tests** and perform manual debugging to ensure stability  
✅ **Test endpoints** using Postman collections  
✅ **Demonstrate backend integration** with the provided frontend website

---

## 🔧 Technologies Used

- **Spring Boot**
- **MySQL**
- **Maven**
- **Postman** for API testing
- **Git & GitHub Projects** for source control and agile task management

---

## 🚀 Key Features Developed


##### 📝 Annotations Used and Their Purpose

Throughout this project, I extensively used **Spring annotations** to build a structured, secure, and RESTful API. Below are the key annotations added, with explanations:

| Annotation | Usage |
| ---------- | ----- |
| `@RestController` | Declares the class as a REST controller, combining `@Controller` and `@ResponseBody` to return JSON responses directly. Used in all controller classes (e.g., `ProductsController`, `OrdersController`, `ShoppingCartController`). |
| `@RequestMapping` | Sets the base URL path for the controller. For example, `/products` for product endpoints. |
| `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` | Maps specific HTTP methods to controller methods for RESTful actions. For example, `@GetMapping("/products")` retrieves all products. |
| `@CrossOrigin` | Enables Cross-Origin Resource Sharing (CORS) to allow frontend apps (running on different ports) to access the API. |
| `@Autowired` | Injects dependencies automatically. Used for DAO and service injection in controllers. |
| `@PreAuthorize` | Ensures authorization rules, like allowing only logged-in users or admins to access certain endpoints. For example, `@PreAuthorize("hasRole('ROLE_ADMIN')")` protects admin routes. |
| `@PathVariable` | Binds URL path variables to method parameters, e.g., retrieving a product by its ID. |
| `@RequestBody` | Maps the incoming JSON body to a Java object, used in POST and PUT endpoints. |
| `@Valid` | Triggers validation on request body data to ensure input integrity. |
| `@Value` | Injects values from `application.properties` into beans (used in `DatabaseConfig`). |
| `@Bean` | Declares a Spring bean for dependency injection, as in configuring `BasicDataSource` for database connections. |

---

### 💡 **Why I Used These Annotations**

- ✅ To build clean, modular, and readable REST APIs  
- ✅ To enforce **security and role-based access control**  
- ✅ To improve maintainability by using dependency injection  
- ✅ To handle HTTP requests effectively and return appropriate responses

---



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

*****✨ Interesting Code Snippets*****


    @RestController
    
     @RequestMapping("/products")
     
    @CrossOrigin
    
    public class ProductsController {
    
    private ProductDao productDao;

    @Autowired
    public ProductsController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Product> search(@RequestParam(name="cat", required = false) Integer categoryId) {
        return productDao.search(categoryId);
    }



*****✅ Why it’s interesting:*****


Uses @RestController to define a REST API.

@RequestMapping("/products") maps base URL.

@CrossOrigin enables cross-origin requests from frontend apps.

@PreAuthorize("permitAll()") allows public access to the GET endpoint.

Clean DAO injection with @Autowired for decoupled design.

