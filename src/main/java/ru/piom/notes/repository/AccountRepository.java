package ru.piom.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.piom.notes.entities.Account;

import java.util.Optional;

/**
 * Created by Alexandr Korkin on 3/5/2016.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
