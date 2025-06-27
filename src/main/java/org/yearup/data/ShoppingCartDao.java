package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

        // Retrieve the cart for a specific user
        ShoppingCart getCart(int userId);

        // Add a product to the cart with a specific quantity
        void addToCart(int userId, int productId, int quantity);

        // Update the quantity of an existing product in the cart
        void updateQuantity(int userId, int productId, int quantity);

        // Remove all products from the user's cart
        void clearCart(int userId);
}
