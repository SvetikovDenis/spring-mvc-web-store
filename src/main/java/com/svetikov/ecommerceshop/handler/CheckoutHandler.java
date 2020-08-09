package com.svetikov.ecommerceshop.handler;

import com.svetikov.ecommerceshop.model.cart.Cart;
import com.svetikov.ecommerceshop.model.cart.CartLine;
import com.svetikov.ecommerceshop.model.order.CheckoutModel;
import com.svetikov.ecommerceshop.model.order.OrderDetail;
import com.svetikov.ecommerceshop.model.order.OrderItem;
import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.model.user.Address;
import com.svetikov.ecommerceshop.model.user.User;
import com.svetikov.ecommerceshop.service.cart.CartLineService;
import com.svetikov.ecommerceshop.service.cart.CartService;
import com.svetikov.ecommerceshop.service.order.OrderDetailService;
import com.svetikov.ecommerceshop.service.product.ProductService;
import com.svetikov.ecommerceshop.service.user.AddressService;
import com.svetikov.ecommerceshop.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckoutHandler {


    @Autowired
    private UserService userService;

    @Autowired
    private CartLineService cartLineService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CartService cartService;

    private static final Logger log = LoggerFactory.getLogger(CheckoutHandler.class);


    public CheckoutModel init(String name){

        System.out.println(name);
        User user = userService.getUserByUserName(name);
        CheckoutModel checkoutModel = null;
        if(user!=null) {
            Cart cart = user.getCart();
            checkoutModel = new CheckoutModel();
            checkoutModel.setUser(user);
            checkoutModel.setCart(cart);

            Integer checkoutTotal = 0;
            List<CartLine> cartLines = cartLineService.getAllByCartId(cart.getId());

            if(cartLines.isEmpty()) {
                throw new IllegalArgumentException("There are no products available for checkout!");
            }
            for(CartLine cartLine: cartLines) {
                checkoutTotal += cartLine.getProductCount() * cartLine.getProduct().getUnitPrice();
            }
            checkoutModel.setCartLines(cartLines);
            checkoutModel.setCheckoutTotal(checkoutTotal);
        }

        return checkoutModel;
    }

    public List<Address> getShippingAddress(CheckoutModel model) {

        List<Address> addresses = new ArrayList<>();
        Address address;
        Long userId = model.getUser().getId();
        address =  addressService.getShippingAddressByUserId(userId);
        if (address != null) {
            addresses.add(address);
        }
        address = addressService.getBillingAddressByUserId(userId);
        if (address != null) {
            addresses.add(address);
        }

        if (addresses.isEmpty()) {
            log.warn("In getShippingAddress - no address was found for user with id : {}",userId);
        }
        return addresses;
    }

    public String saveAddressSelection(CheckoutModel checkoutModel, Long shippingId) {
        String transitionValue = "success";
        Address shipping = addressService.getAddressById(shippingId);
        checkoutModel.setShipping(shipping);
        return transitionValue;

    }

    public String saveAddress(CheckoutModel checkoutModel, Address shipping) {
        Long userId = checkoutModel.getUser().getId();
        Address savedShipping = addressService.getShippingAddressByUserId(userId);
        if (savedShipping != null) {
            addressService.updateShippingAddress(shipping, userId);
        }
        String transitionValue = "success";
        shipping.setUserId(userId);
        shipping.setIsShipping(true);
        addressService.saveAddress(shipping);
        checkoutModel.setShipping(shipping);
        return transitionValue;

    }

    public String saveOrder(CheckoutModel checkoutModel) {
        String transitionValue = "success";

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUser(checkoutModel.getUser());
        orderDetail.setShipping(checkoutModel.getShipping());

        Address billing = addressService.getBillingAddressByUserId(checkoutModel.getUser().getId());
        orderDetail.setBilling(billing);

        List<CartLine> cartLines = checkoutModel.getCartLines();
        OrderItem orderItem = null;

        Integer orderTotal = 0;
        Integer orderCount = 0;
        Product product = null;

        for(CartLine cartLine : cartLines) {

            orderItem = new OrderItem();

            orderItem.setBuyingPrice(cartLine.getProduct().getUnitPrice());
            orderItem.setProduct(cartLine.getProduct());
            orderItem.setProductCount(cartLine.getProductCount());
            orderItem.setTotal(cartLine.getProductCount() * cartLine.getProduct().getUnitPrice());

            orderItem.setOrderDetail(orderDetail);
            orderDetail.getOrderItems().add(orderItem);

            orderTotal += orderItem.getTotal();
            orderCount++;
            product = cartLine.getProduct();
            product.setQuantity(product.getQuantity() - cartLine.getProductCount());
            product.setPurchases(product.getPurchases() + cartLine.getProductCount());

            productService.saveProduct(product);
            cartLineService.deleteCartLine(cartLine);

        }

        orderDetail.setOrderTotal(orderTotal);
        orderDetail.setOrderCount(orderCount);
        orderDetail.setOrderDate(new Timestamp(System.currentTimeMillis()));

        orderDetailService.save(orderDetail);

        checkoutModel.setOrderDetail(orderDetail);

        Cart cart = checkoutModel.getCart();
        cartService.save(cart);

        return transitionValue;
    }


    public OrderDetail getOrderDetail(CheckoutModel checkoutModel) {
        return checkoutModel.getOrderDetail();
    }


}
