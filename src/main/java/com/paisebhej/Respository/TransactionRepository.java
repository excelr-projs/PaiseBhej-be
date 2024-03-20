package com.paisebhej.Respository;

import com.paisebhej.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("select w.transactions from Wallet w where w.walletId=?1")
    public List<Transaction> findByWalletId(int walletId);
    @Query("select t from Transaction t where t.transactionDate>=?1 and t.transactionDate<=?2 ")
    public  List<Transaction> findByTransactionByDate(LocalDate from, LocalDate to);
    public List<Transaction> findByTransactionType(String type);
}
