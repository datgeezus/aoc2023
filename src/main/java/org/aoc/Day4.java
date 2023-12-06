package org.aoc;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {

    public static int par1(String input) {
        Map<Integer, Game> games =
                input.lines()
                        .map(Day4::buildGames)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return games.values().stream()
                .map(Day4::processOne)
                .map(x -> (int) Math.pow(2, x - 1))
                .reduce(0, Integer::sum);
    }

    public static int par2(String input) {
        Map<Integer, Game> games =
                input.lines()
                        .map(Day4::buildGames)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer, Long> cards = games.entrySet().stream()
                .map(game -> Map.entry(game.getKey(), Day4.processOne(game.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return 0;
    }


    private static long processOne(Game game) {
        Set<Integer> numbers = game.numbers;
        Set<Integer> winning = game.winning;
        return winning.stream().filter(numbers::contains).count();
    }

    // line: "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
    private static Map.Entry<Integer, Game> buildGames(String line) {
        String[] gameStr = line.split(":");
        int i = Integer.parseInt(gameStr[0].split("\\s+")[1]);
        Game game = buildGame(gameStr[1].trim());
        return Map.entry(i, game);
    }


    // line: "41 48 83 86 17 | 83 86  6 31 17  9 48 53"
    private static Game buildGame(String line) {
        String[] game = line.split("\\|");
        String[] winning = game[0].trim().split("\\s+");
        String[] numbers = game[1].trim().split("\\s+");

        return new Game(
                Arrays.stream(winning).map(Integer::parseInt).collect(Collectors.toSet()),
                Arrays.stream(numbers).map(Integer::parseInt).collect(Collectors.toSet()));
    }

    record Game(Set<Integer> winning, Set<Integer> numbers) {}
}
