package ua.foxminded;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("ua.foxminded")
@EnableJpaRepositories("ua.foxminded.repository")
public class AppConfig {

	
}
