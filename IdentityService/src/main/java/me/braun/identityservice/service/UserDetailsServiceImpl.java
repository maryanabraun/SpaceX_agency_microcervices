package me.braun.identityservice.service;

import lombok.RequiredArgsConstructor;


import me.braun.identityservice.config.security.UserPrincipal;
import me.braun.identityservice.data.model.Account;
import me.braun.identityservice.data.repository.AccountRepository;
import me.braun.identityservice.exception.UserNotFoundException;
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
