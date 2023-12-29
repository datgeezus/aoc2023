package org.aoc;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day10 {
    private record Position(int r, int c) {}

    private record Context(char[][] graph, Set<Position> visited, List<Position> start) {}

    private static final Map<Character, Map<Position, Set<Character>>> MAP =
            Map.of(
                    '|',
                    Map.of(
                            new Position(1, 0), Set.of('|', 'L', 'J'),
                            new Position(-1, 0), Set.of('|', '7', 'F')),
                    '-',
                    Map.of(
                            new Position(0, 1), Set.of('-', 'J', '7'),
                            new Position(0, -1), Set.of('-', 'L', 'F')),
                    'L',
                    Map.of(
                            new Position(-1, 0), Set.of('|', '7', 'F'),
                            new Position(0, 1), Set.of('-', 'J', '7')),
                    'J',
                    Map.of(
                            new Position(-1, 0), Set.of('|', '7', 'F'),
                            new Position(0, -1), Set.of('-', 'L', 'F')),
                    '7',
                    Map.of(
                            new Position(1, 0), Set.of('|', 'L', 'J'),
                            new Position(0, -1), Set.of('-', 'L', 'F')),

                    'F',
                    Map.of(
                            new Position(1, 0), Set.of('|', 'L', 'J'),
                            new Position(0, 1), Set.of('-', 'J', '7')),
                    'S',
                    Map.of(
                            new Position(1, 0), Set.of('|', 'L', 'J'),
                            new Position(-1, 0), Set.of('|', '7', 'F'),
                            new Position(0, 1), Set.of('-', 'J', '7'),
                            new Position(0, -1), Set.of('-', 'L', 'F'))
            );

    private static final Map<Character, Integer[][]> MOVES = Map.of(
            '|', new Integer[][] {{1, 0}, {-1, 0}},
            '-', new Integer[][] {{0, 1}, {0, -1}},
            'L', new Integer[][] {{-1, 0}, {0, 1}},
            'J', new Integer[][] {{-1, 0}, {0, -1}},
            '7', new Integer[][] {{1, 0}, {0, -1}},
            'F', new Integer[][] {{1, 0}, {0, 1}},
            'S', new Integer[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}
    );

//    private static final Map<Character, Set<Character>> NEXT = Map.of(
//            '|', Set.of('J', '|', '7'),
//            '-', Set.of('-', 'F', '7'),
//            'L', Set.of('-', 'F', '7', 'J', '|'),
//            'J', Set.of('-', 'F', '7', 'J', '|'),
//            '7', Set.of('-', '|', '7'),
//            'F', Set.of('-', '|', '7'),
//    );

    private static final Position[] MOVES_ALL = new Position[] {
            new Position(0, 1),
            new Position(0, -1),
            new Position(1, 0),
            new Position(-1, 0)
    };

    public static int part1(String input) {
        char[][] graph = buildGraph(input);
        Context context = new Context(graph, new HashSet<>(), new ArrayList<>());
        Position start = findStart(graph);
        context = new Context(graph, new HashSet<>(), new ArrayList<>());
        return bfs(context, start, Day10::printPosition, Day10::next1);
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
                .filter(move -> validMove(now, move, graph))
                .collect(Collectors.toList());
    }

    private static boolean validMove(Position now, Position next, char[][] grid) {
        char c = grid[now.r][now.c];
        char n = grid[now.r + next.r][now.c + next.c];
        return MAP.get(c).get(next).contains(n);
    }

    private static List<Position> next1(Position now, Context context) {
        var graph = context.graph;
        var visited = context.visited;
        var c = graph[now.r][now.c];
        Predicate<Map.Entry<Position, Set<Character>>> inVisited = entry -> visited.contains(entry.getKey());
        return MAP.get(c).entrySet().stream()
                .filter(Predicate.not(inVisited))
                .filter(delta -> withinBounds(now, delta.getKey(), graph))
                .filter(delta -> validMove(now, delta.getKey(), graph))
                .map(Map.Entry::getKey)
                .map(delta -> new Position(now.r + delta.r, now.c + delta.c))
                .collect(Collectors.toList());
    }

    private static Boolean withinBounds(Position now, Position delta, char[][] graph) {
        int nRows = graph.length;
        int nCols = graph[0].length;
        int r = now.r + delta.r;
        int c = now.c + delta.c;
        return 0 <= r && r < nRows && 0 <= c && c < nCols;
    }


    private static Boolean withinBounds(Position now, char[][] graph) {
        int nRows = graph.length;
        int nCols = graph[0].length;
        return 0 <= now.r && now.r < nRows && 0 <= now.c && now.c < nCols;
    }

    private static void printPosition(Position position, Context context) {
        if (context.graph[position.r][position.c] == 'S') {
//            System.out.println(position);
            context.start.add(new Position(position.r, position.c));
        }
        context.visited.add(position);
//        System.out.println(position);
    }

    private static char[][] buildGraph(String input) {
        return input.lines().map(String::toCharArray).toList().toArray(char[][]::new);
    }

    private static Position findStart(char[][] grid) {
        for (int r = 0; r < grid.length; ++r) {
            for (int c = 0; c < grid[0].length; ++c) {
                if (grid[r][c] == 'S') {
                    return new Position(r, c);
                }
            }
        }

        return new Position(-1, -1);
    }
}
