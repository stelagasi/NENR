package hr.fer.zemris.fuzzy;

import java.util.Iterator;

public class SimpleDomain extends Domain {

    private final int first;
    private final int last;

    public SimpleDomain(int first, int last) {
        if (first >= last) throw new IllegalArgumentException();
        this.first = first;
        this.last = last;
    }

    @Override
    public int getCardinality() {
        return last - first;
    }

    @Override
    public IDomain getComponent(int index) {
        return this;
    }

    @Override
    public int getNumberOfComponents() {
        return 1;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {
            private int current = first;

            @Override
            public boolean hasNext() {
                return current < last;
            }

            @Override
            public DomainElement next() {
                return new DomainElement(current++);
            }
        };
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }
}
