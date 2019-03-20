package com.tourism.psk.model;

public final class Car extends Transportation {

    private final String from;
    private final String to;

    public Car(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public final String transportationInfo() {
        return "Transportation info: by car from " + from + " to" + to;
    }


}
