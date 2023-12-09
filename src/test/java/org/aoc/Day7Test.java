package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test implements TestResources {

    private static final String INPUT = """
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
""";

    @Test
    public void part1Simple() {
        int ans = Day7.part1(INPUT);
        assertEquals(6440, ans);
    }

    @Test
    public void part1() {
        String input = loadResource("day7.txt");
        int ans = Day7.part1(input);
        assertEquals(6440, ans);
    }
}
