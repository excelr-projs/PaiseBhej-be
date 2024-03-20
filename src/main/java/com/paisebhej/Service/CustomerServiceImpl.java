package com.paisebhej.Service;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Model.CurrentUserSession;
import com.paisebhej.Model.Customer;
import com.paisebhej.Respository.CustomerRepository;
import com.paisebhej.Respository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public Customer createCustomer(Customer customer) throws CustomerException {
        Customer existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (existingCustomer!=null){
            throw new CustomerException("MOBILE NUMBER ALREADY REGISTERED");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updatedCustomer(Customer customer, String key) throws CustomerException {
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if (loggedInUser==null){
            throw new CustomerException("PLEASE PROVIDE A VALID KEY");
        }
        if (customer.getCustomerId() == loggedInUser.getUserId()){
            return customerRepository.save(customer);
        }else
            throw new CustomerException("INVALID CUSTOMER, LOGIN FIRST");

    }
}
