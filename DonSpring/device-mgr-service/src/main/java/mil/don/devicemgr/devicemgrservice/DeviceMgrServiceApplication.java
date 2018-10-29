package mil.don.devicemgr.devicemgrservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients("mil.don.proxies") // where to find the proxies
@EnableDiscoveryClient
@EnableScheduling
public class DeviceMgrServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeviceMgrServiceApplication.class, args);
	}
}
