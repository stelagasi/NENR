package hr.fer.zemris.fuzzy;


import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Domain implements IDomain {

    public static IDomain intRange(int first, int last) {
        return new SimpleDomain(first, last);
    }

    public static Domain combine(IDomain first, IDomain second) {
        if (first instanceof SimpleDomain && second instanceof SimpleDomain) {
            return new CompositeDomain((SimpleDomain) first, (SimpleDomain) second);
        } else throw new IllegalArgumentException();
    }

    public int indexOfElement(DomainElement element) {
        if (this instanceof SimpleDomain) {
            SimpleDomain simpleDomain = (SimpleDomain) this;
            if (element.getNumberOfComponents() != 1 || element.getComponentValue(0) < simpleDomain.getFirst()
                    || element.getComponentValue(0) > simpleDomain.getLast()) throw new IllegalArgumentException();
            return element.getComponentValue(0) - simpleDomain.getFirst();
        } else {
            if (element.getNumberOfComponents() < 2 || this.getNumberOfComponents() != element.getNumberOfComponents()) {
                throw new IllegalArgumentException();
            }
            CompositeDomain compositeDomain = (CompositeDomain) this;
            Iterator<DomainElement> iterator = compositeDomain.iterator();

            int index = 0;
            while (iterator.hasNext()) {
                if (element.equals(iterator.next())) {
                    return index;
                }
                index++;
            }
            throw new NoSuchElementException();
        }
    }

    public DomainElement elementForIndex(int index) {
        if (index < 0 || getCardinality() <= index) throw new IllegalArgumentException();
        if (this instanceof SimpleDomain) {
            SimpleDomain simpleDomain = (SimpleDomain) this;
            return new DomainElement(simpleDomain.getFirst() + index);
        } else {
            CompositeDomain compositeDomain = (CompositeDomain) this;
            Iterator<DomainElement> iterator = compositeDomain.iterator();

            int position = 0;
            while (iterator.hasNext()) {
                if (position == index) {
                    return iterator.next();
                }
                iterator.next();
                position++;
            }
            throw new NoSuchElementException();
        }
    }
}
