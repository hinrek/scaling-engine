package com.company;

public class Log {
    private String date;
    private String timestamp;
    private String threadId;
    private String optionalContext;
    private String uriAndQueryString;
    private String requestedResource;
    private int dataPayloadElements;
    private int requestDuration;

    public Log() {
    }


    public Log(int requestDuration, String requestedResource) {
        this.requestDuration = requestDuration;
        this.requestedResource = requestedResource;
    }

    @Override
    public String toString() {
        return requestDuration + " " + requestedResource;
    }

    public String getRequestedResource() {
        return requestedResource;
    }

    public int getRequestDuration() {
        return requestDuration;
    }
}