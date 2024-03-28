package com.paisebhej.Service;

import com.paisebhej.DTO.TransactionDTO;
import com.paisebhej.Exceptions.TransactionException;
import com.paisebhej.Model.Transaction;
import com.paisebhej.Model.TransactionType;
import com.paisebhej.Model.Wallet;
import com.paisebhej.Respository.TransactionRepository;
import com.paisebhej.Respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Override
    public TransactionDTO addTransaction(TransactionDTO transactionDTO) throws TransactionException {
        try {
            Transaction transaction = new Transaction();
            transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionType(TransactionType.valueOf(transactionDTO.getTransactionType()));
            Wallet wallet = walletRepository.findById(transactionDTO.getWalletId()).orElseThrow(()->new TransactionException("WALLET NOT FOUND"));
            if (transaction.getTransactionType()==TransactionType.CREDIT) {
                wallet.setBalance(wallet.getBalance()+transactionDTO.getTransactionAmount());
            } else if (transaction.getTransactionType()==TransactionType.DEBIT) {
                if (wallet.getBalance()<transactionDTO.getTransactionAmount()) {
                    throw new TransactionException("INSUFFICIENT BALANCE");
                }
                wallet.setBalance(wallet.getBalance()-transactionDTO.getTransactionAmount());
            } else {
                throw new TransactionException("INVALID TRANSACTION TYPE");
            }
            transaction.setWallet(wallet);
            transactionRepository.save(transaction);
            return transactionDTO;
        } catch (Exception e) {
            throw new TransactionException("TRANSACTION FAILED");
        }
    }

    @Override
    public List<Transaction> viewTransactionByWallet(Integer walletId) throws TransactionException {
        List<Transaction> transactions =transactionRepository.findByWalletId((walletId));
        if (transactions.isEmpty()){
            throw new TransactionException("NO TRANSACTIONS");
        }
        return transactions;
    }

    @Override
    public List<Transaction> viewTransactionByDate(String from, String to) throws TransactionException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate localDate= LocalDate.parse(from);
        LocalDate localDate1= LocalDate.parse(to);
        List<Transaction> transactions = transactionRepository.findByTransactionByDate(localDate,localDate1);
        if (transactions.isEmpty()){
            throw  new TransactionException("NO TRANSACTIONS ON THESE DATES");
        }
        return transactions;
    }

    @Override
    public List<Transaction> viewTransactionByType(String type) throws TransactionException {
        List<Transaction> transactions = transactionRepository.findByTransactionType(type);

        if (transactions.isEmpty()){
            throw  new TransactionException("NO TRANSACTION");
        }
        return transactions;
    }
}
