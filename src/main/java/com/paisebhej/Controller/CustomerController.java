package com.paisebhej.Controller;

import com.paisebhej.DTO.RegisterDTO;
import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Model.Customer;
import com.paisebhej.Model.Wallet;
import com.paisebhej.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> saveCustomer(@RequestBody RegisterDTO registerDTO) throws CustomerException {
        try {
            Customer customer = new Customer();
            Wallet wallet = new Wallet();
            wallet.setBalance(0.0);
            customer.setName(registerDTO.getName());
            customer.setMobileNumber(registerDTO.getMobileNumber());
            customer.setPassword(registerDTO.getPassword());
            customer.setWallet(wallet);
            Customer savedCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<Customer>(savedCustomer, HttpStatus.CREATED);
        } catch (CustomerException e) {
            throw new CustomerException(e.getMessage());
        }
    }

    @PostMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,
            @RequestParam(required = false) String key) throws CustomerException {
        Customer updatedCustomer = customerService.updateCustomer(customer, key);
        return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);
    }
}
