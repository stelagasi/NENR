package hr.fer.zemris.fuzzy;


import java.util.Iterator;

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

    //todo što točno vraća? i jel dobro ovo s double?
    public MutableFuzzySet set(DomainElement element, double membership) {
        if (memberships == null) memberships = new double[1];
        memberships[domain.indexOfElement(element)] = membership;
        return this;
    }

    //todo jel tu zaista samo simpledomain? i kako radi ovo čudo za composite?
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (domain instanceof SimpleDomain) {
            SimpleDomain simpleDomain = (SimpleDomain) domain;
            for (int i = simpleDomain.getFirst(); i < simpleDomain.getLast(); i++) {
                sb.append(String.format("d(%d)=%.4f%n", i, memberships[i]));
            }
        } else {
            CompositeDomain compositeDomain = (CompositeDomain) domain;
            Iterator<DomainElement> iterator = compositeDomain.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                sb.append(String.format("d(%d)=%.4f%n", index, memberships[index]));
                index++;
            }
        }
        return sb.toString();
    }
}
