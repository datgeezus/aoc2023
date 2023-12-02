package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test implements TestResources {

    @Test
    void day1Simple() {
        var input = """
1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet
""";
        var ans = Day1.part1(input);
        assertEquals(142, ans);
    }

    @Test
    void day1() {
        var input = loadResource("day1p1.txt");
        var ans = Day1.part1(input);
        System.out.println(ans);
        assertEquals(54708, ans);
    }

    @Test
    void day1p2Simple() {
        var input = """
two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
""";
        var ans = Day1.part2(input);
        assertEquals(281, ans);
    }

    @Test
    void day1p2() {
        var input = loadResource("day1p2.txt");
        var ans = Day1.part2(input);
        System.out.println(ans);
        assertEquals(54708, ans);
    }
}
