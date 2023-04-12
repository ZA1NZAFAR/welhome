package fr.efrei.backend.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccessTokenValidator {
    private static RestTemplate restTemplate = new RestTemplate();

    private static HttpHeaders getRequestHeaderBearer(String bearerAuthorizationToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(bearerAuthorizationToken);

        return headers;
    }

    public static boolean isTokenValid(String url, String accessToken) {
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>("{}", getRequestHeaderBearer(accessToken)), String.class);
        final String message = "Token is valid";

        // Parse response JSON data
        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject.get("message"));
            return jsonObject.get("message").equals(message);
        } catch (JSONException exception) {
            System.out.println("Couldn't parse JSON: " + exception.getMessage());

            if (response.getBody() == null)
                return false;
            else {
                String value = response.getBody().split(":")[1];
                return value.substring(1, value.length() - 2).equals(message);
            }
        }
    }
}
