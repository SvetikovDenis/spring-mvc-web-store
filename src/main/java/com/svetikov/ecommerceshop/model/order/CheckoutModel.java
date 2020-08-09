package com.svetikov.ecommerceshop.model.order;

import com.svetikov.ecommerceshop.model.cart.Cart;
import com.svetikov.ecommerceshop.model.cart.CartLine;
import com.svetikov.ecommerceshop.model.user.Address;
import com.svetikov.ecommerceshop.model.user.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CheckoutModel implements Serializable {

    private User user;
    private Address shipping;
    private Cart cart;
    private List<CartLine> cartLines;
    private OrderDetail orderDetail;
    private Integer checkoutTotal;


    public CheckoutModel() {
    }

    @Builder
    public CheckoutModel(User user, Address shipping, Cart cart, List<CartLine> cartLines, OrderDetail orderDetail, Integer checkoutTotal) {
        this.user = user;
        this.shipping = shipping;
        this.cart = cart;
        this.cartLines = cartLines;
        this.orderDetail = orderDetail;
        this.checkoutTotal = checkoutTotal;
    }
}
