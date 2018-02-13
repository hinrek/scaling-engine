package com.company;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        String fileName;
        int logLimit;

        Log logs = new Log();

        if (args.length < 1) {
            System.out.println(printHelp());
        } else if (args[0].equals("-h") || args[0].equals("-help")) {
            System.out.println(printHelp());
        } else if (args.length == 2) {
            try {
                fileName = args[0];
                logLimit = Integer.parseInt(args[1]);

                Map groupedLogs = logs.groupLogsByName(logs.readFile(fileName));
                logs.printSortedLogs(groupedLogs, logLimit);

                Map countedLogs = logs.countRequestsBerHour(logs.readFile(fileName));
                System.out.println(countedLogs);

            } catch (NumberFormatException e) {
                System.err.println("Argument " + args[1] + " must be an integer.");
                System.exit(1);
            }
        } else {
            System.out.println(printHelp());
        }

        long endTime = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("\nDuration: " + seconds + "ms");
    }

    private static String printHelp() {
        return "Usage: scaling-engine.jar [Filename] [Rows] " +
                "\n e.g java -jar scaling-engine.jar timing.log 10";
    }
}
