package gonzalo.tenpo.infrastructure.config;

import gonzalo.tenpo.delivery.interceptors.RateLimitInterceptor;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
public class Config implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Bandwidth limit = Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(1)));
        registry.addInterceptor(new RateLimitInterceptor(limit))
                .addPathPatterns("/api/v1/sum");
    }
}
