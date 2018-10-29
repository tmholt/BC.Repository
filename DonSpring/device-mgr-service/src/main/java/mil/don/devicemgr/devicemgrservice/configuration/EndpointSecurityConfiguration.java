package mil.don.devicemgr.devicemgrservice.configuration;



import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import mil.don.devicemgr.devicemgrservice.DeviceMgr;


@Configuration
public class EndpointSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // turns on access to all endpoints
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                        .anyRequest().permitAll();
    }


}
