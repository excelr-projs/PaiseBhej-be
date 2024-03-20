package com.paisebhej.Service;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Customer;

public interface WalletService {
    public Customer createAccount(String name, String mobile, Double balance, String key) throws CustomerException, WalletException;
    public Customer showBalance (String mobile , String key) throws CustomerException;
    public String fundTransfer(String senderMobile, String receiverMobile ,Double amount, String key)
        throws CustomerException , WalletException;
    public Customer depositAmount(String mobile, Double amount, String key) throws CustomerException;
}
