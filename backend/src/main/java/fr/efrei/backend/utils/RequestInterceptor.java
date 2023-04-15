package fr.efrei.backend.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Value("${authenticationService.url}/checkToken")
    private String URL;
    private static final String AUTHORIZATION = "Authorization";
    private static final Map<String, String> WHITELIST = Map.ofEntries(
            Map.entry("/api/properties", "GET"),
            Map.entry("/api/reviews", "GET")
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Waiving the authentication requirement: handling exclusions of certain endpoints with specific HTTP method type
        if (WHITELIST.entrySet().stream().anyMatch(entry -> request.getServletPath().startsWith(entry.getKey()) && entry.getValue().equals(request.getMethod())))
            return true;

        if (request.getHeader(AUTHORIZATION) != null)
            log.info("Request intercepted. Authorization header: " + request.getHeader(AUTHORIZATION));

        if (request.getHeader(AUTHORIZATION) == null || !AccessTokenValidator.isTokenValid(URL, request.getHeader(AUTHORIZATION).split("\\s")[1]))
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, null, null, null, null);

        return true;
    }
}
