package gonzalo.tenpo.delivery.interceptors;

import gonzalo.tenpo.delivery.exceptions.RateLimitException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RateLimitInterceptor implements HandlerInterceptor {
    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final Bandwidth limit = Bandwidth.simple(3, Duration.ofMinutes(1)); // 10 peticiones por minuto



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ServletException, IOException {
        String bucketKey = request.getServletPath();


        Bucket bucket = buckets.computeIfAbsent(bucketKey, k -> Bucket4j.builder().addLimit(limit).build());
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            return true;
        } else {
          throw new RateLimitException("Too many request");
        }
    }


}
