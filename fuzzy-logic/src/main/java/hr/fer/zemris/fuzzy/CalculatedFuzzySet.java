package hr.fer.zemris.fuzzy;


public class CalculatedFuzzySet implements IFuzzySet {
    private IDomain domain;
    private IIntUnaryFunction intUnaryFunction;


    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction intUnaryFunction) {
        if (domain == null || intUnaryFunction == null) throw new IllegalArgumentException();
        if (!(domain instanceof SimpleDomain)) throw new IllegalArgumentException();
        this.domain = domain;
        this.intUnaryFunction = intUnaryFunction;
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return this.intUnaryFunction.valueAt(element.getComponentValue(0));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (domain instanceof SimpleDomain) {
            SimpleDomain simpleDomain = (SimpleDomain) domain;
            int index = 0;
            for (int i = simpleDomain.getFirst(); i < simpleDomain.getLast(); i++) {
                sb.append(String.format("d(%d)=%.6f%n", i, getValueAt(new DomainElement(index++))));
            }
        }
        return sb.toString();
    }
}
