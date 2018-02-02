package com.company;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
	    String fileName = "timing.log";

	    try(Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream
                    .map(s -> Integer.parseInt(s.substring(s.lastIndexOf(" ") + 1)))
                    .sorted()
                    .forEach(System.out::println);

        } catch (IOException e){
	        e.printStackTrace();
        }

        long endTime   = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Duration: " + seconds + "ms");
    }
}
