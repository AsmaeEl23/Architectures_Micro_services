package org.sdia.bankaccountservice.web;

import org.sdia.bankaccountservice.DTO.BankAccountRequestDTO;
import org.sdia.bankaccountservice.DTO.BankAccountResponseDTO;
import org.sdia.bankaccountservice.entities.BankAccount;
import org.sdia.bankaccountservice.mappers.AccountMapper;
import org.sdia.bankaccountservice.repositories.BankAccountRepository;
import org.sdia.bankaccountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountRestControler {
    private BankAccountRepository bankAccountRepository;
    private AccountService accountService;
    private AccountMapper accountMapper;
    public AccountRestControler(BankAccountRepository bankAccountRepository, AccountService accountService){
        this.bankAccountRepository=bankAccountRepository;
        this.accountService = accountService;
    }
    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return bankAccountRepository.findAll();
    }
    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
    }
    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO requestDTO){
        return accountService.addAccount(requestDTO);
    }
    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id,@RequestBody BankAccount bankAccount){
        BankAccount account=bankAccountRepository.findById(id).orElseThrow();
        if(bankAccount.getBalance()!=null)account.setBalance(bankAccount.getBalance());
        if(bankAccount.getCreatedAt()!=null)account.setCreatedAt(bankAccount.getCreatedAt());
        if(bankAccount.getType()!=null)account.setType(bankAccount.getType());
        if(bankAccount.getCurrency()!=null)account.setCurrency(bankAccount.getCurrency());
        return bankAccountRepository.save(account);
    }
    @DeleteMapping("/bankAccounts/{id}")
    public void delete(@PathVariable String id){
        bankAccountRepository.deleteById(id);
    }
}
