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

@RestController
@RequestMapping("/trans")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<TransactionDTO> addTransaction(
            @RequestBody TransactionDTO transactionDTO)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<TransactionDTO>(transactionService.addTransaction(transactionDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Transaction>> viewTransactionByWallet(@RequestParam Integer wallet_id)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<>(transactionService.viewTransactionByWallet(wallet_id), HttpStatus.OK);
    }

    @GetMapping("/get/{from}/{to}")
    public ResponseEntity<List<Transaction>> viewTransactionByDate(@PathVariable("from") String from, @PathVariable("to") String to)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<>(transactionService.viewTransactionByDate(from, to), HttpStatus.OK);
    }

    @GetMapping("/get/{type}")
    public ResponseEntity<List<Transaction>> viewTransactionByType(@PathVariable("type") String type)
            throws TransactionalException, TransactionException {

        return new ResponseEntity<>(transactionService.viewTransactionByType(type), HttpStatus.OK);
    }
}
