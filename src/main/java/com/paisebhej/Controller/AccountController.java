package com.paisebhej.Controller;

import com.paisebhej.Exceptions.AccountException;
import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Account;
import com.paisebhej.Model.Wallet;
import com.paisebhej.Service.AccountService;
import com.paisebhej.Service.WalletService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Getter
@Setter
class AddAccountRequest {
    private String key;
    private Integer walletId;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
}

class RemoveAccountRequest {
    private Integer accountId;
    private String key;

    public RemoveAccountRequest(Integer accountId, String key) {
        this.accountId = accountId;
        this.key = key;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getKey() {
        return key;
    }
}

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private WalletService walletService;

    @PostMapping("/addAccount")
    public ResponseEntity<Account> addAccountHandler(@RequestBody AddAccountRequest addAccountRequest) throws CustomerException, WalletException {
        Account account = new Account();
        Wallet wallet = walletService.getWallet(addAccountRequest.getWalletId(), addAccountRequest.getKey());
        account.setAccountNumber(addAccountRequest.getAccountNumber());
        account.setIfscCode(addAccountRequest.getIfscCode());
        account.setBankName(addAccountRequest.getBankName());
        account.setWallet(wallet);
        Account savedAccount = accountService.addAccount(account, addAccountRequest.getKey());
        return new ResponseEntity<Account>(savedAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/removeAccount")
    public ResponseEntity<Account> removeAccount(@RequestBody RemoveAccountRequest removeAccountRequest) throws CustomerException, AccountException, WalletException {
        Account deleteAccount = accountService.removeAccount(removeAccountRequest.getAccountId(), removeAccountRequest.getKey());
        return new ResponseEntity<Account>(deleteAccount, HttpStatus.OK);
    }

    @GetMapping("/viewAccounts")
    public ResponseEntity<List<Account>> viewAccount(@RequestParam Integer walletId, @RequestParam String key) throws CustomerException, WalletException, AccountException {
        List<Account> accounts = accountService.viewAllAccounts(walletId, key);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}
