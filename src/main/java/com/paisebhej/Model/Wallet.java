package com.paisebhej.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;

    private Double balance;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "wallet")
    private List<Transaction> transactions = new ArrayList<>();
}
