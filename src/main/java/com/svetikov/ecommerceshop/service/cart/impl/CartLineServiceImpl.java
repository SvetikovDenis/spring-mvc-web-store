package com.svetikov.ecommerceshop.service.cart.impl;

import com.svetikov.ecommerceshop.exception.EntityNotFoundException;
import com.svetikov.ecommerceshop.model.cart.Cart;
import com.svetikov.ecommerceshop.model.cart.CartLine;
import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.repository.cart.CartLineRepository;
import com.svetikov.ecommerceshop.service.cart.CartLineService;
import com.svetikov.ecommerceshop.service.cart.CartService;
import com.svetikov.ecommerceshop.service.product.ProductService;
import com.svetikov.ecommerceshop.validation.CartValidationState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartLineServiceImpl implements CartLineService {

    private final Logger log = LoggerFactory.getLogger(CartLineServiceImpl.class);

    @Autowired
    private CartLineRepository cartLineRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;


    @Override
    public List<CartLine> getAllByCartId(Long id) {
        List<CartLine> cartLines = cartLineRepository.getAllByCartId(id);
        if (cartLines.isEmpty()) {
            log.info("In getAllByCartId - no cart lines was found for cart with id : {} ", id);
        }
        log.info("In getAllByCartId - {} cart lines was found for cart with id : {}", cartLines.size(), id);
        return cartLines;
    }

    @Override
    public List<CartLine> getAllByUserId(Long id) {
        Cart cart = cartService.getCartByUserId(id);
        if (cart == null) {
            log.info("In getAllByUserId - no cart was found for user with id : {} ", id);
        }
        List<CartLine> cartLines = cartLineRepository.getAllByCartId(cart.getId());
        log.info("In getAllByUserId - {} cart lines was found for user with id : {} ", cartLines.size(), id);
        return cartLines;
    }

    @Override
    public String addCartLine(Long productId, Long userId, Optional<Integer> quantity) {

        Product product = productService.findSingleProduct(productId);
        if (product == null) {
            log.warn("In addCartLine - no product was found for id : {} ", productId);
            throw new EntityNotFoundException(Product.class, "id", productId.toString());
        }

        Cart userCart = cartService.getCartByUserId(userId);

        if (userCart == null) {
            log.warn("In addCartLine - no cart was found for user with id : {} ", userId);
            throw new EntityNotFoundException(Cart.class, "user id", userId.toString());
        }

        List<CartLine> cartLines = cartLineRepository.getAllByCartId(userCart.getId());

        CartLine newCartLine;

        if (cartLines.isEmpty()) {
            if (validateCartLineQuantity(product, quantity.orElse(1))) {
                newCartLine = CartLine.
                        builder().
                        cart(userCart).
                        product(product).
                        productCount(quantity.orElse(1)).
                        build();
                cartLineRepository.save(newCartLine);
                log.info("In addCartLine - new product with id : {} was added to cart by user with id : {} in :{} pieces", productId, userId, quantity.orElse(1));
                return CartValidationState.STATE[0];
            } else return CartValidationState.STATE[3];

        } else {

            boolean isProductNew = true;
            Integer cartProductQuantity;

            for (int i = 0; i < cartLines.size(); i++) {
                if (cartLines.get(i).getProduct().getId() == productId) {
                    newCartLine = cartLines.get(i);
                    cartProductQuantity = newCartLine.getProductCount();

                    if (validateCartLineQuantity(newCartLine.getProduct(), cartProductQuantity + quantity.orElse(1))) {
                        newCartLine.setProductCount(cartProductQuantity + quantity.orElse(1));
                        isProductNew = false;
                        cartLineRepository.save(newCartLine);
                        log.info("In addCartLine - new product with id : {} was added to cart by user with id : {} in :{} pieces", productId, userId, quantity.orElse(1));
                        return CartValidationState.STATE[0];
                    } else {
                        isProductNew = false;
                        return CartValidationState.STATE[3];
                    }
                }
            }
            if (isProductNew) {
                newCartLine = CartLine.
                        builder().
                        cart(userCart).
                        product(product).
                        productCount(quantity.orElse(1)).
                        build();

                cartLineRepository.save(newCartLine);
                log.info("In addCartLine - new product with id : {} was added to cart by user with id : {} in :{} pieces", productId, userId, quantity.orElse(1));
                return CartValidationState.STATE[0];
            }
        }
        return CartValidationState.STATE[0];

    }

    @Override
    public String deleteCartLine(Long productId, Long userId) {
        Cart userCart = cartService.getCartByUserId(userId);
        if (userCart == null) {
            log.warn("In deleteCartLine - no cart was found for user id : {} ", userId);
            throw new EntityNotFoundException(Cart.class, "user id", userId.toString());
        }
        List<CartLine> cartLines = cartLineRepository.getAllByCartId(userCart.getId());
        if (!cartLines.isEmpty()) {
            for (int i = 0; i < cartLines.size(); i++) {
                if (cartLines.get(i).getProduct().getId() == productId) {
                    cartLineRepository.deleteById(cartLines.get(i).getId());
                    log.info("In deleteCartLine - user with id : {} deleted product with id : {} ", userId, productId);
                    return CartValidationState.STATE[2];
                }
            }
        }
        return CartValidationState.STATE[2];
    }

    @Override
    public String deleteAllCartLine(Long userId) {
        Cart userCart = cartService.getCartByUserId(userId);
        if (userCart == null) {
            log.warn("In deleteAllCartLine - no cart was found for user id : {} ", userId);
            throw new EntityNotFoundException(Cart.class, "user id", userId.toString());
        }
        List<CartLine> cartLines = cartLineRepository.getAllByCartId(userCart.getId());
        if (!cartLines.isEmpty()) {
            cartLineRepository.deleteAll(cartLines);
            log.info("In deleteAllCartLine - user with id : {} deleted all product from cart", userId);

        }
        return CartValidationState.STATE[2];
    }

    @Override
    public String updateCartLine(Long productId, Long userId, Integer quantity) {

        Cart userCart = cartService.getCartByUserId(userId);
        if (userCart == null) {
            log.warn("In updateCartLine - no cart was found for user id : {} ", userId);
            throw new EntityNotFoundException(Cart.class, "user id", userId.toString());
        }
        List<CartLine> cartLines = cartLineRepository.getAllByCartId(userCart.getId());
        if (!cartLines.isEmpty()) {
            CartLine cartLine;
            for (int i = 0; i < cartLines.size(); i++) {
                if (cartLines.get(i).getProduct().getId() == productId) {
                    cartLine = cartLines.get(i);
                    if (validateCartLineQuantity(cartLine.getProduct(), quantity)) {
                        cartLine.setProductCount(quantity);
                        cartLineRepository.save(cartLine);
                        log.info("In updateCartLine - user with id : {} updated product with id : {} to : {} pieces", userId, productId, quantity);
                        return CartValidationState.STATE[1];
                    } else {
                        return CartValidationState.STATE[3];
                    }

                }
            }
        }
        return CartValidationState.STATE[3];
    }

    @Override
    public void deleteCartLine(CartLine cartLine) {
        cartLineRepository.delete(cartLine);
        log.info("In deleteCartLine - user with id : {} deleted product with id : {} ", cartLine.getCart().getUser().getId(), cartLine.getProduct().getId());
    }

    private boolean validateCartLineQuantity(Product product, Integer quantity) {
        if (product.getQuantity() < quantity) {
            return false;
        }
        return true;
    }

}
