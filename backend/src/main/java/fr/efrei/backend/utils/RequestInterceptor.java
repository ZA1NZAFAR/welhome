package fr.efrei.backend.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Value("${authenticationService.url}/checkToken")
    private String URL;
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Request intercepted. Authorization header: " + request.getHeader(AUTHORIZATION));

        if (!AccessTokenValidator.isTokenValid(URL, request.getHeader(AUTHORIZATION).split("\\s")[1]))
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, null, null, null, null);

        return true;
    }
}
