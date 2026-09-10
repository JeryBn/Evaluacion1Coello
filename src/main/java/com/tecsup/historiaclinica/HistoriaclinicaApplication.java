package com.tecsup.historiaclinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tecsup.historiaclinica", "com.coello.historiaclinica.atencionmedica"})
@EntityScan(basePackages = {
        "com.tecsup.historiaclinica.model",
        "com.tecsup.historiaclinica.antecedentes.entity",
        "com.coello.historiaclinica.atencionmedica.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.tecsup.historiaclinica.repository",
        "com.tecsup.historiaclinica.antecedentes.repository",
        "com.coello.historiaclinica.atencionmedica.repository"
})
public class HistoriaclinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoriaclinicaApplication.class, args);
    }

}
