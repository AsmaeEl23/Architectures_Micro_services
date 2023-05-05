package org.sdia.bankaccountservice.service;


import org.sdia.bankaccountservice.DTO.*;

public interface AccountService {
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);
}
