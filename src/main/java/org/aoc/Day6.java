package org.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day6 {

    public static int part1(List<Integer[]> races) {
        return races.stream().map(Day6::processOne).reduce(1, (x, y) -> x * y);
    }

    public static int processOne(Integer[] race) {
        int end = race[0];
        int time = race[1];

        List<Integer> ans = new ArrayList<>();

        for (int i = 1; i <= end; ++i) {
            int moving = (end - i);
            int diff = (moving * i);
            if (time < diff) {
                ans.add(i);
            }
        }

        return ans.size();

    }
    
}
