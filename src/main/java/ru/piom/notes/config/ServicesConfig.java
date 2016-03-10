package ru.piom.notes.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.piom.notes.StorageService;

/**
 * Created by Alex on 09.03.2016.
 */
@Configuration
@ComponentScan(basePackageClasses = StorageService.class)
public class ServicesConfig {

}
