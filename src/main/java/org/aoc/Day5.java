package org.aoc;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day5 {
    private record Range(Long dest, Long source, Long limit) {}
    private record To(String name, List<Range> maps) {}
    private record Pair(Long start, Long limit) {}

    public static Long part1(String input) {
        List<Long> seeds = getSeeds(input.lines().findFirst().get());

        Map<String, To> collect = input.lines().skip(2).collect(RangeCollector.collector());

        return seeds.stream().map(seed -> processOne(collect, seed)).min(Long::compareTo).orElse(seeds.get(0));
    }

    public static Long part2(String input) {
        List<Pair> seeds = getSeedPairs(input.lines().findFirst().get());

        Map<String, To> collect = input.lines().skip(2).collect(RangeCollector.collector());

//                List<Long> mins = new ArrayList<>();
//                for (var seed: seeds) {
////                    var sl = LongStream.rangeClosed(seed.start, seed.start +
////         seed.limit).boxed().toList();
//                    var val = LongStream.rangeClosed(seed.start, seed.start + seed.limit)
//                            .map(s -> processOne(collect, s)).min().orElse(0L);
//                    mins.add(val);
//                }
//
//                return mins.stream().min(Long::compareTo).orElse(0L);

        return seeds.stream()
                .flatMapToLong(
                        seed -> LongStream.rangeClosed(seed.start, seed.start + seed.limit))
                .map(s -> processOne(collect, s))
                .min().orElse(0L);
    }

    private static List<Pair> getSeedPairs(String line) {
        List<Long> seeds = getSeeds(line);
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            pairs.add(new Pair(seeds.get(i), seeds.get(i + 1)));
        }
        return pairs;
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
