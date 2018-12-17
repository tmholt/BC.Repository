package mil.don.devicemgr.devicemgrservice.configuration;


import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class EndpointSecurityConfiguration extends WebSecurityConfigurerAdapter {
    // turns on access to all endpoints. we need this in order to
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                        .anyRequest().permitAll();
    }
}
