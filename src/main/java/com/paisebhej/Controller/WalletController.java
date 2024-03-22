package com.paisebhej.Controller;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Customer;
import com.paisebhej.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("customer/wallet")
    public ResponseEntity<Customer> createWallet(@RequestParam(required = false) String name ,
                                                 @RequestParam(required = false) String mobile,
                                                 @RequestParam(required = false) Double balance,
                                                 @RequestParam(required = false) String key) throws
            CustomerException, WalletException{
        Customer savedCustomer = walletService.createAccount(name, mobile, balance, key);

        return new ResponseEntity<Customer>(savedCustomer, HttpStatus.OK);

    }
    @PostMapping("/customer/wallet/fundTransfer")
    public ResponseEntity<String> fundTransferHandler(@RequestParam(required = false) String SourceMobile,
                                                      @RequestParam(required = false) String targetMobile, @RequestParam(required = false) Double amount,
                                                      @RequestParam(required = false) String key) throws CustomerException, WalletException {

        String message = walletService.fundTransfer(SourceMobile, targetMobile, amount, key);
        return new ResponseEntity<String>(message, HttpStatus.OK);

    }

    @PostMapping("/customer/wallet/deposit")
    public ResponseEntity<Customer> fundDepositHandler(@RequestParam(required = false) String mobile,
                                                       @RequestParam(required = false) Double amount, @RequestParam(required = false) String key)
            throws CustomerException {

        Customer customer = walletService.depositAmount(mobile, amount, key);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
}
