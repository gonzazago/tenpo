package gonzalo.tenpo.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tenpo Challenge - Rest api With Spring boot")
                        .description("Challenge for tenpo")
                        .license(new License().name("Gonzalo Sergio Zago")));
    }
}
