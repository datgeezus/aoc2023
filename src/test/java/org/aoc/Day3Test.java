package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test implements TestResources {

    private final String testInput = """
467..114.0
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
""";

    @Test
    public void testSimpleP1()  {
        int ans = Day3.part1(testInput);
        assertEquals(4361, ans);
    }

    @Test
    public void testP1()  {
        String input = loadResource("day3.txt");
        int ans = Day3.part1(input);
        assertEquals(4361, ans);
    }
}
