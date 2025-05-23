package com.mert.secunda_bank.controllers;

import com.mert.secunda_bank.dto.AccountResponseDTO;
import com.mert.secunda_bank.dto.CreateAccountRequestDTO;
import com.mert.secunda_bank.mappers.AccountMapper;
import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.services.AccountService;
import com.mert.secunda_bank.dto.ResetPasswordRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    // login / logout will be added

    @PostMapping("/register")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        try {
            Account accountToCreate = AccountMapper.toAccount(createAccountRequestDTO);
            // hashing is not implemented
            Account createdAccount = accountService.createAccount(accountToCreate);
            AccountResponseDTO responseDTO = AccountMapper.toAccountResponseDTO(createdAccount);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Account could not be created: " + e.getMessage(), e);
        }
    }

    @PutMapping("/users/{id}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable Long id, 
                                                @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        try {
            accountService.resetPassword(id, 
                                         resetPasswordRequestDTO.getOldPassword(), 
                                         resetPasswordRequestDTO.getNewPassword());
            return ResponseEntity.ok("Password updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error resetting password: " + e.getMessage());
        }
    }
}
