package org.aoc;

import org.junit.jupiter.api.Test;

import java.util.Collections;
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

        long ans = Day6.part1(input);
        assertEquals(288, ans);
    }

    @Test
    public void part1() {
        List<Integer[]> input = List.of(
                new Integer[]{50, 242},
                new Integer[]{74, 1017},
                new Integer[]{86, 1691},
                new Integer[]{85, 1252}
        );

        long ans = Day6.part1(input);
        assertEquals(1731600, ans);
    }

    @Test
    public void part2() {
        List<Long[]> input = Collections.singletonList(
                new Long[]{50748685L, 242101716911252L}
        );

        long ans = Day6.part2(input);
        assertEquals(40087680, ans);
    }
}
