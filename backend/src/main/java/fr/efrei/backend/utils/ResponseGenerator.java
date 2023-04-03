package fr.efrei.backend.utils;

import fr.efrei.backend.entities.Profile;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class ResponseGenerator<T> {
    public ResponseEntity<T> execute(String requestUrl, HttpMethod method, Class<T> dto) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(requestUrl);

        // Configure HTTP headers sent upon request
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Create HttpEntity, execute Http Request, create ResponseEntity serialize JSON body
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<T> result = restTemplate.exchange(requestUrl, method, entity, dto);
        return result;
    }
}
