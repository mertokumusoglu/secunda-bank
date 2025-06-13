package com.mert.secunda_bank.repositories;

import com.mert.secunda_bank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdentityNumber(Long identityNumber);
    Optional<Account> findByEmail(String email);
}