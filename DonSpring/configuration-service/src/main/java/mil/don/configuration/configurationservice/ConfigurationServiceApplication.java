package mil.don.configuration.configurationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigServer
@EnableAutoConfiguration
@SpringBootApplication
public class ConfigurationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationServiceApplication.class, args);
    }
}