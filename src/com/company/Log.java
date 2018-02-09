package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Log {
    private String requestResourceName;
    private int requestDuration;

    public Log() {
    }

    public Log(int requestDuration, String requestResourceName) {
        this.requestDuration = requestDuration;
        this.requestResourceName = requestResourceName;
    }

    public void printLogs(Map<String, Double> logs, int logLimit) {
        if (logs != null) {
            logs
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(logLimit)
                    .forEach(entry -> System.out.printf("ms: %8.1f | Resource name: %s%n", entry.getValue(), entry.getKey()));
        }
    }

    public Map groupLogsByName(List<Log> logs) {
        Map<String, Double> logsGrouped;
        if (logs != null) {
            logsGrouped = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestResourceName,
                            Collectors.averagingInt(Log::getRequestDuration)));
            return logsGrouped;
        }
        return null;
    }

    public List<Log> readFile(String fileName) throws NullPointerException {
        List<Log> logs = null;
        try (Stream<String> in = Files.lines(Paths.get(fileName))) {
            Pattern pattern = Pattern.compile(" ");
            logs = in
                    .map(pattern::split)
                    .map((arr) -> new Log(Integer.parseInt(arr[arr.length - 1]),
                            arr[4]))
                    .collect(Collectors.toList());
            in.close();
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

    private int getRequestDuration() {
        return requestDuration;
    }

    @Override
    public String toString() {
        return requestDuration + " " + requestResourceName;
    }
}