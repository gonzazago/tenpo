package gonzalo.tenpo.delivery.interceptors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "trackingFilter", urlPatterns = "/")
public class TraceInterceptor extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper cacheRequest = new ContentCachingRequestWrapper(request);
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response);
        //headers
        Map<String, String> headers = Collections
                .list(cacheRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(name -> name, cacheRequest::getHeader));
        //Request body
        String requestBody = new String(responseWrapper.getContentAsByteArray());
        String method = cacheRequest.getMethod();

        log.info("Headers: {}", headers);
            log.info("body: {}", requestBody);
        log.info("method {}", method);
        filterChain.doFilter(cacheRequest,responseWrapper);

        String responseBody = new String(responseWrapper.getContentAsByteArray());
        Integer status = response.getStatus();

        log.info("response: {}", responseBody);
        log.info("status {}", status);


    }
}
