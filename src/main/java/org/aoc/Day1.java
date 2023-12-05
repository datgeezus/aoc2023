package org.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * --- Day 1: Trebuchet?! ---
 * Something is wrong with global snow production, and you've been selected to take a look.
 * The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that
 * are likely to be having problems.
 * You've been doing this long enough to know that to restore snow operations,
 * you need to check all fifty stars by December 25th.
 * Collect stars by solving puzzles.
 * Two puzzles will be made available on each day in the Advent calendar;
 * the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 *
 * You try to ask why they can't just use a weather machine ("not powerful enough")
 * and where they're even sending you ("the sky") and why your map looks mostly blank
 * ("you sure ask a lot of questions") and hang on did you just say the sky
 * ("of course, where do you think snow comes from") when you realize that the Elves are already loading
 * you into a trebuchet ("please hold still, we need to strap you in").
 *
 * As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been amended by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.
 *
 * The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
 *
 * For example:
 *
 * 1abc2
 * pqr3stu8vwx
 * a1b2c3d4e5f
 * treb7uchet
 *
 * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
 *
 * Consider your entire calibration document. What is the sum of all of the calibration values?
 */
public final class Day1 {

    private static Logger logger = LoggerFactory.getLogger(Day1.class);

    private static final Map<String, Integer> NUMBERS = Map.of(
            "zero", 0,
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
    );

    public static Integer part1(String input) {
        return input.lines().map(Day1::getCalibrationNumber).reduce(0, Integer::sum);
    }

    public static Integer part2(String input) {
        Trie trie = new Trie();
        NUMBERS.forEach(trie::insert);
        return input.lines().map(line -> getNumberWithStrings(line, trie)).reduce(0, Integer::sum);
    }

    private static Integer getCalibrationNumber(String line) {
        int len = line.length();
        int l = 0;
        int r = len - 1;
        int x = 1;
        int ans = 0;

        for (int i = r; i >= 0; --i) {
            var c = line.charAt(i);
            if (Character.isDigit(c)) {
                ans += x * (c - '0');
                x *= 10;
                break;
            }
        }

        for (int i = l; i <= r; ++i) {
            var c = line.charAt(i);
            if (Character.isDigit(c)) {
                ans += x * (c - '0');
                x *= 10;
                break;
            }
        }

        return ans;
    }

    private static Integer getNumberWithStrings(String line, Trie trie) {
        var stack = new ArrayDeque<Integer>();
        int l = 0;

        while (l < line.length()) {
            var c = line.charAt(l);
            if (Character.isDigit(c)) {
                stack.add(c - '0');
            } else {
                Optional<TrieValue> val = trie.containsNode(line.substring(l));
                if (val.isPresent()) {
                    TrieValue data = val.get();
                    stack.add(data.value);
                    l += data.position;
                }
            }
            l += 1;
        }

        int ans =  (10 * stack.getFirst()) + stack.getLast();

        logger.info("line: {}, numbers: {}, sum: {}", line, stack, ans);

        return ans;
    }

    private static Optional<Integer> digitFromStr(String slice) {
        return Optional.ofNullable(NUMBERS.get(slice));
    }

    private static final class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String content = "";
        Integer level = 0;
        Integer value = -1;
        boolean isWord = false;

        public TrieNode(Integer level) {
            this.level = level;
        }
    }

    private static final class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode(0);
        }

        public void insert(String word, Integer value) {
            TrieNode current = root;

            int n = word.length();
            for (int i = 0; i < n; ++i) {
                var c = word.charAt(i);
                int finalI = i;
                current = current.children.computeIfAbsent(c, v -> new TrieNode(finalI));
            }

            current.isWord = true;
            current.value = value;
        }

        public Optional<TrieValue> containsNode(String word) {
            TrieNode curr = root;

            for (int i = 0; i < word.length(); ++i) {
                var c = word.charAt(i);
                TrieNode node = curr.children.get(c);
                if (node == null) {
                    return Optional.empty();
                } else if (node.isWord) {

                    return Optional.of(new TrieValue(node.value, node.level));
                }
                curr = node;
            }

            return curr.isWord
                    ? Optional.of(new TrieValue(curr.value, curr.level))
                    : Optional.empty();
        }
    }

    private record TrieValue(Integer value, Integer position) {}

}
