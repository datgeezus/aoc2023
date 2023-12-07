package org.aoc;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day5 {
    private record Range(Long dest, Long source, Long limit) {}
    private record To(String name, List<Range> maps) {}

    public static Long part1(String input) {
        List<Long> seeds = getSeeds(input.lines().findFirst().get());

        Map<String, To> collect = input.lines().skip(2).collect(RangeCollector.collector());

        return seeds.stream().map(seed -> processOne(collect, seed)).min(Long::compareTo).orElse(seeds.get(0));
    }

    private static List<Long> getSeeds(String line) {
        String[] seeds = line.split(":");
        return Arrays.stream(seeds[1].trim().split("\\s+")).map(Long::parseLong).toList();
    }

    private static Long processOne(Map<String, To> almanac, Long seed) {
        String name = "seed";
        Long value = seed;
        while (!"location".equals(name)) {
            var to = almanac.get(name);

            Long finalValue = value;
            var next = to.maps.stream()
                    .map(range -> getVal(range, finalValue))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            value = next.isEmpty() ? value : next.get(0);
            name = to.name;
        }
        return value;
    }

    private static Optional<Long> getVal(Range range, final Long val) {
        boolean withinRange = range.source <= val && val <= range.source + range.limit;
        Function<Long, Long> map = (i) -> Math.abs(i - range.source) + range.dest;

        return withinRange ? Optional.of(map.apply(val)) : Optional.empty();
    }

    private static final class RangeCollector{

        private final Map<String, To> map = new HashMap<>();
        private String from = null;

        public void accept(String str) {
            String[] data = str.split("\\s+");
            if (2 == data.length) {
                String[] name = data[0].split("-");
                To to = new To(name[2], new ArrayList<>());
                map.put(name[0], to);
                from = name[0];
            } else if (3 == data.length) {
                var range =
                        new Range(
                                Long.parseLong(data[0]),
                                Long.parseLong(data[1]),
                                Long.parseLong(data[2]));
                map.get(from).maps().add(range);
            }
        }

        public RangeCollector combine(RangeCollector other) {
            throw new UnsupportedOperationException();
        }

        public Map<String, To> finish() {
            return map;
        }

        public static Collector<String, ?, Map<String, To>> collector() {
            return Collector.of(RangeCollector::new, RangeCollector::accept, RangeCollector::combine, RangeCollector::finish);
        }

    }
}
