package org.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {

    @Test
    public void part1simple() {
        List<Integer[]> input = List.of(
                new Integer[]{7, 9},
                new Integer[]{15, 40},
                new Integer[]{30, 200}
        );

        int ans = Day6.part1(input);
        assertEquals(288, ans);
    }

    @Test
    public void part2() {
        List<Integer[]> input = List.of(
                new Integer[]{50, 242},
                new Integer[]{74, 1017},
                new Integer[]{86, 1691},
                new Integer[]{85, 1252}
        );

        int ans = Day6.part1(input);
        assertEquals(1731600, ans);
    }
}
