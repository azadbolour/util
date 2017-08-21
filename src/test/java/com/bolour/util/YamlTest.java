/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util;

import com.bolour.util.rest.JsonUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.bolour.util.FileUtil.readResourceAsString;
import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

public class YamlTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlTest.class);
    private static final String CONFIG_RESOURCE = "com/bolour/util/test-config.yml";

    @Test
    public void testReadYaml() throws Exception {
        String configString = readResourceAsString(this.getClass(), CONFIG_RESOURCE);
        Yaml yaml = new Yaml(new Constructor(TestConfig.class));
        TestConfig config = (TestConfig) yaml.load(configString);
        LOGGER.info(config.toString());

        assertTrue(config.personas.length > 0);
    }

    @Test
    public void testReadYamlManual() throws Exception {
        String configString = readResourceAsString(this.getClass(), CONFIG_RESOURCE);
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(configString);
        LOGGER.info(map.toString());
        int users = (int) map.get("users");
        int durationSeconds = (int) map.get("durationSeconds");
        int thinkTimeMillis = (int) map.get("thinkTimeMillis");
        LOGGER.info(format("users: %s", users));

        Object personasObj = map.get("personas");
        LOGGER.info(personasObj.getClass().getName());

        List<Map<String, Object>> personaMaps = (List) personasObj;
        LOGGER.info(personaMaps.get(0).getClass().getName());
    }

    @Test
    public void testReadImmutable() throws Exception {
        String configString = "x: 20\ns: 'value'\nchildren:\n  - x: 1\n    y: 2";
        LOGGER.info(configString);
        Yaml yaml = new Yaml(new Constructor(Mutable.class));
        Mutable config = (Mutable) yaml.load(configString);
        LOGGER.info(config.toString());
        Immutable immutable = JsonUtil.copy(config, Immutable.class);
        LOGGER.info(immutable.toString());
    }

    @Test
    public void testReadImmutableViaJackson() throws Exception {
        String configString = "x: 20\ns: 'value'\nchildren:\n  - x: 1\n    y: 2";
        LOGGER.info(configString);
        Immutable immutable = YamlUtil.yamlStringToImmutable(configString, Immutable.class);
        LOGGER.info(format("immutable: %s", immutable));
    }

    static class ImmutableChild {
        public final int x;
        public final int y;

        @JsonCreator
        public ImmutableChild(
          @JsonProperty("x") int x,
          @JsonProperty("y") int y
        ) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "ImmutableChild{" +
              "x=" + x +
              ", y=" + y +
              '}';
        }
    }

    static class MutableChild {
        public int x;
        public int y;

        public MutableChild(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public MutableChild() {}

        @Override
        public String toString() {
            return "MutableChild{" +
              "x=" + x +
              ", y=" + y +
              '}';
        }
    }

    static class Immutable {
        public final int x;
        public final String s;
        public final List<ImmutableChild> children;

        @JsonCreator
        public Immutable(
          @JsonProperty("x") int x,
          @JsonProperty("s") String s,
          @JsonProperty("children") List<ImmutableChild> children
        ) {
            this.x = x;
            this.s = s;
            this.children = Collections.unmodifiableList(children);
        }

        @Override
        public String toString() {
            return "Immutable{" +
              "x=" + x +
              ", s='" + s + '\'' +
              ", children=" + children +
              '}';
        }
    }

    static class Mutable {
        public int x;
        public String s;
        public List<MutableChild> children;

        public Mutable() {}

        @JsonCreator
        public Mutable(
          @JsonProperty("x") int x,
          @JsonProperty("s") String s,
          @JsonProperty("children") List<MutableChild> children

        ) {
            this.x = x;
            this.s = s;
            this.children = children;
        }

        @Override
        public String toString() {
            return "Mutable{" +
              "x=" + x +
              ", s='" + s + '\'' +
              ", children=" + children +
              '}';
        }
    }
}
