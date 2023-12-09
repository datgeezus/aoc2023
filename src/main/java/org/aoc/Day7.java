package org.aoc;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day7 {
    private record Hand(String cards, Integer bid, Type type) {}

    private enum Type {FIVE, FOUR, FULL, THREE, TWO, ONE, HIGH}

    private static final String RANKS = "AKQJT98765432";

    public static int part1(String input) {
        List<Hand> sortedHands = input.lines().map(Day7::processOne).sorted(Day7::handComparator).toList();
        AtomicInteger mult = new AtomicInteger(sortedHands.size());
        return sortedHands.stream().map(x -> mult.getAndDecrement() * x.bid).reduce(0, Integer::sum);
    }

    private static int handComparator(Hand a, Hand b) {
        if (a.type != b.type) {
            return a.type.compareTo(b.type);
        } else {
            return rankComparator(a.cards, b.cards);
        }
    }

    private static int rankComparator(String a, String b) {
        for (int i = 0; i < a.length(); ++i) {
            int aRank = RANKS.indexOf(a.charAt(i));
            int bRank = RANKS.indexOf(b.charAt(i));

            if (aRank == bRank) continue;
            return  (aRank > bRank) ? 1 : -1;
        }
        return 0;
    }


    private static Hand processOne(String line) {
        String[] game = line.split("\\s+");
        String cards = game[0].trim();
        Integer bid = Integer.parseInt(game[1].trim());
        Type type = getType(cards);
        return new Hand(cards, bid, type);
    }

    private static Type getType(String cards) {
        Map<Character, Integer> count = new HashMap<>();


        cards.chars().forEach(c -> count.merge((char) c, 1, Integer::sum));

        Set<Integer> values = new HashSet<>(count.values());

        if (values.contains(5)) {
            return Type.FIVE;
        } else if(values.contains(4)){
            return Type.FOUR;
        } else if(values.contains(3)) {
            return values.contains(2) ? Type.FULL : Type.THREE;
        } else if (values.contains(2)) {
            long c = count.entrySet().stream().filter(x -> x.getValue() == 2).count();
            return c > 1 ? Type.TWO : Type.ONE;
        } else {
            return Type.HIGH;
        }
    }
}
