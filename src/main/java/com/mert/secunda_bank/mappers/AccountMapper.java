package com.mert.secunda_bank.mappers;

import com.mert.secunda_bank.dto.AccountResponseDTO;
import com.mert.secunda_bank.dto.CreateAccountRequestDTO;
import com.mert.secunda_bank.models.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountResponseDTO toAccountResponseDTO(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountResponseDTO(
                account.getAccountId(),
                account.getIdentityNumber(),
                account.getName(),
                account.getEmail(),
                account.getPhoneNumber(),
                account.getBalance(),
                account.getLoanDebt()
        );
    }

    public static List<AccountResponseDTO> toAccountResponseDTOList(List<Account> accounts) {
        if (accounts == null) {
            return null;
        }
        return accounts.stream()
                .map(AccountMapper::toAccountResponseDTO)
                .collect(Collectors.toList());
    }

    public static Account toAccount(CreateAccountRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Account.builder()
                .identityNumber(dto.getIdentityNumber())
                .name(dto.getName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
} 