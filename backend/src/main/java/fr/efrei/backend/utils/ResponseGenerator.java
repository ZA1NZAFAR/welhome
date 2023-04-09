package fr.efrei.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Scope("singleton")
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
        ResponseEntity<T> result = null;

        try {
            // Executes GET and DELETE requests
            if (body == null) {
                HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
                result = restTemplate.exchange(requestUrl, method, entity, responseType);
                // Executes POST and PUT requests
            } else {
                HttpEntity<T> entity = new HttpEntity<>(body, headers);
                result = restTemplate.exchange(requestUrl, method, entity, responseType);
            }
        } catch(HttpClientErrorException exception) {
            // Microservice doesn't create a resource if it doesn't exist upon making PUT request, correct it on backend side
            if (method == HttpMethod.PUT) {
                HttpEntity<T> entity = new HttpEntity<>(body, headers);
                // Trim out id part used to locate the resource upon making PUT request
                result = restTemplate.exchange(requestUrl.substring(0, requestUrl.lastIndexOf("/")), HttpMethod.POST, entity, responseType);
                result = new ResponseEntity<>(result.getBody(), HttpStatus.CREATED);
                return result;
            }
        } catch (Exception exception) {
            // Return generic status code to indicate error, in case if Request failed and/or server couldn't process the request
            log.error("Request failed: ", exception.getMessage());
            result = new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
            return result;
        }

        // If result is still null, it means that the request has either failed or has succeeded, but has been formatted improperly
        if (result == null) {
            result = new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
            return result;
        }

        // Sending out proper response code in ResponseEntity
        if (method == HttpMethod.DELETE && result.getStatusCode() == HttpStatus.OK)
            result = new ResponseEntity<>(result.getBody(), result.getStatusCode());
        // Send out correct status code (201: created) in response to POST request
        else if (method == HttpMethod.POST && result.getStatusCode() == HttpStatus.OK)
            result = new ResponseEntity<>(result.getBody(), HttpStatus.CREATED);
        // Empty List<T> ~ body or not found resource should be followed back with 404 status code according to this discussion:
        // https://stackoverflow.com/questions/11746894/what-is-the-proper-rest-response-code-for-a-valid-request-but-an-empty-data
        else if ((result.getBody() == null || (result.getBody() instanceof List<?> && ((List<?>) result.getBody()).isEmpty())))
            result = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            result = new ResponseEntity<>(result.getBody(), result.getStatusCode());

        return result;
    }
}
