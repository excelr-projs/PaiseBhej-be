package com.paisebhej.Controller;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Model.Customer;
import com.paisebhej.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/saveCustomer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) throws CustomerException{
        Customer savedCustomer = customerService.createCustomer(customer);
        return  new ResponseEntity<Customer>(savedCustomer, HttpStatus.CREATED);
    }
    @PostMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,
                                                   @RequestParam(required = false) String key) throws CustomerException{
        Customer updatedCustomer = customerService.updatedCustomer(customer,key);
        return  new ResponseEntity<Customer>(updatedCustomer,HttpStatus.OK);


    }
}
