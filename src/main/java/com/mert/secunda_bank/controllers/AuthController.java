package com.mert.secunda_bank.controllers;

import com.mert.secunda_bank.dto.AccountResponseDTO;
import com.mert.secunda_bank.dto.CreateAccountRequestDTO;
import com.mert.secunda_bank.dto.LoginRequestDTO;
import com.mert.secunda_bank.dto.AuthResponseDTO;
import com.mert.secunda_bank.mappers.AccountMapper;
import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.services.AccountService;
import com.mert.secunda_bank.services.JwtService;
import com.mert.secunda_bank.dto.ResetPasswordRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AccountService accountService, 
                          AuthenticationManager authenticationManager, 
                          JwtService jwtService) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/home")
    public String homepageFunc() {
        return "Hello world";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginAccount(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        try {
            Account accountToCreate = AccountMapper.toAccount(createAccountRequestDTO);
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
