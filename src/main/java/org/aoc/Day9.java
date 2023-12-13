package org.aoc;

import java.util.Arrays;

public class Day9 {

    public static int part1(String input) {
        return input.lines()
                .map(line -> line.split("\\s+"))
                .map(Day9::toNums)
                .map(Day9::processOne)
                .reduce(0, Integer::sum);
    }

    private static int processOne(Integer[] history) {
        return 0;
    }

    private static Integer[] toNums(String[] history) {
        return Arrays.stream(history).map(Integer::parseInt).toArray(Integer[]::new);
    }
}
