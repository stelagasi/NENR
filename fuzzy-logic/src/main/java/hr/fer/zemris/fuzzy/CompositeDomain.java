package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CompositeDomain extends Domain {

    private final SimpleDomain[] simpleDomains;

    public CompositeDomain(SimpleDomain... simpleDomains) {
        if (simpleDomains.length < 2) throw new IllegalArgumentException();
        this.simpleDomains = simpleDomains;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < getCardinality();
            }

            //todo probaj s primitivnim rijesiti
            @Override
            public DomainElement next() {
                ArrayList<Integer> values = new ArrayList<>();
                int position = current;
                int index;
                for (int i = getNumberOfComponents() - 1; i >= 0; i--) {
                    SimpleDomain simpleDomain = (SimpleDomain) getComponent(i);
                    index = position % simpleDomain.getCardinality();
                    position /= simpleDomain.getCardinality();
                    values.add(simpleDomain.getFirst() + index);
                }
                int[] intValues = new int[values.size()];
                for (int k = values.size() - 1; k >= 0; k--) {
                    intValues[values.size() - k - 1] = values.get(k);
                }
                current++;
                return new DomainElement(intValues);
            }
        };
    }

    @Override
    public int getCardinality() {
        int cardinality = 1;
        for (SimpleDomain simpleDomain : simpleDomains) {
            cardinality *= simpleDomain.getCardinality();
        }
        return cardinality;
    }

    @Override
    public IDomain getComponent(int index) {
        return simpleDomains[index];
    }

    @Override
    public int getNumberOfComponents() {
        return this.simpleDomains.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeDomain that = (CompositeDomain) o;
        return Arrays.equals(simpleDomains, that.simpleDomains);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(simpleDomains);
    }
}
