package am.itspace.townrestaurantsrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Async;

@Async
@SpringBootApplication
@EntityScan(basePackages = {"am.itspace.townrestaurantscommon.*"})
@EnableJpaRepositories(basePackages = {"am.itspace.townrestaurantscommon.*"})
@ComponentScan(basePackages = {"am.itspace.townrestaurantsrest.*", "am.itspace.townrestaurantscommon.*"})
public class TownRestaurantsRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TownRestaurantsRestApplication.class, args);
    }

}
