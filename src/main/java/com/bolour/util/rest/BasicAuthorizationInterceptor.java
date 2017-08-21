/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;


// import com.bolour.sandbox.boardgame.core.util.BasicCredentials;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.util.Base64Utils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BasicAuthorizationInterceptor implements ClientHttpRequestInterceptor {

    private final String username;
    private final String password;

    public BasicAuthorizationInterceptor(BasicCredentials credentials) {
        this.username = credentials.username;
        this.password = credentials.password;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String authHeaderValue = basicAuthHeaderValue();
        request.getHeaders().add(AUTHORIZATION, authHeaderValue);
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }

    private String basicAuthHeaderValue() {
        String token = Base64Utils.encodeToString((this.username + ":" + this.password).getBytes(UTF_8));
        String value = "Basic " + token;
        return value;
    }
}
