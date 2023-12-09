package org.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day6 {

    public static long part1(List<Integer[]> races) {
        return races.stream()
                .map(x -> new Long[] {(long) x[0], (long) x[1]})
                .map(Day6::processOne)
                .reduce(1L, (x, y) -> x * y);
    }

    public static long part2(List<Long[]> races) {
        return races.stream().map(Day6::processOne).reduce(1L, (x, y) -> x * y);
    }

    public static long processOne(Long[] race) {
        long end = race[0];
        long time = race[1];

        List<Integer> ans = new ArrayList<>();

        for (int i = 1; i <= end; ++i) {
            long moving = (end - i);
            long diff = (moving * i);
            if (time < diff) {
                ans.add(i);
            }
        }

        return ans.size();

    }
    
}
