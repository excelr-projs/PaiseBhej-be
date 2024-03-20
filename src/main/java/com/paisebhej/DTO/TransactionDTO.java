package com.paisebhej.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Double amount;
    private String description;
    private String transactionType;
    private int walletId;
}
