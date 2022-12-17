package am.itspace.townrestaurantsrest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EntityScan(basePackages = {"am.itspace.townrestaurantscommon.*"})
@EnableJpaRepositories(basePackages = {"am.itspace.townrestaurantscommon.*"})
@ComponentScan(basePackages = {"am.itspace.townrestaurantsrest.*", "am.itspace.townrestaurantscommon.*"})
class TownRestaurantsRestApplicationTests {

    @Test
    void contextLoads() {
    }

}
