package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test implements TestResources {

    private static final String INPUT = """
RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)
""";

    private static final String INPUT2 = """
LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)
""";

    @Test
    public void part1Simple() {
        int ans = Day8.part1(INPUT);
        assertEquals(2, ans);
    }

    @Test
    public void part1Simple2() {
        int ans = Day8.part1(INPUT2);
        assertEquals(6, ans);
    }

    @Test
    public void part1() {
        String input = loadResource("day8.txt");
        int ans = Day8.part1(input);
        assertEquals(17287, ans);
    }
}
