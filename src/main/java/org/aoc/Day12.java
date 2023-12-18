package org.aoc;

public class Day12 {

    public static int part1(String input) {
        return input.lines().map(Day12::findArrangements).reduce(0, Integer::sum);
    }

    private static int findArrangements(String line) {
        String[] row = line.split("\\s+");
        String springs = row[0].trim();
        String[] groups = row[1].trim().split(",");
        return 0;
    }
}
