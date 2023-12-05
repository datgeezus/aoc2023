package org.aoc;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Day3 {

    public static int part1(String input) {

        AtomicInteger row = new AtomicInteger(0);
        List<Boolean[]> symbols = input.lines().map(Day3::findSymbols).toList();

        return input.lines()
                .map(line -> process(line, row.getAndIncrement(), symbols))
                .reduce(0, Integer::sum);
    }

    public static int process(String line, int y, List<Boolean[]> symbols) {
        int n = line.length();
        int p = 1;
        int i = 0;

        Predicate<Integer> inBounds = (x) -> x < n;
        Predicate<Integer> isClose = (x) -> isCloseToSymbol(x, y, symbols);

        boolean found = false;
        List<String> numStr = new ArrayList<>();
        int r = 0;
        while (inBounds.test(i)) {
            while (inBounds.test(r) && Character.isDigit(line.charAt(r))) {
                r += 1;
                found = true;
            }

//            r = Math.min(r, n-1);

//            if (found) {
            if (found && (isClose.test(r) || isClose.test(i))) {
                numStr.add(line.substring(i, r));
                found = false;
            } else {
                r += 1;
            }
            i = r;
        }

        return numStr.stream()
                .filter(Predicate.not(String::isEmpty))
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }


    private static Boolean[] findSymbols(String line) {
        int n = line.length();

        Boolean[] symbols = new Boolean[n];
        Arrays.fill(symbols, false);

        for (int i = 0; i < n; ++i) {
            var c = line.charAt(i);
            if (isSymbol(c)) {
                symbols[i] = true;
            }
        }

        return symbols;
    }

    private static boolean isCloseToSymbol(int x, int y, List<Boolean[]> symbols) {
        int n = Math.max(0, y - 1);
        int s = Math.min(symbols.size() - 1, y + 1);
        int e = Math.min(symbols.get(0).length - 1, x + 1);
        int w = Math.max(0, x - 1);
        int xx = Math.min(x, symbols.get(0).length - 1);
        int yy = Math.min(y, symbols.size() - 1);

        return symbols.get(y)[xx]
                || symbols.get(y)[e]
                || symbols.get(y)[w]
                || symbols.get(n)[xx]
                || symbols.get(n)[e]
                || symbols.get(n)[w]
                || symbols.get(s)[xx]
                || symbols.get(s)[e]
                || symbols.get(s)[w];
    }

    private static boolean isSymbol(char c) {
        return !(Character.isDigit(c) || Character.isAlphabetic(c) || c == '.');
    }

    private record Row(Integer x, Boolean[] symbol) {}
}
