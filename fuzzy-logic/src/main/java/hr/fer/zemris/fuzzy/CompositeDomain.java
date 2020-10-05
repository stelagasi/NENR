package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CompositeDomain extends Domain {

    private final IDomain[] domains;

    public CompositeDomain(IDomain... domains) {
        this.domains = domains;
    }

    //todo može biti nula domains.length
    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < getCardinality();
            }

            @Override
            public DomainElement next() {
                ArrayList<Integer> values = new ArrayList<>();
                int numberOfComponents = getNumberOfComponents();
                for (int i = numberOfComponents - 1; i >= 0; i--) {
                    if (getComponent(i).getCardinality() > current) {
                        for (int j = 0; j < numberOfComponents - i; j++) {
                            if (getComponent(j) instanceof SimpleDomain) {
                                SimpleDomain simpleComponent = (SimpleDomain) getComponent(j);
                                values.add(simpleComponent.getFirst());
                            } else {
                                CompositeDomain compositeComponent = (CompositeDomain) getComponent(j);
                                values.addAll(compositeComponent.getFirstCompositeElement());
                            }
                        }
                        //todo dodaj i trenutni član
                        return new DomainElement(values);
                    }
                }
            }
        };
}

    public ArrayList<Integer> getFirstCompositeElement() {
        ArrayList<Integer> values = new ArrayList<>();
        for(int i = 0; i < getNumberOfComponents(); i++){
            if(getComponent(i) instanceof SimpleDomain){
                SimpleDomain simpleComponent = (SimpleDomain) getComponent(i);
                values.add(simpleComponent.getFirst());
            } else {
                CompositeDomain compositeComponent = (CompositeDomain) getComponent(i);
                values.addAll(compositeComponent.getFirstCompositeElement());
            }
        }
        return values;
    }

    @Override
    public int getCardinality() {
        if (domains.length == 0) return 0;

        int cardinality = 1;
        for (IDomain domain : domains) {
            cardinality *= domain.getCardinality();
        }
        return cardinality;
    }

    @Override
    public IDomain getComponent(int index) {
        return domains[index];
    }

    @Override
    public int getNumberOfComponents() {
        return this.domains.length;
    }
}
