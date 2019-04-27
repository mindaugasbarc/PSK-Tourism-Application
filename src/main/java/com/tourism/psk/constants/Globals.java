package com.tourism.psk.constants;

import java.util.ArrayList;
import java.util.List;

public class Globals {

    public static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";

    public static List<String> CORS_ALLOWED_METHODS = new ArrayList<String>() {{
        add("HEAD");
        add("GET");
        add("POST");
        add("PUT");
        add("DELETE");
        add("PATCH");
    }};

    public static List<String> CORS_ALLOWED_HEADERS = new ArrayList<String>() {{
        add("Access-Control-Allow-Origin");
        add("Origin");
        add(Globals.ACCESS_TOKEN_HEADER_NAME);
        add("Content-Type");
    }};

}
