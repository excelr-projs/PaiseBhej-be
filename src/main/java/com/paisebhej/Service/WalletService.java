package com.paisebhej.Service;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Customer;
import com.paisebhej.Model.Wallet;

public interface WalletService {
    public Wallet createWallet(String mobile, Double balance, String key) throws CustomerException, WalletException;
    public Wallet getCWallet (String mobile , String key) throws CustomerException;
    public Wallet getWallet(Integer walletId, String key) throws CustomerException;
    public String fundTransfer(String senderMobile, String receiverMobile ,Double amount, String key)
        throws CustomerException , WalletException;
    public Customer depositAmount(String mobile, Double amount, String key) throws CustomerException;
}
