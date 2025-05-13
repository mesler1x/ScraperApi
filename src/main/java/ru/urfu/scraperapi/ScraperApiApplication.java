package ru.urfu.scraperapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ScraperApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScraperApiApplication.class, args);
    }

}
