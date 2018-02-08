package com.company;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        String fileName = "timing.log";
        
        try (Stream<String> in = Files.lines(Paths.get(fileName))) {
            Pattern pattern = Pattern.compile(" ");
            List<Log> logs;
            logs = in
                    .map(pattern::split)
                    .map((arr) -> new Log(Integer.parseInt(arr[arr.length - 1]),
                            arr[4]))
                    .collect(Collectors.toList());
            in.close();

            Map<String, Double> logsGrouped;
            logsGrouped = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestResourceName,
                            Collectors.averagingInt(Log::getRequestDuration)));
            logsGrouped
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(entry -> System.out.printf("ms: %8.1f | Resource name: %s%n", entry.getValue(), entry.getKey()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Duration: " + seconds + "ms");
    }
}
