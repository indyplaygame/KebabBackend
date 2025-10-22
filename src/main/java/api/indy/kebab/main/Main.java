package api.indy.kebab.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "api.indy.kebab.repository")
@EntityScan(basePackages = "api.indy.kebab.model")
@ComponentScan(basePackages = {"api.indy.kebab.auth", "api.indy.kebab.config", "api.indy.kebab.controller", "api.indy.kebab.service", "api.indy.kebab.decorators"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
