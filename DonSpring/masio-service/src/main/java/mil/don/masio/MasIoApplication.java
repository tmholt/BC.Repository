package mil.don.masio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import mil.don.masio.configuration.AppConfig;




@ComponentScan
@EnableScheduling
@EnableDiscoveryClient // eureka support
@EnableFeignClients("mil.don.proxies") // where to find the feign proxies
@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class MasIoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasIoApplication.class, args);
	}
}
