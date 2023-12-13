package org.aoc;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day11 {
    private record Pos(int r, int c) {}
    private record Context(char[][] graph, Set<Pos> visited, Set<Pos> galaxies) {}

    private static final Integer[][] MOVES = new Integer[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static int part1(String input) {
        char[][] graph = buildGraph(input);
        Pos start = findStart(graph);
        Context context = new Context(graph, new HashSet<>(), new HashSet<>());
        findGalaxies(start, Day11::visit, context);
        List<List<Pos>> pairs = getPosPairs(context.galaxies);
        return pairs.stream().map(Day11::computeDistance).reduce(0, Integer::sum);
    }

    private static int computeDistance(List<Pos> pairs) {
        Pos a = pairs.get(0);
        Pos b = pairs.get(1);
        int r = Math.abs(a.r - b.r);
        int c = Math.abs(a.c - b.c);
        return r + c;
    }

    private static void findGalaxies(
            Pos start,
            BiConsumer<Pos, Context> visit,
            Context context) {
        Deque<Pos> deque = new ArrayDeque<>();
        deque.add(start);

        while (!deque.isEmpty()) {
            var now = deque.pop();
            visit.accept(now, context);
            List<Pos> next = getNext(now, context);
            deque.addAll(next);
        }

    }

    private static void visit(Pos now, Context context) {
        char[][] graph = context.graph;
        char c = graph[now.r][now.c];
        if (c == '#') {
            context.galaxies.add(now);
        }
        context.visited.add(now);

    }

    private static List<Pos> getNext(Pos now, Context context) {
        Predicate<Pos> notVisited = Predicate.not(context.visited::contains);
        return Arrays.stream(MOVES)
                .map(move -> new Pos(now.r + move[0], now.c + move[1]))
                .filter(next -> withinBounds(next, context))
                .filter(notVisited)
                .collect(Collectors.toList());
    }

    private static boolean isValid(Pos now, Context context) {
        char[][] graph = context.graph;;
        char c = graph[now.r][now.c];
        return c != '.';
    }

    private static boolean withinBounds(Pos now, Context context) {
        char[][] graph = context.graph;;
        int nRows = graph.length;
        int nCols = graph[0].length;
        return 0 <= now.r && now.r < nRows && 0 <= now.c && now.c < nCols;
    }

    private static Pos findStart(char[][] graph) {
        int nRows = graph.length;
        int nCols = graph[0].length;
        for (int r = 0; r < nRows; ++r) {
            for (int c = 0; c < nCols; ++c) {
                char g = graph[r][c];
                if (g == '#') {
                    return new Pos(r, c);
                }
            }
        }

        return new Pos(-1, -1);
    }

    private static char[][] buildGraph(String input) {
        return input.lines().map(String::toCharArray).toList().toArray(char[][]::new);
    }

    private static List<List<Pos>> getPosPairs(Set<Pos> galaxies) {
        List<Pos> arr = new ArrayList<>(galaxies);
        List<List<Pos>> ans = new ArrayList<>();
        for (int i = 0; i < arr.size() - 1; ++i) {
            for (int j = i + 1; j < arr.size(); ++j) {
                ans.add(List.of(arr.get(i), arr.get(j)));
            }
        }

        return ans;
    }
}
