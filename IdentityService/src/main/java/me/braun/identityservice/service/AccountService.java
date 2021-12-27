package me.braun.identityservice.service;

import me.braun.identityservice.data.model.Account;
import me.braun.identityservice.data.model.Role;
import me.braun.identityservice.data.repository.AccountRepository;
import me.braun.identityservice.data.repository.RoleRepository;
import me.braun.identityservice.exception.RoleNotFoundException;
import me.braun.identityservice.exception.UserAlreadyExistsException;
import me.braun.identityservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public List<Account> getAccounts(){return accountRepository.findAll();}

    public Long createAccount(String firstName, String lastName,
                                 String email, String phone,
                                 String passwordHash ) {
        accountRepository.findByEmailAndPhone(email, phone).ifPresent(account -> {
            throw new UserAlreadyExistsException();
        });

        Account.AccountBuilder builder = Account.builder();
        Account account = builder
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .passwordHash(passwordEncoder.encode(passwordHash))
                .build();
        Account saveAccount = accountRepository.save(account);
        return saveAccount.getId();
    }

    public Account getAccountById(Long id) {

        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            log.info("user: {}", account.get());
            return account.get();
        }
        throw new UserNotFoundException();
    }
    public Account getAccountByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            log.info("user: {}", account.get());
            return account.get();
        }
        throw new UserNotFoundException();
    }

    public Long updateAccount(Long id, String firstName, String lastName,
                                 String email, String phone,
                                 String password ) throws UserNotFoundException,
            RoleNotFoundException {
        final Optional<Account> maybeAccount = accountRepository.findById(id);
        final Account account = maybeAccount.orElseThrow(UserNotFoundException::new);
        if (firstName != null && !firstName.isBlank()) account.setFirstName(firstName);
        if (lastName != null && !lastName.isBlank()) account.setLastName(lastName);
        if (email != null && !email.isBlank()) account.setEmail(email);
        if (phone!= null && !phone.isBlank()) account.setPhone(phone);
        if (password!= null && !password.isBlank()) account.setPasswordHash(passwordEncoder.encode(password));

        Account saveAccount = accountRepository.save(account);
        return saveAccount.getId();


     }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }



}

