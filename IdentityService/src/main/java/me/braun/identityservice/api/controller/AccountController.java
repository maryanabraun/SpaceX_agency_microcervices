package me.braun.identityservice.api.controller;


import me.braun.identityservice.api.dto.AccountDto;
import me.braun.identityservice.api.dto.RegistrationDto;
import me.braun.identityservice.data.model.Account;
import me.braun.identityservice.exception.UserAlreadyExistsException;
import me.braun.identityservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public final class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping
    public ResponseEntity<List<Account>> index() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> show(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping(params = "email")
    public ResponseEntity<Account> showByEmail(@RequestParam String email) {
        return ResponseEntity.ok(accountService.getAccountByEmail(email));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody RegistrationDto registrationDto)
            throws UserAlreadyExistsException {
        final String firstName = registrationDto.getFirstName();
        final String lastName = registrationDto.getLastName();
        final String email = registrationDto.getEmail();
        final String phone = registrationDto.getPhone();
        final String password = registrationDto.getPassword();

        final Long id = accountService.createAccount(firstName, lastName, email, phone, password);
        final String location = String.format("/accounts/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @Valid @RequestBody RegistrationDto registrationDto) {
        final String firstName = registrationDto.getFirstName();
        final String lastLame = registrationDto.getLastName();
        final String email = registrationDto.getEmail();
        final String phone = registrationDto.getPhone();
        final String password = registrationDto.getPassword();
        try {
            accountService.updateAccount(id, firstName, lastLame, email, phone, password);
            final String location = String.format("/accounts/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        accountService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }
}

