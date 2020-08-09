package com.svetikov.ecommerceshop.service.user.impl;

import com.svetikov.ecommerceshop.model.user.Address;
import com.svetikov.ecommerceshop.repository.user.AddressRepository;
import com.svetikov.ecommerceshop.service.user.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }


    @Override
    public Address updateShippingAddress(Address address, Long userId) {

        Address existed = addressRepository.getByUserIdAndIsShippingIsTrue(userId);
        if (existed == null) {
            throw new IllegalArgumentException("In updateShipping - Shipping address for user with id " + userId + " was not found");
        }
        Address updatedAddress = Address
                        .builder()
                        .addressLineOne(existed.getAddressLineOne())
                        .addressLineTwo(existed.getAddressLineTwo())
                        .userId(existed.getUserId())
                        .city(existed.getCity())
                        .state(existed.getState())
                        .country(existed.getCountry())
                        .postalCode(existed.getPostalCode())
                        .isBilling(false)
                        .isShipping(true)
                        .build();

        updatedAddress = addressRepository.save(updatedAddress);
        return updatedAddress;
    }

    @Override
    public Address updateBillingAddress(Address address, Long userId) {
        Address existed = addressRepository.getByUserIdAndIsBillingTrue(userId);
        if (existed == null) {
            throw new IllegalArgumentException("In updateBillingAddress - Billing address for user with id " + userId + " was not found");
        }
        Address updatedAddress = Address
                .builder()
                .addressLineOne(existed.getAddressLineOne())
                .addressLineTwo(existed.getAddressLineTwo())
                .userId(existed.getUserId())
                .city(existed.getCity())
                .state(existed.getState())
                .country(existed.getCountry())
                .postalCode(existed.getPostalCode())
                .isBilling(true)
                .isShipping(false)
                .build();

        updatedAddress = addressRepository.save(updatedAddress);
        return updatedAddress;
    }

    @Override
    public Address getShippingAddressByUserId(Long userId) {
        return addressRepository.getByUserIdAndIsShippingIsTrue(userId);
    }

    @Override
    public Address getBillingAddressByUserId(Long userId) {
        return addressRepository.getByUserIdAndIsBillingTrue(userId);
    }

}
