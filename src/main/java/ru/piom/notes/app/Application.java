// tag::runner[]
package ru.piom.notes.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.piom.notes.entities.Account;
import ru.piom.notes.entities.Note;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;

import java.util.Arrays;

/**
 * Created by Alexandr Korkin on 3/5/2016.
 */

@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
@EntityScan(basePackageClasses = Note.class)
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           NoteRepository noteRepository) {
        return (evt) -> Arrays.asList(
                "alex".split(","))
                .forEach(
                        a -> {
                            Account account = accountRepository.save(new Account(a,
                                    "password"));
                            noteRepository.save(new Note(account,
                                    "Note description"));
                        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


