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

//        var ans = starts.stream()
//                .map(start -> nSteps(graph, steps, start, (s) -> s.endsWith("Z")))
//                .toList();

        return nSteps(graph, steps, starts, b -> atEnd(b, starts.size()));
    }

    private static boolean atEnd(List<String> now) {
        return now.stream().allMatch(s -> s.endsWith("Z"));
    }

    private static boolean atEnd(String[] now, int size) {
//        return now.size() == size && now.stream().allMatch(Predicate.not(String::isEmpty));
//        return now.size() == size;
        return now.length > 0 && Arrays.stream(now).allMatch(Objects::nonNull);
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
            Predicate<String[]> atEnd) {

        List<String> now = starts;
        String[] ended = new String[starts.size()];
        List<Map<String, Integer>> caches = new ArrayList<>(starts.size());

        for (var start: starts) {
            var map = new HashMap<String, Integer>();
            map.put(start, 0);
            caches.add(map);
        }

        int n = 0;
        while (!atEnd.test(ended)) {
            Step step = steps.pop();
            List<String[]> children = now.stream().map(graph::get).toList();

            now = children.stream().map(c -> c[step.ordinal()]).toList();

//            ended.addAll(now.stream().filter(a -> a.endsWith("Z")).toList());

            n += 1;
            for (int i = 0; i < starts.size(); ++i) {
                var curr = now.get(i);
                var cache = caches.get(i);
                cache.putIfAbsent(curr, n);
                if (curr.endsWith("Z")) {
                    ended[i] = curr;
                }
            }


            steps.add(step);
        }



        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < starts.size(); ++i) {
            var last = ended[i];
            var lastVal = caches.get(i).get(last);
            var curr = now.get(i);
            var currVal = caches.get(i).get(curr);
            var diff = lastVal - currVal;
            ans.add(lastVal + diff);
        }

        return ans.stream().reduce(0, Integer::sum);
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
