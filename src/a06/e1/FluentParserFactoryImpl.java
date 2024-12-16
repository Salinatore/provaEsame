package a06.e1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FluentParserFactoryImpl implements FluentParserFactory {

    private record FluentParserImpl<T>(Iterator<T> iterator) implements FluentParser<T> {

        @Override
        public FluentParser<T> accept(T value) {
            if (iterator.next().equals(value)) {
                return new FluentParserImpl<>(iterator);
            } else {
                throw new IllegalStateException("Wrong value");
            }
        }

    }

    @Override
    public FluentParser<Integer> naturals() {
        return new FluentParserImpl<>(Stream.iterate(0, i -> i + 1).iterator());
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return new FluentParserImpl<List<Integer>>(Stream.iterate(
                (List<Integer>) new ArrayList<Integer>(),
                list -> {
                    List<Integer> newList = new ArrayList<>(list);
                    newList.add(newList.size());
                    return newList;
                } 
        ).iterator());
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return new FluentParserImpl<>(IntStream.iterate(0, i -> i + 1).flatMap(gruop ->  IntStream.rangeClosed(0, gruop)).iterator());
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return new FluentParserImpl<>(Stream.iterate(1, i -> i + 1).flatMap(group -> IntStream.rangeClosed(1, group).mapToObj(n -> s.repeat(n))).iterator());    
    }

    private List<String> createList(int size, String s) {
        List<String> toReturn = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            toReturn.add(s);
        }
        return toReturn;
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return new FluentParserImpl<>(Stream.iterate(i0, op).map(i -> new Pair<>(i, createList(i, s))).iterator());
    }

}
