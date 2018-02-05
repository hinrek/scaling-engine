package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        String fileName = "timing.log";

        Pattern pattern = Pattern.compile(" ");
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            List<Log> logs = in
                    .lines()
                    .map(line -> {
                        String[] arr = pattern.split(line);
                        return new Log(Integer.parseInt(arr[arr.length - 1]),
                                arr[4]);
                    })
                    .collect(Collectors.toList());

            System.out.println("Group by log request resource");
            Map<String, List<Log>> logsByRequestResource = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestedResource));


            System.out.println("Group by log request resource and average request duration");
            Map<String, Double> logsByRequestResourceAndAverageDuration = logs
                    .stream()
                    .collect(Collectors.groupingBy(Log::getRequestedResource,
                            Collectors.averagingInt(Log::getRequestDuration)));

            logsByRequestResourceAndAverageDuration
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(100)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }


        long endTime = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Duration: " + seconds + "ms");
    }
}
