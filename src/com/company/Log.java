package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void printSortedLogs(Map<String, Double> logs, int logLimit) {
        if (logs != null) {
            logs
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(logLimit)
                    .forEach(entry -> System.out.printf("ms: %8.1f | Resource name: %s%n", entry.getValue(), entry.getKey()));
        }
    }

    public Map<Integer, Long> countRequestsBerHour(List<Log> logs){
        Map<Integer, Long> logsGrouped = null;
        if (logs !=null){
            logsGrouped = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestHour, Collectors.counting()));
        }
        return  logsGrouped;
    }

    public Map<String, Double> groupLogsByName(List<Log> logs) {
        Map<String, Double> logsGrouped = null;
        if (logs != null) {
            logsGrouped = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestResourceName,
                            Collectors.averagingInt(Log::getRequestDuration)));
        }
        return logsGrouped;
    }

    public List<Log> readFile(String fileName) {
        List<Log> logs = null;
        try (Stream<String> in = Files.lines(Paths.get(fileName))) {
            logs = in
                    .map(string -> string.split(" "))
                    .map(arr -> new Log(
                            Integer.parseInt(arr[arr.length - 1]),
                            arr[4],
                            arr[1]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        return logs;
    }

    private String getRequestResourceName() {
        return replaceCharactersInString(requestResourceName);
    }

    private String replaceCharactersInString(String initialString) {
        // https://regexr.com/3ke4j
        String pattern = "(=\\d+)|(=CUS\\w+)";
        return initialString.replaceAll(pattern, "");
    }

    private int getRequestHour() {
        return  Integer.parseInt(requestTime.substring(3,5));
    }

    private int getRequestDuration() {
        return requestDuration;
    }

    @Override
    public String toString() {
        return requestTime + " "  + requestDuration + " " + requestResourceName;
    }

}