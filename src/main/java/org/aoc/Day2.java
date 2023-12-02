package org.aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day2 {

    public static int day2p1(String input) {
        Map<String, Integer> avalableCubes = Map.of(
                "red", 12,
                "green", 13,
                "blue", 14
        );
        return input.lines()
                .map(line -> computePossible(line, avalableCubes))
                .reduce(0, Integer::sum);
    }

    public static int day2p2(String input) {
        return input.lines().map(Day2::computeFewest).reduce(0, Integer::sum);
    }

    private static Integer computeFewest(String line) {
        String[] game = line.split(":");
        var fewest = buildFewest(game[1].trim());
        return fewest.values().stream().reduce(1, (x, y) -> x * y);
    }

    // line: "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
    private static Integer computePossible(String line, Map<String, Integer> availableCubes) {
        String[] game = line.split(":");
        int id = Integer.parseInt(game[0].split(" ")[1]);
        boolean isPossible = isGamePossible(game[1].trim(), availableCubes);
        return isPossible ? id : 0;
    }

    // game: "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
    private static Map<String, Integer> buildFewest(String game) {
        List<Turn> turns = buildTurns(game).stream().flatMap(List::stream).toList();
        return turns.stream()
                .collect(Collectors.toMap(turn -> turn.name, turn -> turn.val, Integer::max));
    }

    // game: "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
    private static boolean isGamePossible(String game, Map<String, Integer> availableCubes) {
        List<List<Turn>> turns = buildTurns(game);
        return turns.stream().noneMatch(turn -> notEnoughCubes(turn, availableCubes));
    }

    private static boolean notEnoughCubes(List<Turn> turns, Map<String, Integer> cubes) {
        Predicate<Turn> notEnough = (t) -> t.val > cubes.get(t.name);
        return turns.stream().anyMatch(notEnough);
    }

    // ex: "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
    private static List<List<Turn>> buildTurns(String game) {
        String[] sets = game.split(";");

        return Arrays.stream(sets)
                .map(String::trim)
                .map(t -> t.split(","))
                .map(Day2::getTurns)
                .toList();
    }

    // ex: { "3 blue", "4 red" }
    private static List<Turn> getTurns(String[] turns) {
        return Arrays.stream(turns)
                .map(String::trim)
                .map(t -> t.split(" "))
                .map(t -> new Turn(t[1], Integer.valueOf(t[0])))
                .collect(Collectors.toList());
    }

    record Turn(String name, Integer val) {}
}
