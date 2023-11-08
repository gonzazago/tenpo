package gonzalo.tenpo.delivery.interceptors;

import gonzalo.tenpo.domain.models.Trace;
import gonzalo.tenpo.infrastructure.config.HttpRequestWrapper;
import gonzalo.tenpo.infrastructure.config.HttpResponseWrapper;
import gonzalo.tenpo.infrastructure.db.TraceRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "trackingFilter", urlPatterns = "/")
public class TraceInterceptor extends OncePerRequestFilter {

    @Autowired
    private TraceRepository traceRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpRequestWrapper cacheRequest = new HttpRequestWrapper(request);
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response);
        //headers
        Map<String, String> headers = Collections
                .list(cacheRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(name -> name, cacheRequest::getHeader));
        //Request body
        String requestBody = new String(cacheRequest.getContentAsByteArray());
        String method = cacheRequest.getMethod();
        String url = cacheRequest.getRequestURI();
        filterChain.doFilter(cacheRequest, responseWrapper);

        String responseBody = new String(responseWrapper.getContentAsByteArray());
        Integer status = response.getStatus();

        CompletableFuture.runAsync(() -> {
            Trace t = Trace.builder()
                    .url(url)
                    .headers(headers.toString())
                    .method(method)
                    .statusCode(status)
                    .request(requestBody)
                    .response(responseBody)
                    .build();
//            t.setCreateAt(LocalDateTime.now());
            traceRepository.save(t);
        }).whenComplete((result, throwable) -> {
            if (throwable == null) {
                log.info("Trace save success");
            } else {
                log.error("Error try save trace {}", throwable.getMessage());
            }
        });


    }
}
