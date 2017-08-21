/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.lang.String.format;

public class JsonUtil {

    private JsonUtil() {}

    public static <From, To> To copy(From from, Class<To> toClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String serialized = mapper.writeValueAsString(from);
            To to = mapper.readValue(serialized, toClass);
            return to;
        } catch (Exception ex) {
            throw new RuntimeException(format("unable to copy: %s to class: %s", from, toClass), ex);
        }
    }
}
