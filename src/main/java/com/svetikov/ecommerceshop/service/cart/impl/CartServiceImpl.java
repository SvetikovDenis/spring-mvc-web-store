package com.svetikov.ecommerceshop.service.cart.impl;

import com.svetikov.ecommerceshop.model.cart.Cart;
import com.svetikov.ecommerceshop.model.cart.CartLine;
import com.svetikov.ecommerceshop.model.user.User;
import com.svetikov.ecommerceshop.repository.cart.CartRepository;
import com.svetikov.ecommerceshop.service.cart.CartService;
import com.svetikov.ecommerceshop.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Override
    public Cart getCartByUserId(Long id) {

        Cart cart = cartRepository.getCartByUserId(id);
        if (cart == null) {
            log.warn("In getCartByUserId - no cart was found for user with id : {}", id);
            User user = userService.getUserByUserId(id);
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            log.info("In getCartByUserId - created and save new cart for user with username : {}", user.getUsername());
            return cart;
        }
        return cart;
    }


    @Override
    public Integer getCartTotal(List<CartLine> cartLines) {
        return cartLines
                .stream()
                .map(c -> c.getProduct().getUnitPrice() * c.getProductCount())
                .collect(Collectors.summingInt(Integer::intValue));
    }

    @Override
    public Cart save(Cart cart) {
        Cart newCart = cartRepository.save(cart);
        log.info("In save - cart with id : {} was saved", cart.getId());
        return newCart;
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
        log.info("In delete - cart with id : {} was deleted", cart.getId());
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
        log.info("In delete - cart with id : {} was deleted", id);
    }

    @Override
    public void deleteByUserId(Long id) {
        cartRepository.deleteByUserId(id);
        log.info("In delete - cart with user id : {} was deleted", id);
    }
}
