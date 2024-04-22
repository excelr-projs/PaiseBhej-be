package com.paisebhej.Respository;

import com.paisebhej.Model.Customer;
import com.paisebhej.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public Customer findByMobileNumber(String mobileNumber);
    public Customer findByEmail(String email);

    public Customer findByWallet(Wallet wallet);
}
