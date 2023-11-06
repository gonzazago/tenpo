package gonzalo.tenpo.infrastructure.rest;


import gonzalo.tenpo.infrastructure.rest.exceptions.ApiException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public abstract class RestClient<T> {


    @Autowired
    private RetryTemplate retryTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    public T executeWithRetry(String url, RetryCallback<T, Exception> callback) throws Exception {
            return retryTemplate.execute((RetryCallback<T, Exception>) callback::doWithRetry);

    }

    public T getWitRetry(String url, Class<T> responseType) throws Exception {
        return executeWithRetry(url, context -> restTemplate.getForObject(url, responseType));
    }

    public ApiException handleException(String clientName, HttpStatusCode status, HttpStatusCodeException e){
        if (status.equals(NOT_FOUND)) {
            return new ApiException(clientName, "NOT_FOUND", "NOT_FOUND", status, LocalDateTime.now(), e.getMessage());
        } else if (status.equals(BAD_REQUEST)) {
            return new ApiException(clientName, "BAD_REQUEST", "BAD_REQUEST", status, LocalDateTime.now(), e.getMessage());
        }
        return new ApiException(clientName, "INTERNAL_ERROR", "INTERNAL_ERROR", status, LocalDateTime.now(), e.getMessage());
    }
}
