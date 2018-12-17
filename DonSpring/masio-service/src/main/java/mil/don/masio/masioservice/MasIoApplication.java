package mil.don.masio.masioservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;



@EnableScheduling
@EnableDiscoveryClient // eureka support
@EnableFeignClients("mil.don.proxies") // where to find the feign proxies
@SpringBootApplication
public class MasIoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasIoApplication.class, args);
	}
}
