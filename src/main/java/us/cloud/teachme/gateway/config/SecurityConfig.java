package us.cloud.teachme.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.cloud.teachme.authutils.config.AuthSecurityConfiguration;

@Configuration
@Import(AuthSecurityConfiguration.class)
public class SecurityConfig {

}
