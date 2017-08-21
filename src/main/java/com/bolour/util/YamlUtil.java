/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

import static java.lang.String.format;

/**
 * Utilities for Yaml.
 */
public class YamlUtil {

    private YamlUtil() {}

    /**
     * Read a yaml string into an immutable class.
     *
     * An immutable class has all its instance variables marked as 'final',
     * and it has a main constructor that includes all the instance
     * variables not initialized to constants. Similarly for the
     * transitive closure of the classes of its instance variables.
     *
     * To deserialize a yaml string into such a class you need to
     * (1) annotate its main constructor and the main
     * constructors of its transitive instance variables
     * with '@JsonCreator', and (2) annotate corresponding constructor
     * parameters with '@JsonProperty("paramName")'.
     *
     * See YamlTest in this package for an example.
     */
    public static <Immutable> Immutable yamlStringToImmutable(String yamlString, Class<Immutable> immutableClass) {
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(yamlString);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String serialized = mapper.writeValueAsString(map);
            Immutable immutable = mapper.readValue(serialized, immutableClass);
            return immutable;
        } catch (IOException ex) {
            throw new RuntimeException(format("error in mapping yaml string: %s", yamlString), ex);
        }
    }
}
