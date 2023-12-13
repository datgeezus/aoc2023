package org.aoc;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day10 {
    private record Position(int r, int c) {}

    private record Context(char[][] graph, Set<Position> visited, List<Position> start) {}

    private static final Map<Character, Integer[][]> MOVES = Map.of(
            '|', new Integer[][] {{1, 0}, {-1, 0}},
            '-', new Integer[][] {{0, 1}, {0, -1}},
            'L', new Integer[][] {{-1, 0}, {0, 1}},
            'J', new Integer[][] {{-1, 0}, {0, -1}},
            '7', new Integer[][] {{1, 0}, {0, -1}},
            'F', new Integer[][] {{1, 0}, {0, 1}},
            'S', new Integer[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}
    );

    private static final Position[] MOVES_ALL = new Position[] {
            new Position(0, 1),
            new Position(0, -1),
            new Position(1, 0),
            new Position(-1, 0)
    };

    public static int part1(String input) {
        char[][] graph = buildGraph(input);
        Context context = new Context(graph, new HashSet<>(), new ArrayList<>());
        int levels = bfs(context, new Position(0, 0), Day10::printPosition, Day10::nextAll);
        Position start = context.start.get(0);
        context = new Context(graph, new HashSet<>(), new ArrayList<>());
        return bfs(context, start, Day10::printPosition, Day10::next);
    }

    private static int bfs(
            Context context,
            Position start,
            BiConsumer<Position, Context> visit,
            BiFunction<Position, Context, List<Position>> nextMove) {
        Deque<Position> deque = new ArrayDeque<>();
        deque.add(start);
        int level = 0;
        while (!deque.isEmpty()) {
            int n = deque.size();
            for (int i = 0; i < n; ++i) {
                var curr = deque.pop();
                visit.accept(curr, context);
                List<Position> next = nextMove.apply(curr, context);
                deque.addAll(next);
            }
            level += 1;
        }
        return level - 1;
    }

    private static List<Position> nextAll(Position now, Context context) {
        if (!context.start.isEmpty()) {
            return Collections.emptyList();
        }
        var graph = context.graph;
        var visited = context.visited;
        return Arrays.stream(MOVES_ALL)
                .map(move -> new Position(now.r + move.r, now.c + move.c))
                .filter(Predicate.not(visited::contains))
                .filter(move -> withinBounds(move, graph))
                .collect(Collectors.toList());
    }

    private static List<Position> next(Position now, Context context) {
        var graph = context.graph;
        var visited = context.visited;
        var c = graph[now.r][now.c];
        Predicate<Position> valid = (p) -> MOVES.containsKey(graph[p.r][p.c]);
        return Arrays.stream(MOVES.get(c))
                .map(move -> new Position(now.r + move[0], now.c + move[1]))
                .filter(Predicate.not(visited::contains))
                .filter(move -> withinBounds(move, graph))
                .filter(valid)
                .collect(Collectors.toList());
    }

    private static Boolean withinBounds(Position now, char[][] graph) {
        int nRows = graph.length;
        int nCols = graph[0].length;
        return 0 <= now.r && now.r < nRows && 0 <= now.c && now.c < nCols;
    }

    private static void printPosition(Position position, Context context) {
        if (context.graph[position.r][position.c] == 'S') {
            System.out.println(position);
            context.start.add(new Position(position.r, position.c));
        }
        context.visited.add(position);
//        System.out.println(position);
    }

    private static char[][] buildGraph(String input) {
        return input.lines().map(String::toCharArray).toList().toArray(char[][]::new);
    }
}
