package org.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day5Test implements TestResources {

    private static final String TEST_INPUT = """
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4
""";

    @Test
    public void part1Simple() {
        Long ans = Day5.part1(TEST_INPUT);
        Assertions.assertEquals(35L, ans);
    }

    @Test
    public void par1() {
        final String input = loadResource("day5.txt");
        Long ans = Day5.part1(input);
        Assertions.assertEquals(600279879L, ans);
    }
}
