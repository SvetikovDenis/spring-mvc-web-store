package com.svetikov.ecommerceshop.repository.user;

import com.svetikov.ecommerceshop.model.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    Address getByUserIdAndIsBillingTrue(Long userId);

    Address getByUserIdAndIsShippingIsTrue(Long userId);

}
