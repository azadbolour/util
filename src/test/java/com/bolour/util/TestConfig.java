/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util;

import java.util.Arrays;

public class TestConfig {

    public int users = 1;
    public int durationSeconds = 5;
    public int thinkTimeMillis = 100;
    public PersonaConfig[] personas = new PersonaConfig[0];
    public GameConfig gameConfig = new GameConfig();

    public TestConfig() {}

    @Override
    public String toString() {
        return "TestConfig{" +
          "users=" + users +
          ", durationSeconds=" + durationSeconds +
          ", thinkTimeMillis=" + thinkTimeMillis +
          ", personas=" + Arrays.toString(personas) +
          ", gameConfig=" + gameConfig +
          '}';
    }

    static class PersonaConfig {
        public String name = "";
        public int weight = 1;

        public PersonaConfig() {}

        @Override
        public String toString() {
            return "PersonaConfig{" +
              "name='" + name + '\'' +
              ", weight=" + weight +
              '}';
        }
    }

    static class GameConfig {
        public String gameServerUrl = "";
        public float gameEndProbability = .05f;

        public GameConfig() {}

        @Override
        public String toString() {
            return "GameConfig{" +
              "gameServerUrl='" + gameServerUrl + '\'' +
              ", gameEndProbability=" + gameEndProbability +
              '}';
        }
    }
}
