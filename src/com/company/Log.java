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

    public String getRequestResourceName() {
        return replaceCharactersInString(requestResourceName);
    }

    private String replaceCharactersInString(String string) {
        // https://regexr.com/3ke4j
        String pattern = "(=\\d+)|(=CUS\\w+)";
        return string.replaceAll(pattern, "");
    }

    public int getRequestDuration() {
        return requestDuration;
    }

    @Override
    public String toString() {
        return requestDuration + " " + requestResourceName;
    }
}