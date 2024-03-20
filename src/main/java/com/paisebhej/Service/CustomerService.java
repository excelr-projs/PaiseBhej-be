package com.paisebhej.Service;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Model.Customer;
import org.springframework.stereotype.Service;


public interface CustomerService {
    public Customer createCustomer(Customer customer) throws CustomerException;
    public Customer updatedCustomer(Customer customer, String key) throws CustomerException;
}
