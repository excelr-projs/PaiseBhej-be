package com.paisebhej.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @ToString.Exclude
    @OneToOne(mappedBy = "wallet")
    private Customer customer;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "wallet")
    private List<Account> accounts = new ArrayList<>();
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions = new ArrayList<>();
}
