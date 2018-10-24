package mil.don.devicemgr.devicemgrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableFeignClients // ("mil.don.devicemgr.devicemgrservice") // don't need - not sure why this was here // now i understand
@EnableFeignClients("mil.don.proxies")
@EnableDiscoveryClient
public class DeviceMgrServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeviceMgrServiceApplication.class, args);
	}
}
