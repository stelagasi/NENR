package hr.fer.zemris.fuzzy;

import java.util.Arrays;

public class DomainElement {
    private final int[] values;

    DomainElement(int... values) {
        if (values.length <= 0) throw new IllegalArgumentException();
        this.values = values;
    }

    public int getNumberOfComponents() {
        return values.length;
    }

    public int getComponentValue(int index) {
        return values[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainElement that = (DomainElement) o;
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        if (getNumberOfComponents() == 1) return Integer.toString(this.values[0]);
        return Arrays.toString(this.values).replace('[', '(').replace(']', ')');
    }

    public static DomainElement of(int... values) {
        return new DomainElement(values);
    }
}
