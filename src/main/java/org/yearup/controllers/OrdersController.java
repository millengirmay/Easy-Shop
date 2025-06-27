package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class OrdersController {
    private final OrderDao orderDao;
    private final ShoppingCartDao cartDao;
    private final UserDao userDao;

    @Autowired
    public OrdersController(OrderDao orderDao, ShoppingCartDao cartDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.cartDao = cartDao;
        this.userDao = userDao;
    }

    @PostMapping("")
    public void checkout(Principal principal) {
        try {
            String username = principal.getName();
            if (username == null || username.isBlank()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated.");
            }

            User user = userDao.getByUserName(username);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found.");
            }

            ShoppingCart cart = cartDao.getByUserId(user.getId());
            if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty.");
            }

            List<ShoppingCartItem> items = new ArrayList<>(cart.getItems().values());

            Order order = new Order();
            order.setUserId(user.getId());
            order.setOrderDate(LocalDate.now());
            order.setStatus("Pending");

            int orderId = orderDao.create(order);
            if (orderId <= 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create order.");
            }

            for (ShoppingCartItem item : items) {
                OrderLineItem lineItem = new OrderLineItem();
                lineItem.setOrderId(orderId);
                lineItem.setProductId(item.getProduct().getProductId());
                lineItem.setQuantity(item.getQuantity());
                lineItem.setPrice(item.getProduct().getPrice());

                orderDao.addLineItem(lineItem);
            }

            cartDao.clearCart(user.getId());

        } catch (ResponseStatusException e) {
            throw e; // rethrow known errors
        } catch (Exception e) {
            e.printStackTrace(); // helpful for debugging during development
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to checkout");
        }
    }
}
