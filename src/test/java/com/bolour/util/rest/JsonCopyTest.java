/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JsonCopyTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonCopyTest.class);

    private static SomeData data = new SomeData(1, 2, "abc", new String[] {"a", "b"});

    @Test
    public void testSerializeAndDeserialize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String serialized = mapper.writeValueAsString(data);
        logger.info(serialized);
        SomeData reread = mapper.readValue(serialized, SomeData.class);
        assertEquals(data, reread);
    }

    @Test
    public void testJsonUtilCopy() throws Exception {
        SomeData copy = JsonUtil.copy(data, SomeData.class);
        assertEquals(data, copy);
    }

    static class SomeData {
        public int x;
        public int y;
        public String s;
        public String[] values;

        /**
         * Needed by libraries.
         */
        public SomeData() {}

        public SomeData(int x, int y, String s, String[] values) {
            this.x = x;
            this.y = y;
            this.s = s;
            this.values = values;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SomeData someData = (SomeData) o;

            if (x != someData.x) return false;
            if (y != someData.y) return false;
            if (s != null ? !s.equals(someData.s) : someData.s != null) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(values, someData.values);
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + (s != null ? s.hashCode() : 0);
            result = 31 * result + Arrays.hashCode(values);
            return result;
        }
    }
}
