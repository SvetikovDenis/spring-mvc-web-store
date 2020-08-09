package com.svetikov.ecommerceshop.service.cart;

import com.svetikov.ecommerceshop.model.cart.CartLine;

import java.util.List;
import java.util.Optional;

public interface CartLineService {


    List<CartLine> getAllByCartId(Long id);

    List<CartLine> getAllByUserId(Long id);

    String addCartLine(Long productId,Long userId, Optional<Integer> quantity);

    String updateCartLine(Long productId, Long userId, Integer quantity);

    String deleteCartLine(Long productId, Long userId);

    String deleteAllCartLine(Long userId);

    void deleteCartLine(CartLine cartLine);



}
