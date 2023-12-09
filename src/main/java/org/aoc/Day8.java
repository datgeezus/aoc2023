package org.aoc;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day8 {
    private record Node(String key, String[] children) {}

    private enum Step {L, R}

    private static final String START = "AAA";
    private static final String END = "ZZZ";

    public static int part1(String input) {
        Deque<Step> steps = buildSteps(input.lines().findFirst().get());
        Map<String, String[]> graph = buildGraph(input);
        return nSteps(graph, steps, START, END::equals);
    }

    public static int part2(String input) {
        Deque<Step> steps = buildSteps(input.lines().findFirst().get());
        Map<String, String[]> graph = buildGraph(input);
        List<String> starts = graph.keySet().stream().filter(k -> k.endsWith("A")).toList();

        return starts.stream()
                .map(start -> nSteps(graph, steps, start, (s) -> s.endsWith("Z")))
                .reduce(1, (a, b) -> a * b);

//        return nSteps(graph, steps, starts, Day8::atEnd);
    }

    private static boolean atEnd(List<String> now) {
        return now.stream().allMatch(s -> s.endsWith("Z"));
    }

    private static int nSteps(
            Map<String, String[]> graph, Deque<Step> steps, String start, Predicate<String> atEnd) {
        String now = start;
        int n = 0;
        while (!atEnd.test(now)) {
            Step step = steps.pop();
            String[] children = graph.get(now);
            now = children[step.ordinal()];
            steps.add(step);
            n += 1;
        }

        return n;
    }

    private static int nSteps(
            Map<String, String[]> graph,
            Deque<Step> steps,
            List<String> starts,
            Predicate<List<String>> atEnd) {

        List<String> now = starts;
        int n = 0;
        while (!atEnd.test(now)) {
            Step step = steps.pop();
            List<String[]> children = now.stream().map(graph::get).toList();

            now = children.stream().map(c -> c[step.ordinal()]).collect(Collectors.toList());

            steps.add(step);
            n += 1;
        }

        return n;
    }

    private static Map<String, String[]> buildGraph(String lines) {
        return lines.lines()
                .skip(2)
                .map(Day8::buildNode)
                .collect(Collectors.toMap(node -> node.key, node -> node.children));
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
}
