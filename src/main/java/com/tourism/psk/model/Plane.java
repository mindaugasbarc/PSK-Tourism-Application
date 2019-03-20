package com.tourism.psk.model;

public class Plane extends Transportation {

    private final String from;
    private final String to;

    public Plane(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String transportationInfo() {
        return "flying by plane from " + from + " to " + to;
    }
}
