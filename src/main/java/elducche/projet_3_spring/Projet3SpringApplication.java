package elducche.projet_3_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "elducche.projet_3_spring.repository")
public class Projet3SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Projet3SpringApplication.class, args);
	}

}
