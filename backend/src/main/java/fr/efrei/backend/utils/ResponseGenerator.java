package fr.efrei.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
public class ResponseGenerator<T> {
    public ResponseEntity<T> buildRequest(String requestUrl, HttpMethod method, ParameterizedTypeReference<T> responseType) {
        return buildRequest(requestUrl, method, null, responseType);
    }

    public ResponseEntity<T> buildRequest(String requestUrl, HttpMethod method, T body, ParameterizedTypeReference<T> responseType) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Log HTTP request trace
        try {
            log.info("Sending HTTP " + method.name() + " request to: " + requestUrl + ((body != null)
                    ? "\nBody: " + new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(body) + "\nEntity: " + body : ""));
        } catch (JsonProcessingException e) {
            log.error("Couldn't parse JSON: " + e.getMessage());
            log.info("Sending HTTP " + method.name() + " request to: " + requestUrl + "\nEntity: " + body);
        }

        // Configure HTTP headers sent upon request
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Create HttpEntity, execute Http Request, create ResponseEntity serialize JSON body
        ResponseEntity<T> result;

        // Executes GET and DELETE requests
        if (body == null) {
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            result = restTemplate.exchange(requestUrl, method, entity, responseType);
        // Executes POST and PUT requests
        } else {
            HttpEntity<T> entity = new HttpEntity<>(body, headers);
            result = restTemplate.exchange(requestUrl, method, entity, responseType);
        }

        return result;
    }
}
