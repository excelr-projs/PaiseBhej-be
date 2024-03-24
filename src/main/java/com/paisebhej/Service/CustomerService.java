package com.paisebhej.Service;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Model.Customer;


public interface CustomerService {
    public Customer createCustomer(Customer customer) throws CustomerException;
    public Customer updateCustomer(Customer customer, String key) throws CustomerException;
}
