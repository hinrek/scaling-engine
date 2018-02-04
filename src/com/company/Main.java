package com.company;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        String fileName = "timing.log";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            Map<String, Integer> map = stream
                    .collect(Collectors.toMap(element -> element, s -> Integer.parseInt(s.substring(s.lastIndexOf(" ") + 1))));

            Map<String, Integer> result = map
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(100)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            result
                    .entrySet()
                    .forEach(System.out::println);


            /*
            stream
                    .map(s -> Integer.parseInt(s.substring(s.lastIndexOf(" ") + 1)))
                    .sorted()
                    .limit(1000)
                    .forEach(System.out::println);
                    */

        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Duration: " + seconds + "ms");
    }
}
