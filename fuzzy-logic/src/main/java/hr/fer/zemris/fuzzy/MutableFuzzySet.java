package hr.fer.zemris.fuzzy;


import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class MutableFuzzySet implements IFuzzySet {
    private double[] memberships;
    private IDomain domain;

    public MutableFuzzySet(IDomain domain) {
        this.domain = domain;
        memberships = new double[domain.getCardinality()];
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return memberships[domain.indexOfElement(element)];
    }

    public MutableFuzzySet set(DomainElement element, double membership) {
        if (memberships == null) memberships = new double[1];
        memberships[domain.indexOfElement(element)] = membership;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableFuzzySet that = (MutableFuzzySet) o;
        return Arrays.equals(memberships, that.memberships) &&
                Objects.equals(domain, that.domain);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(domain);
        result = 31 * result + Arrays.hashCode(memberships);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (domain instanceof SimpleDomain) {
            SimpleDomain simpleDomain = (SimpleDomain) domain;
            int index = 0;
            for (int i = simpleDomain.getFirst(); i < simpleDomain.getLast(); i++) {
                sb.append(String.format("d(%d)=%.6f%n", i, memberships[index++]));
            }
        } else {
            CompositeDomain compositeDomain = (CompositeDomain) domain;
            Iterator<DomainElement> iterator = compositeDomain.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                sb.append(String.format("d(%d)=%.6f%n", index, memberships[index]));
                index++;
            }
        }
        return sb.toString();
    }
}
