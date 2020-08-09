package com.svetikov.ecommerceshop.repository.cart;

import com.svetikov.ecommerceshop.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart getCartByUserId(Long id);

    void deleteByUserId(Long id);

}
