package am.itspace.townrestaurantsweb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@RequiredArgsConstructor
@EntityScan(basePackages = {"am.itspace.townrestaurantscommon.*"})
@EnableJpaRepositories(basePackages = {"am.itspace.townrestaurantscommon.*"})
@ComponentScan(basePackages = {
        "am.itspace.townrestaurantsweb.*",
        "am.itspace.townrestaurantscommon.*"
})
public class TownRestaurantsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TownRestaurantsWebApplication.class, args);
    }
}
