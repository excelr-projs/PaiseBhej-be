package com.paisebhej.Service;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.CurrentUserSession;
import com.paisebhej.Model.Customer;
import com.paisebhej.Model.Wallet;
import com.paisebhej.Respository.CustomerRepository;
import com.paisebhej.Respository.SessionRepository;
import com.paisebhej.Respository.WalletRepository;

import jakarta.mail.MessagingException;

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
    @Autowired
    private EmailService emailService;
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
        CurrentUserSession loggedInUser = sessionRepository.findByUuid(key);
        if (loggedInUser == null) {
            throw new CustomerException("PLEASE PROVIDE A VALID KEY");
        }

        Customer sender = customerRepository.findByMobileNumber(senderMobile);
        if (sender == null) {
            throw new CustomerException("SENDER MOBILE NUMBER NOT FOUND");
        }

        Customer receiver = customerRepository.findByMobileNumber(receiverMobile);
        if (receiver == null) {
            throw new CustomerException("RECEIVER MOBILE NUMBER NOT FOUND");
        }

        if (amount <= 0) {
            throw new WalletException("AMOUNT MUST BE GREATER THAN ZERO");
        }

        if (amount > sender.getWallet().getBalance()) {
            throw new WalletException("INSUFFICIENT BALANCE");
        }

        sender.getWallet().setBalance(sender.getWallet().getBalance() - amount);
        receiver.getWallet().setBalance(receiver.getWallet().getBalance() + amount);


        try {
            emailService.sendEmail(sender.getEmail(), "Money Transfer Notification", "You have sent $" + amount + " to " + receiver.getEmail());
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            emailService.sendEmail(receiver.getEmail(), "Money Received Notification", "You have received $" + amount + " from " + sender.getEmail());
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        customerRepository.save(sender);
        customerRepository.save(receiver);

        return "TRANSACTION COMPLETED";
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
        try {
            emailService.sendEmail(customer.getEmail(), "Deposit Notification", "You have successfully deposited $" + amount + " into your wallet.");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return customer;
    }
}
