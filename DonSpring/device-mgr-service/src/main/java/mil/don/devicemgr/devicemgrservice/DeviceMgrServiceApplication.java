package mil.don.devicemgr.devicemgrservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import mil.don.devicemgr.devicemgrservice.configuration.AppConfig;

@SpringBootApplication
@EnableFeignClients("mil.don.proxies") // where to find the feign proxies

// we can do this OR define our appconfig class as a component (which is what I did)
//@EnableConfigurationProperties(AppConfig.class) // binding configuration server properties to our config POJO

@EnableDiscoveryClient
@EnableScheduling
public class DeviceMgrServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeviceMgrServiceApplication.class, args);
	}
}
