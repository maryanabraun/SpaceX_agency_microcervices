package me.braun.authentityservice.data.repository;

import me.braun.authentityservice.data.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmailAndPhone(String email, String phone);
    Optional<Account> findByEmail(String email);
}
