package com.pos.entities.repositories;

import com.pos.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByUsernameAndActiveTrue(String username);

    Optional<Account> findByIdAndActiveTrue(String id);

    boolean existsAccountByUsernameAndActiveTrue(String username);

}
