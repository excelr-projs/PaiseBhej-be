package com.paisebhej.Controller;

import com.paisebhej.Exceptions.CustomerException;
import com.paisebhej.Exceptions.WalletException;
import com.paisebhej.Model.Customer;
import com.paisebhej.Model.Wallet;
import com.paisebhej.Service.WalletService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
class CreateWalletRequest {
    private String mobile;
    private Double balance;
    private String key;

    public CreateWalletRequest(String mobile, Double balance, String key) {
        this.mobile = mobile;
        this.balance = balance;
        this.key = key;
    }

}

@Getter
class FundTransferRequest {
    private String SourceMobile;
    private String targetMobile;
    private String senderEmail;
    private String receiverEmail;
    private Double amount;
    private String key;

    public FundTransferRequest(String SourceMobile, String targetMobile,  Double amount, String key) {
        this.SourceMobile = SourceMobile;
        this.targetMobile = targetMobile;

        this.amount = amount;
        this.key = key;
    }

}

@Getter
class DepositRequest {
    private String mobile;
    private Double amount;
    private String key;

    public DepositRequest(String mobile, Double amount, String key) {
        this.mobile = mobile;
        this.amount = amount;
        this.key = key;
    }

}
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam String mobile, @RequestParam String uuid) throws CustomerException, WalletException {
        Wallet Wallet = walletService.getCWallet(mobile, uuid);
        System.out.print(Wallet);
        Double balance = Wallet.getBalance();
        return new ResponseEntity<Double>(balance, HttpStatus.OK);
    }
    
    @PostMapping("/createWallet")
    public ResponseEntity<Wallet> createWalletHandler(@RequestBody CreateWalletRequest createWalletRequest) throws CustomerException, WalletException {
        Wallet wallet = walletService.createWallet(createWalletRequest.getMobile(), createWalletRequest.getBalance(), createWalletRequest.getKey());
        return new ResponseEntity<Wallet>(wallet, HttpStatus.CREATED);
    }

    @PostMapping("/fundTransfer")
    public ResponseEntity<String> fundTransferHandler(@RequestBody FundTransferRequest fundTransferRequest) throws CustomerException, WalletException {
        String message = walletService.fundTransfer(fundTransferRequest.getSourceMobile(), fundTransferRequest.getTargetMobile(), fundTransferRequest.getAmount(), fundTransferRequest.getKey());
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Customer> fundDepositHandler(@RequestBody DepositRequest depositRequest) throws CustomerException {
        Customer customer = walletService.depositAmount(depositRequest.getMobile(), depositRequest.getAmount(), depositRequest.getKey());
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
}