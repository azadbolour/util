/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;


public class BasicCredentials {

    public final String username;
    public final String password;

    public BasicCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
