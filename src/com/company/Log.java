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

    public Log(){
    }

    public Log(String date, String timestamp, String threadId, String optionalContext, String uriAndQueryString, String requestedResource, int dataPayloadElements, int requestDuration){
        this.date = date;
        this.timestamp = timestamp;
        this.threadId = threadId;
        this.optionalContext = optionalContext;
        this.uriAndQueryString = uriAndQueryString;
        this.requestedResource = requestedResource;
        this.dataPayloadElements = dataPayloadElements;
        this.requestDuration = requestDuration;
    }
}



/*
        date
        timestamp
        thread-id (in brackets)
        optional user context (in square brackets)
        URI + query string
        string "in"
        request duration in milliseconds


requested resource name (one string)
data payload elements for resource (0..n elements)
        */