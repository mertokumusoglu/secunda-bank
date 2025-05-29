package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.Bill;
import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.repositories.AccountRepository;
import com.mert.secunda_bank.dto.UpdateAccountRequestDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasRole('ADMIN') or #accountNumber == authentication.principal.accountId")
    @Transactional
    public Account getAccountByAccountNumber(Long accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber, LockModeType.PESSIMISTIC_WRITE);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return account;
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    public Account getAccountByIdentityNumber(Long identityNumber) {
        Account account = entityManager.find(Account.class, identityNumber, LockModeType.PESSIMISTIC_WRITE);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return account;
    }
    @Transactional
    public Account createAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }
    
    @Transactional
    public void resetPassword(Long accountNumber, String currentPassword, String newPassword) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        if (!passwordEncoder.matches(currentPassword, account.getPassword())) {
            throw new RuntimeException("Incorrect current password");
        }
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    public List<Transaction> getTransactions(Long accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return account.getTransactions();
    }

    public List<Bill> getBills(Long accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return account.getBills();
    }
    @Transactional
    public Account updateAccount(Long accountNumber, UpdateAccountRequestDTO dto) {
        Account existingAccount = getAccountByAccountNumber(accountNumber);
        
        if (dto.getName() != null) {
            existingAccount.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            existingAccount.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) {
            existingAccount.setPhoneNumber(dto.getPhoneNumber());
        }
        return existingAccount;
    }
    @Transactional
    public void deleteAccount(Long AccountNumber) {
        Account account = getAccountByAccountNumber(AccountNumber);
        accountRepository.delete(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
