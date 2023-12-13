package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test implements TestResources {

    private static final String INPUT = """
...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....
""";

    @Test
    public void part1Simple() {
        int ans = Day11.part1(INPUT);
        assertEquals(374, ans);
    }

    @Test
    public void part1() {
        String input = loadResource("day11.txt");
        int ans = Day11.part1(input);
        assertEquals(374, ans);
    }
}
