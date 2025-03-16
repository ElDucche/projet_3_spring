package elducche.projet_3_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "elducche.projet_3_spring.repository")
@ComponentScan(basePackages = "elducche.projet_3_spring")
public class Projet3SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Projet3SpringApplication.class, args);
	}

}
