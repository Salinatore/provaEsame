package a06.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ModelImpl implements Model {

    private List<List<Integer>> values;

    public ModelImpl(final int size) {
        Random random = new Random();
        values = IntStream.range(0, size)
                .mapToObj(i -> IntStream.range(0, size)
                    .mapToObj(n -> random.nextInt(2) + 1)
                    .toList())
                .toList();
    }

    @Override
    public boolean fire() {
        this.values = this.values.stream()
                .map(l -> this.apply(l))
                .toList();
        return true;
    }
                
    private List<Integer> apply(List<Integer> list) {
        List<Integer> newList = new ArrayList<>(list.size());
        newList.addAll(list);
        for (int i = 0; i < (newList.size() - 1); i++) {
            var value = newList.get(i);
            if (value != 0 && value == newList.get(i + 1)) {
                newList.set(i, value + 1);
                newList.remove(i + 1);
                newList.addLast(0);
            }
        }
        return List.copyOf(newList);
    }

    public List<List<Integer>> matrix() {
        return List.copyOf(values);
    }
}
