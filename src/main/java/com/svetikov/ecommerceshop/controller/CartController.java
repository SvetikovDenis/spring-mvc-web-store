package com.svetikov.ecommerceshop.controller;

import com.svetikov.ecommerceshop.model.cart.CartLine;
import com.svetikov.ecommerceshop.security.UserDetailsImpl;
import com.svetikov.ecommerceshop.service.cart.CartLineService;
import com.svetikov.ecommerceshop.service.cart.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartLineService cartLineService;

    private static final Logger log = LoggerFactory.getLogger(CartController.class);


    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showCart(Model model) {
        Long userId = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        List<CartLine> cartLines = cartLineService.getAllByUserId(userId);
        Integer totalCartSum = cartService.getCartTotal(cartLines);
        model.addAttribute("cartLines", cartLines);
        model.addAttribute("totalCartSum", totalCartSum);
        log.info("In showCart - user with id : {} visit cart",userId);
        return "cart";
    }

    @RequestMapping(value = "/add/product/{id}", method = RequestMethod.GET)
    public String addCartLine(@PathVariable(name = "id") Long productId , @RequestParam("q") Optional<Integer> quantity) {
        Long userId = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        cartLineService.addCartLine(productId, userId,quantity);
        log.info("In addCartLine - user with id : {} added new product with id : {} to cart",userId,productId);
        return "redirect:/cart/show";
    }

    @RequestMapping(value = "/update/product/{id}", method = RequestMethod.GET)
    public String updateCartLine(@PathVariable(name = "id") Long productId, @RequestParam(value = "q", required = true) Integer quantity) {
        Long userId = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        cartLineService.updateCartLine(productId, userId, quantity);
        log.info("In updateCartLine - user with id : {} updated product quantity for product with id : {}",userId,productId);
        return "redirect:/cart/show";
    }

    @RequestMapping(value = "/delete/product/{id}", method = RequestMethod.GET)
    public String deleteCartLine(@PathVariable(name = "id") Long productId ) {
        Long userId = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        cartLineService.deleteCartLine(productId, userId);
        log.info("In deleteCartLine - user with id : {} deleted product with id : {} from cart", userId, productId);
        return "redirect:/cart/show";
    }
    @RequestMapping(value = "/delete/cart", method = RequestMethod.GET)
    public String deleteCartLines() {
        Long userId = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        cartLineService.deleteAllCartLine(userId);
        log.info("In deleteCartLines - user with id : {} deleted all products from cart", userId);
        return "redirect:/cart/show";
    }


}
