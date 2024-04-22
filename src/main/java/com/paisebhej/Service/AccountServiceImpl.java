package com.paisebhej.Service;

import com.paisebhej.Exceptions.AccountException;
import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Account;
import com.paisebhej.Model.CurrentUserSession;
import com.paisebhej.Model.Wallet;
import com.paisebhej.Respository.AccountRepository;
import com.paisebhej.Respository.CustomerRepository;
import com.paisebhej.Respository.SessionRepository;
import com.paisebhej.Respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Account addAccount(Account account, String key) throws CustomerException, WalletException {
       CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
       if (loggedInUser==null){
           throw  new CustomerException("PLEASE PROVIDE A VALID KEY");
       }
       Wallet wallet = walletRepository.findById(customerRepository.findById(loggedInUser.getUserId()).get().getWallet().getWalletId())
               .orElseThrow(()->new WalletException("WALLET DOES NOT EXIST"));
       Account savedAccount = accountRepository.save(account);
       wallet.getAccounts().add(savedAccount);
        return savedAccount;
    }

    @Override
    public Account removeAccount(Integer accountId, String key) throws CustomerException, WalletException, AccountException {
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if (loggedInUser==null){
            throw  new CustomerException("PLEASE PROVIDE A VALID KEY");
        }
        Account existingAccount = accountRepository.findById(accountId).orElseThrow(()->
                new AccountException("ACCOUNT NOT FOUND"));
        accountRepository.delete(existingAccount);

        return existingAccount;
    }

    @Override
    public List<Account> viewAllAccounts(Integer walledId, String key) throws
            CustomerException, WalletException, AccountException {
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if (loggedInUser==null){
            throw new CustomerException("PROVIDE A VALID KEY");
        }
        Wallet existingWallet= walletRepository.findById(walledId).orElseThrow(()->
                new WalletException("WALLET NOT FOUND"));
        if (existingWallet.getAccounts().isEmpty())
            throw  new AccountException("ACCOUNT NOT FOUND");
        return existingWallet.getAccounts();
    }
}
