package com.dev.petbackend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication
public class PetBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetBackendApplication.class, args);
    }

}
