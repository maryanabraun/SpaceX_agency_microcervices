package me.braun.authentityservice.service;

import me.braun.authentityservice.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import me.braun.authentityservice.data.model.Account;
import me.braun.authentityservice.data.repository.AccountRepository;

import me.braun.authentityservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        Account account = accountRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return new UserPrincipal(account);
    }
}
