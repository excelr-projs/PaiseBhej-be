package com.paisebhej.Controller;

import com.paisebhej.DTO.TransactionDTO;
import com.paisebhej.Exceptions.TransactionException;
import com.paisebhej.Model.Transaction;
import com.paisebhej.Service.TransactionService;
import jakarta.transaction.TransactionalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("tran")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> addTransaction(
            @RequestBody TransactionDTO transactionDTO)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<TransactionDTO>(transactionService.addTransaction(transactionDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/transactions{id}")
    public ResponseEntity<List<Transaction>> viewTransactionByWallet(@PathVariable("id") Integer wallet_id)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<>(transactionService.viewTransactionByWallet(wallet_id), HttpStatus.OK);
    }

    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> viewTransactionByDate(@RequestParam String from, @RequestParam String to)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<>(transactionService.viewTransactionByDate(from, to), HttpStatus.OK);
    }

    @GetMapping("/transaction{type}")
    public ResponseEntity<List<Transaction>> viewALLTransaction(@PathVariable("type") String type)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<>(transactionService.viewAllTransaction(type), HttpStatus.OK);
    }
}
