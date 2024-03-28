package com.paisebhej.DTO;

import com.paisebhej.Model.TransactionType;
import lombok.Data;

@Data
public class TransactionDTO {
    private Double transactionAmount;
    private String transactionType;
    private int walletId;
}