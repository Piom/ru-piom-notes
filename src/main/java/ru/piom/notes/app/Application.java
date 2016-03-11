// tag::runner[]
package ru.piom.notes.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.piom.notes.config.ServicesConfig;
import ru.piom.notes.entities.Account;
import ru.piom.notes.entities.Note;
import ru.piom.notes.entities.Tag;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;
import ru.piom.notes.repository.TagRepository;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Alexandr Korkin on 3/5/2016.
 */

@SpringBootApplication
@ComponentScan(basePackageClasses = ServicesConfig.class)
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           NoteRepository noteRepository,
                           TagRepository tagRepository) {
        return (evt) -> Arrays.asList(
                "alex".split(","))
                .forEach(
                        a -> {
                            Optional<Tag> tag = tagRepository.findByName("REST");
                            Account account = accountRepository.save(new Account(a,
                                    "password"));
                            noteRepository.save(new Note(account,
                                    "Note title", "Note description", null));
                        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


