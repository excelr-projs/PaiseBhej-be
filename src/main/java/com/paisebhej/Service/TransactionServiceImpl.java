package com.paisebhej.Service;

import com.paisebhej.DTO.TransactionDTO;
import com.paisebhej.Exceptions.TransactionException;
import com.paisebhej.Model.Transaction;
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
        if (transactionDTO ==null){
            throw new TransactionException("ENTER CORRECT DETAILS");
        }
        Wallet wallet = walletRepository.findById(transactionDTO.getWalletId()).get();
        if (transactionDTO.getWalletId()!=wallet.getWalletId()){
            throw new TransactionException("INVALID WALLET ID");
        }
        if (wallet.getBalance()<transactionDTO.getAmount()){
            throw new TransactionException("INSUFFICIENT FUNDS");
        }
        Transaction transaction =new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setDescription(transactionDTO.getDescription());
        wallet.getTransactions().add(transaction);
        walletRepository.save(wallet);
        return transactionDTO;
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
    public List<Transaction> viewAllTransaction(String type) throws TransactionException {
        List<Transaction> transactions = transactionRepository.findByTransactionType(type);

        if (transactions.isEmpty()){
            throw  new TransactionException("NO TRANSACTION");
        }
        return transactions;
    }
}
