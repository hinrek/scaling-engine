package com.company;

public class Log {
    private String requestResourceName;
    private int requestDuration;

    public Log() {
    }


    public Log(int requestDuration, String requestResourceName) {
        this.requestDuration = requestDuration;
        this.requestResourceName = requestResourceName;
    }

    private String replaceCharactersInRequestResourceName(String requestResourceName) {
        // https://regexr.com/3ke4j
        String pattern = "(=\\d+)|(=CUS\\w+)";
        return requestResourceName.replaceAll(pattern, "");
    }

    public String getRequestResourceName() {
        return replaceCharactersInRequestResourceName(requestResourceName);
    }

    public int getRequestDuration() {
        return requestDuration;
    }

    @Override
    public String toString() {
        return requestDuration + " " + requestResourceName;
    }
}