package org.aoc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8 {
    private record Node(String key, String[] children) {}

    private enum Step {L, R}

    private static final String START = "AAA";
    private static final String END = "ZZZ";

    public static int part1(String input) {
        Deque<Step> steps = buildSteps(input.lines().findFirst().get());

        Map<String, String[]> graph =
                input.lines().skip(2)
                        .map(Day8::buildNode)
                        .collect(Collectors.toMap(node -> node.key, node -> node.children));
        return nSteps(graph, steps);
    }

    private static Deque<Step> buildSteps(String line) {
        Deque<Step> deque = new ArrayDeque<>(line.length());
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            deque.add(Step.valueOf(Character.toString(c)));
        }
        return deque;
    }

    // "AAA = (BBB, CCC)"
    private static Node buildNode(String line) {
        String[] parts = line.split("=");
        String key = parts[0].trim();
        String[] children = parts[1].substring(2, parts[1].length() - 1).split(",\\s+");
        return new Node(key, children);
    }

    private static int nSteps(Map<String, String[]> graph, Deque<Step> steps) {
        String now = START;
        int n = 0;
        while (!END.equals(now)) {
            Step step = steps.pop();
            String[] children = graph.get(now);
            now = children[step.ordinal()];
            steps.add(step);
            n += 1;
        }

        return n;
    }
}
