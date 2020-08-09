package com.svetikov.ecommerceshop.service.user;


import com.svetikov.ecommerceshop.model.user.Address;

public interface AddressService {

    Address getAddressById(Long id);

    Address getShippingAddressByUserId(Long userId);

    Address getBillingAddressByUserId(Long userId);

    Address saveAddress(Address address);

    Address updateShippingAddress(Address address,Long userId);

    Address updateBillingAddress(Address address,Long userId);


}
