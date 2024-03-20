package com.paisebhej.Service;

import com.paisebhej.Exceptions.AccountException;
import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Account;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AccountService {
    public Account addAccount(Account account ,String key) throws CustomerException, WalletException;
    public Account removeAccount(Integer accountId, String key) throws  CustomerException, WalletException,
            AccountException;
    public List<Account> viewAllAccounts(Integer walledId , String key) throws  CustomerException, WalletException,
            AccountException;
}
