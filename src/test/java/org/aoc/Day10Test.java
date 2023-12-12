package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test implements TestResources {

    private static final String INPUT = """
.....
.S-7.
.|.|.
.L-J.
.....
""";

    private static final  String INPUT2 = """
..F7.
.FJ|.
SJ.L7
|F--J
LJ...
""";

    @Test
    public void part1Simple() {
        int ans = Day10.part1(INPUT);
        assertEquals(4, ans);
    }

    @Test
    public void part1Simple2() {
        int ans = Day10.part1(INPUT2);
        assertEquals(8, ans);
    }

    @Test
    public void part1() {
        String input = loadResource("day10.txt");
        int ans = Day10.part1(input);
        assertEquals(8, ans);
    }
}
