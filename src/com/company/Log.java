package com.company;

public class Log {
    private String requestResourceName;
    private int requestDuration;
    private String requestTime;

    public Log() {
    }

    public Log(int requestDuration, String requestResourceName, String requestTime) {
        this.requestDuration = requestDuration;
        this.requestResourceName = requestResourceName;
        this.requestTime = requestTime;
    }

    public String getRequestResourceName() {
        return replaceCharactersInString(requestResourceName);
    }

    private String replaceCharactersInString(String initialString) {
        // https://regexr.com/3ke4j
        String pattern = "(=\\d+)|(=CUS\\w+)";
        return initialString.replaceAll(pattern, "");
    }

    public int getRequestHour() {
        return  Integer.parseInt(requestTime.substring(3,5));
    }

    public int getRequestDuration() {
        return requestDuration;
    }

    @Override
    public String toString() {
        return requestTime + " "  + requestDuration + " " + requestResourceName;
    }

}