package com.mert.secunda_bank.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    private Long identityNumber;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private BigDecimal balance = BigDecimal.valueOf(50.0);
    private BigDecimal loanDebt;

    @OneToMany(mappedBy = "account")
    private List<Bill> bills = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_number"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    // UserDetails fields
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private Account() {
    }

    public Long getAccountId() {
        return accountNumber;
    }

    public Long getIdentityNumber() {
        return identityNumber;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getLoanDebt() {
        return loanDebt;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email; // Using email as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoanDebt(BigDecimal loanDebt) {
        this.loanDebt = loanDebt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public static class Builder {
        private Account account = new Account();

        public Builder identityNumber(Long identityNumber) {
            account.identityNumber = identityNumber;
            return this;
        }

        public Builder name(String name) {
            account.name = name;
            return this;
        }

        public Builder password(String password) {
            account.password = password;
            return this;
        }

        public Builder email(String email) {
            account.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            account.phoneNumber = phoneNumber;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            account.balance = balance;
            return this;
        }

        public Builder loanDebt(BigDecimal loanDebt) {
            account.loanDebt = loanDebt;
            return this;
        }

        public Builder roles(Set<String> roles) {
            account.roles = roles;
            return this;
        }

        public Account build() {
            validateAccountData();
            return account;
        }

        private void validateAccountData() {
            StringBuilder errors = new StringBuilder();

            if (account.identityNumber == null) {
                errors.append("Identity number is required. ");
            }
            if (account.name == null) {
                errors.append("Name is required. ");
            }
            if (account.password == null) {
                errors.append("Password is required. ");
            }
            if (account.email == null) {
                errors.append("Email is required. ");
            }
            if (account.phoneNumber == null) {
                errors.append("Phone number is required. ");
            }
            if (account.balance == null) {
                account.balance = BigDecimal.valueOf(50.0);
            }
            if (account.loanDebt == null) {
                account.loanDebt = BigDecimal.ZERO;
            }
            if (account.getRoles().isEmpty()) {
                account.setRoles(new HashSet<>(Set.of("ROLE_USER")));
            }
            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid account data: " + errors.toString());
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}