package hr.fer.zemris.fuzzy;

public class CalculatedFuzzySet implements IFuzzySet {
    private IDomain domain;


    //todo može imati nula jednih ili drugih?
    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction intUnaryFunction) {
        this.domain = domain;
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    //todo
    @Override
    public double getValueAt(DomainElement element) {
        return 0;
    }

    //todo
    @Override
    public String toString() {
        return null;
    }
}
