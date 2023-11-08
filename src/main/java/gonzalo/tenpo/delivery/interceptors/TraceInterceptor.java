package gonzalo.tenpo.delivery.interceptors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@WebFilter(filterName = "trackingFilter", urlPatterns = "/")
public class TraceInterceptor extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response);
        //headers
        Map<String, String> headers = Collections
                .list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(name -> name, request::getHeader));
        //Request body
        String requestBody = new BufferedReader(new InputStreamReader(request.getInputStream()))
                .lines().reduce("", (acc, line) -> acc + line);
        String method = request.getMethod();

        log.info("Headers: {}", headers.toString());
        log.info("body: {}", requestBody);
        log.info("method {}", method);
        filterChain.doFilter(request,response);

        String responseBody = responseWrapper.getCapturedResponseBody();
        Integer status = response.getStatus();

        log.info("response: {}", responseBody);
        log.info("status {}", status);


    }
}
