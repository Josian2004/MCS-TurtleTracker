package com.mcsturtletrackerbackend.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class MCSAuthFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        ArrayList<String> authorizedEndpoints = new ArrayList<>(Arrays.asList(
                "/mcs/messages/services/send",
                "/ws/mcs/"
        ));
        for (String authorizedEndpoint:authorizedEndpoints) {
            if (request.getRequestURI().startsWith(request.getContextPath() + authorizedEndpoint)) {
                return false;
            }
        }
        return true;
    }




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String username;
        String authToken;
        try {
            username = Objects.requireNonNull(request.getHeader("username"));
            authToken = Objects.requireNonNull(request.getHeader("authToken"));
        } catch (NullPointerException nullPointerException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        ResponseEntity<String> httpResponse;
        try {
            String url = "https://auth.mcsynergy.nl/validate/raw";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("username", username);
            headers.add("authToken", authToken);
            HttpEntity httpRequest = new HttpEntity(headers);
            httpResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpRequest,
                    String.class
            );

            if (httpResponse.getStatusCode() == HttpStatus.OK) {
                chain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (HttpClientErrorException.Unauthorized  exception){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
