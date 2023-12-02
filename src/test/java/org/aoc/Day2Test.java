package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test implements TestResources {

    @Test
    public void day2p1Simple() {
        String input = """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""";
        int ans = Day2.day2p1(input);
        assertEquals(8, ans);
    }

    @Test
    public void day2p1() {
        String input = loadResource("day2.txt");
        int ans = Day2.day2p1(input);
        assertEquals(3099, ans);
    }

    @Test
    public void day2p2Simple() {
        String input = """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""";
        int ans = Day2.day2p2(input);
        assertEquals(2286, ans);
    }

    @Test
    public void day2p2() {
        String input = loadResource("day2.txt");
        int ans = Day2.day2p2(input);
        assertEquals(72970, ans);
    }
}
