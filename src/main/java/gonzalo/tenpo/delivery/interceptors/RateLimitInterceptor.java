package gonzalo.tenpo.delivery.interceptors;

import gonzalo.tenpo.delivery.exceptions.RateLimitException;
import io.github.bucket4j.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.Setter;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Data
@Setter
public class RateLimitInterceptor implements HandlerInterceptor {
    private  Bucket bucket;

    public RateLimitInterceptor(Bandwidth limit) {
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ServletException, IOException {
        String bucketKey = request.getServletPath();

        if(request.getMethod().equals("POST")){
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
            if (probe.isConsumed()) {
                return true;
            } else {
                throw new RateLimitException("Too many request");
            }
        }
        else {
            return true;
        }

    }


}
