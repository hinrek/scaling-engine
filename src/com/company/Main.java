package com.company;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        String fileName;
        int logLimit;

        if (args.length == 0 || args[0].equals("-h") || args[0].equals("-help")) {
            printHelp();
        } else if (args.length == 2) {
            try {
                fileName = args[0];
                logLimit = Integer.parseInt(args[1]);

                Map<String, Double> groupedLogs = LogReader.groupLogsByName(fileName);
                LogReader.printSortedLogs(groupedLogs, logLimit);

                Map<LocalTime, Long> countedLogs = LogReader.countRequestsPerHour(fileName);
                LogReader.printHourlyHistogram(countedLogs);

            } catch (NumberFormatException e) {
                System.err.println("Argument " + args[1] + " must be an integer.");
                System.exit(1);
            }
        } else {
            printHelp();
        }

        long endTime = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("\nDuration: " + seconds + "ms");
    }

    private static void printHelp() {
        System.out.println("Usage: scaling-engine.jar [Filename] [Rows] " +
                "\n e.g java -jar scaling-engine.jar timing.log 10");
    }
}
