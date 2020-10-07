package hr.fer.zemris.fuzzy;

import java.util.NoSuchElementException;

public class CalculatedFuzzySet implements IFuzzySet {
    private double[] memberships;
    private IDomain domain;


    //todo može imati nula jednih ili drugih?
    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction intUnaryFunction) {
        if (domain == null || intUnaryFunction == null) throw new IllegalArgumentException();
        if (!(domain instanceof SimpleDomain)) throw new IllegalArgumentException();
        this.domain = domain;
        this.memberships = calculateFuzzySet((SimpleDomain) domain, intUnaryFunction);
    }

    private double[] calculateFuzzySet(SimpleDomain domain, IIntUnaryFunction intUnaryFunction) {
        double[] memberships = new double[domain.getCardinality()];
        int index = 0;
        for (int i = domain.getFirst(); i < domain.getLast(); i++) {
            memberships[index] = intUnaryFunction.valueAt(index++);
        }
        return memberships;
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        SimpleDomain simpleDomain = (SimpleDomain) domain;
        if (element.getNumberOfComponents() != 1) throw new IllegalArgumentException();
        if(simpleDomain.getFirst() < element.getComponentValue(0)
                || simpleDomain.getLast() <= element.getComponentValue(0))
            throw new NoSuchElementException();
        int index = 0;
        int x = element.getComponentValue(0);
        for(int i = simpleDomain.getFirst(); i < simpleDomain.getLast(); i++){
            if(x == i) return memberships[index];
            index++;
        }
        throw new NoSuchElementException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (domain instanceof SimpleDomain) {
            SimpleDomain simpleDomain = (SimpleDomain) domain;
            int index = 0;
            for (int i = simpleDomain.getFirst(); i < simpleDomain.getLast(); i++) {
                sb.append(String.format("d(%d)=%.4f%n", i, memberships[index++]));
            }
        }
        return sb.toString();
    }
}
