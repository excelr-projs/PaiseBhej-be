package com.paisebhej.Controller;

import com.paisebhej.Exceptions.AccountException;
import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Account;
import com.paisebhej.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/customers/account")
    public ResponseEntity<Account> addAccountHandler(@RequestBody Account account, @RequestParam
            (required = false) String key) throws CustomerException , WalletException{
        Account savedAccount = accountService.addAccount(account,key);
        return  new ResponseEntity<Account>(savedAccount , HttpStatus.CREATED);

    }
    @DeleteMapping("/customers/account")
    public ResponseEntity<Account> removeAccount(@RequestParam (required = false) Integer accountId ,
                                                 String key) throws CustomerException, AccountException, WalletException{
        Account deleteAccount = accountService.removeAccount(accountId,key);
        return new ResponseEntity<Account>(deleteAccount,HttpStatus.OK);
    }
    @GetMapping("/customers")
    public ResponseEntity<List<Account>> viewAccount(@RequestParam(required = false) Integer walledId,
        @RequestParam(required = false) String key) throws CustomerException, WalletException, AccountException{
        List<Account> accounts = accountService.viewAllAccounts(walledId, key);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}
