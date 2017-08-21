/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;

// import com.bolour.sandbox.boardgame.core.util.BasicCredentials;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public class ClientRestHelper {

    private ClientRestHelper() {}

    /**
     * If the body of the http response is empty, this method will return null.
     */
    public static <T> T getResponseBody(ResponseEntity<T> responseEntity, String message) {
        HttpStatus status = responseEntity.getStatusCode();
        switch(status) {
            case OK:
                return responseEntity.getBody();
            case NO_CONTENT:
                return null;
            case FOUND:
                String redirectLocation = redirectLocation(responseEntity);
                throw new RuntimeRestClientException(
                  format("%s - rest call was redirected to %s", message, redirectLocation), status);
            default:
                throw new RuntimeRestClientException(message, status);
        }
    }

    public static HttpHeaders createPostHeaders(String returnMediaType, MediaType contentType) {
        HttpHeaders headers = createAcceptHeaders(returnMediaType);
        headers.setContentType(contentType);
        return headers;
    }

    public static HttpHeaders createJsonGetHeaders() {
        return createAcceptHeaders(MediaType.APPLICATION_JSON_VALUE);
    }

    public static HttpHeaders createJsonPostHeaders() {
        return createPostHeaders(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON);
    }

    public static HttpHeaders createAcceptHeaders(String acceptMediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, acceptMediaType);
        headers.add(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE);
        return headers;
    }

    private static String redirectLocation(ResponseEntity responseEntity) {
        HttpHeaders headers = responseEntity.getHeaders();
        if (headers == null)
            return null;
        URI location = headers.getLocation();
        if (location == null)
            return null;
        return location.getPath();
    }

    public static RestTemplate createCredentialedRestTemplate(BasicCredentials credentials) {

        if (credentials.username == null)
            throw new IllegalArgumentException("unable to create credentialed rest template - no username given");

        RestTemplate template = new RestTemplate();
        addBasicAuthenticationInterceptorToTemplate(template, credentials);
        addErrorHandlerToTemplate(template);
        return template;
    }

    private static void addErrorHandlerToTemplate(RestTemplate template) {
        template.setErrorHandler(new DefaultResponseErrorHandler());
    }

    private static void addBasicAuthenticationInterceptorToTemplate(RestTemplate template, BasicCredentials credentials) {
        ClientHttpRequestFactory requestFactory = template.getRequestFactory();
        BasicAuthorizationInterceptor authInterceptor = new BasicAuthorizationInterceptor(credentials);
        List<ClientHttpRequestInterceptor> interceptors = Collections.singletonList(authInterceptor);
        InterceptingClientHttpRequestFactory interceptingRequestFactory =
          new InterceptingClientHttpRequestFactory(requestFactory, interceptors);
        template.setRequestFactory(interceptingRequestFactory);
    }
}
