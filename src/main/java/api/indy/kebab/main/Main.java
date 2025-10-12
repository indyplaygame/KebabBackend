package api.indy.kebab.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "api.indy.kebab.model")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
