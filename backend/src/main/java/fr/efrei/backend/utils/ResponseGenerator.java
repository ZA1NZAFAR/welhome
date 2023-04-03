package fr.efrei.backend.utils;

import fr.efrei.backend.entities.Profile;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
public class ResponseGenerator<T> {
    public ResponseEntity<T> execute(String requestUrl, HttpMethod method, Class<T> dto) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        log.info("Sending HTTP " + method.name() + " request to: " + requestUrl);

        // Configure HTTP headers sent upon request
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Create HttpEntity, execute Http Request, create ResponseEntity serialize JSON body
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<T> result = restTemplate.exchange(requestUrl, method, entity, dto);
        return result;
    }
}
