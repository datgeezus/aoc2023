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

    private static final String INPUT3 = """
LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)
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

    @Test
    public void part2Simple() {
        int ans = Day8.part2(INPUT3);
        assertEquals(6, ans);
    }

    @Test
    public void part2() {
        String input = loadResource("day8.txt");
        int ans = Day8.part2(input);
        assertEquals(17287, ans);
    }
}
