package com.paisebhej.Service;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.CurrentUserSession;
import com.paisebhej.Model.Customer;
import com.paisebhej.Model.Wallet;
import com.paisebhej.Respository.CustomerRepository;
import com.paisebhej.Respository.SessionRepository;
import com.paisebhej.Respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public Wallet createWallet(String mobile, Double balance, String key) throws CustomerException, WalletException {
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if (loggedInUser == null){
            throw new CustomerException("PLEASE PROVIDE A VALID KEY");
        }
        Customer customer = customerRepository.findByMobileNumber(mobile);
        if (customer == null){
            throw new CustomerException("CUSTOMER NOT FOUND");
        }
        Wallet wallet = new Wallet();
        wallet.setBalance(balance);
        walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public Wallet getCWallet(String mobile, String key) throws CustomerException {
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if(loggedInUser == null){
            throw new CustomerException("PLEASE PROVIDE A VALID KEY");
        }
        Customer customer = customerRepository.findByMobileNumber(mobile);
        if (customer == null){
            throw  new CustomerException("CUSTOMER NOT FOUND");
        }
        return customer.getWallet();
    }

    @Override
    public Wallet getWallet(Integer walletId, String key) throws CustomerException {
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if (loggedInUser == null) {
            throw new CustomerException("PLEASE PROVIDE A VALID KEY");
        }
        return walletRepository.findById(walletId).get();
    }

    @Override
    public String fundTransfer(String senderMobile, String receiverMobile, Double amount, String key)
            throws CustomerException, WalletException {
        CurrentUserSession leggedInUser = sessionRepository.findByUuid(key);
        if (leggedInUser == null){
            throw new CustomerException("PLEASE PROVIDE A VALID KEY");
        }
        Customer sender = customerRepository.findByMobileNumber(senderMobile);
        if (sender==null){
            throw new CustomerException("PLEASE PROVIDE A VALID MOBILE NUMBER");
        }
        Customer receiver = customerRepository.findByMobileNumber(senderMobile);
        if (receiver==null){
            throw new CustomerException("PLEASE PROVIDE A VALID MOBILE NUMBER");
        }
        if(amount<sender.getWallet().getBalance()){
            sender.getWallet().setBalance(receiver.getWallet().getBalance()-amount);
            receiver.getWallet().setBalance(receiver.getWallet().getBalance()+amount);
            customerRepository.save(sender);
            customerRepository.save(receiver);
            return "TRANSACTION COMPLETED";
        }
        else{
            throw new WalletException("AMOUNT NOT VALID");
        }

    }

    @Override
    public Customer depositAmount(String mobile, Double amount, String key)
            throws CustomerException {
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if (loggedInUser == null) {
            throw new CustomerException("PLEASE PROVIDE A VALID KEY");
        }
        Customer customer = customerRepository.findByMobileNumber(mobile);
        if (customer==null){
            throw new CustomerException("PLEASE ENTER A VALID PHONE NUMBER");
        }
        customer.getWallet().setBalance(customer.getWallet().getBalance()+amount);
        customerRepository.save(customer);
        return customer;
    }
}
