package a06.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ModelImpl implements Model {

    private List<List<Integer>> values;

    public ModelImpl(final int size) {
        Random random = new Random();
        values = IntStream.range(0, size)
                .mapToObj(i -> IntStream.range(0, size)
                    .mapToObj(n -> random.nextInt(2) + 1)
                    .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean fire() {
        boolean somethingHasChanged = this.applySums();
        this.applyGravity();
        return somethingHasChanged; 
    }

    @Override                          
    public List<List<Integer>> getAllValues() {
        return List.copyOf(this.values);
    }

    private boolean applySums() {
        boolean somethingHasChanged = false;
        for (var list : this.values) {
            for (int i = (list.size() - 1); i > 0; i--) {
                if (list.get(i) != 0 && list.get(i) == list.get(i - 1)) {
                    list.set(i, list.get(i) + list.get(i - 1));
                    list.set(i - 1, 0);
                    somethingHasChanged = true;
                    break;
                }
            }
        }
        return somethingHasChanged;
    }

    private void applyGravity() {
        this.values = this.values.stream()
                .map(l -> Stream.concat(
                        Stream.generate(() -> 0)
                            .limit(l.stream().filter(i -> i == 0).count()),
                        l.stream()
                            .filter(i -> i != 0)   
                        ).collect(Collectors.toCollection(ArrayList::new))
                ).collect(Collectors.toCollection(ArrayList::new));
    }
 
}
