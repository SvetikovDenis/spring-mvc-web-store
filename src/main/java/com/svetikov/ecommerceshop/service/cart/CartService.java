package com.svetikov.ecommerceshop.service.cart;

import com.svetikov.ecommerceshop.model.cart.Cart;
import com.svetikov.ecommerceshop.model.cart.CartLine;

import java.util.List;


public interface CartService {

    Cart getCartByUserId(Long id);


    Integer getCartTotal(List<CartLine> cartLines);

    Cart save(Cart cart);

    void delete(Cart cart);

    void deleteById(Long id);

    void deleteByUserId(Long id);

}
