package hr.fer.zemris.fuzzy;

public class CalculatedFuzzySet implements IFuzzySet {
    private IDomain domain;
    private IIntUnaryFunction intUnaryFunction;


    //todo može imati nula jednih ili drugih?
    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction intUnaryFunction) {
        this.domain = domain;
        this.intUnaryFunction = intUnaryFunction;
    }

    //todo
    @Override
    public IDomain getDomain() {
        return null;
    }

    //todo
    @Override
    public double getValueAt(DomainElement element) {
        return 0;
    }
}
