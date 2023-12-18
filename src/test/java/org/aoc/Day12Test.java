package org.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test implements TestResources {

    private static final String INPUT = """
???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
""";

    @Test
    public void part1Simple() {
        int ans = Day12.part1(INPUT);
        assertEquals(21, ans);
    }
}
