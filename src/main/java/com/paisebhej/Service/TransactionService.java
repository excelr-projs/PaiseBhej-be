package com.paisebhej.Service;

import com.paisebhej.DTO.TransactionDTO;
import com.paisebhej.Exceptions.TransactionException;
import com.paisebhej.Model.Transaction;

import java.util.List;

public interface TransactionService {
    public TransactionDTO addTransaction(TransactionDTO transactionDTO) throws TransactionException;
    public List<Transaction> viewTransactionByWallet(Integer walletId) throws TransactionException;
    public List<Transaction> viewTransactionByDate(String from, String to) throws TransactionException;
    public  List<Transaction> viewAllTransaction(String type ) throws  TransactionException;
}
