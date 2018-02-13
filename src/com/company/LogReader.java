package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogReader {

    public static void printSortedLogs(Map<String, Double> logs, int logLimit) {
        if (logs != null) {
            logs
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(logLimit)
                    .forEach(entry -> System.out.printf("%8.1fms | Resource name: %s%n", entry.getValue(), entry.getKey()));
        }
    }

    public static Map<Integer, Long> countRequestsBerHour(String fileName) {
        List<Log> logs = readFile(fileName);
        Map<Integer, Long> logsGrouped = null;
        if (logs != null) {
            logsGrouped = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestHour,
                            Collectors.counting()));
        }
        return logsGrouped;
    }

    public static Map<String, Double> groupLogsByName(String fileName) {
        List<Log> logs = readFile(fileName);
        Map<String, Double> logsGrouped = null;
        if (logs != null) {
            logsGrouped = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestResourceName,
                            Collectors.averagingInt(Log::getRequestDuration)));
        }
        return logsGrouped;
    }

    private static List<Log> readFile(String fileName) {
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

}
