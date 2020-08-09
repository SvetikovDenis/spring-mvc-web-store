package com.svetikov.ecommerceshop.repository.cart;

import com.svetikov.ecommerceshop.model.cart.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartLineRepository extends JpaRepository<CartLine,Long> {
    List<CartLine> getAllByCartId(Long id);
}
