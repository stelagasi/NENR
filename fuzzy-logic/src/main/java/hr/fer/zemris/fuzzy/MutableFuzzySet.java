package hr.fer.zemris.fuzzy;

public class MutableFuzzySet implements IFuzzySet {
    private double[] memberships;
    private IDomain domain;

    public MutableFuzzySet(IDomain domain) {
        this.domain = domain;
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


    //todo što je double tu??
    public MutableFuzzySet set(DomainElement element, double index) {
        return null
    }
}
