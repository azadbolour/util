/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;


import static java.lang.String.format;

public class HttpUtil {

    public static final String HTTP = "http";
    public static final String HTTPS = "https";

    private HttpUtil() {}

    public static String makeUrl(String protocol, String host, int port) {
        return format("%s://%s:%s", protocol, host, port);
    }

    public static String makeUrl(String host, int port) {
        return makeUrl(HTTP, host, port);
    }
}
