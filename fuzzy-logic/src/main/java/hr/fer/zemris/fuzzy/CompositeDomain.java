package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.Iterator;

public class CompositeDomain extends Domain {

    private final SimpleDomain[] simpleDomains;

    public CompositeDomain(SimpleDomain... simpleDomains) {
        this.simpleDomains = simpleDomains;
    }

    //todo može biti nula simpleDomains.length
    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < getCardinality();
            }

            //todo probaj s primitivnim rijesiti
            @Override
            public DomainElement next() {
                ArrayList<Integer> values = new ArrayList<>();
                int position = current;
                for (int i = getNumberOfComponents() - 1; i <= 0; i--) {
                    SimpleDomain simpleDomain = (SimpleDomain) getComponent(i);
                    int index = position % simpleDomain.getCardinality();
                    position /= simpleDomain.getCardinality();
                    values.add(simpleDomain.getFirst() + index);
                }
                int[] intValues = new int[values.size()];
                for (int k = values.size() - 1; k >= 0; k--) {
                    intValues[values.size() - k - 1] = values.get(k);
                }
                current++;
                return new DomainElement(intValues);
            }
        };
    }

//    int position = current;
//    ArrayList<Integer> values = new ArrayList<>();
//                for (int i = getNumberOfComponents() - 1; i >= 0; i--) {
//        if (getComponent(i).getCardinality() > position) {
//            for (int j = 0; j < getNumberOfComponents() - i - 1; j++) {
//                SimpleDomain simpleComponent = (SimpleDomain) getComponent(j);
//                values.add(simpleComponent.getFirst());
//            }
//            SimpleDomain simpleComponent = (SimpleDomain) getComponent(i);
//            values.add(simpleComponent.getFirst() + position);
//            for (int k = i+1; k < getNumberOfComponents(); k++) {
//                simpleComponent = (SimpleDomain) getComponent(k);
//                position += simpleComponent.getCardinality();
//                simpleComponent = (SimpleDomain) getComponent(k);
//                if (simpleComponent.getCardinality())
//                    values.add(simpleComponent.getFirst())
//                values.add(simpleComponent.getFirst() + position);
//            }
//
//            int[] intValues = new int[values.size()];
//            for (int w = 0; w < values.size(); w++) {
//                intValues[w] = values.get(w);
//            }
//            return new DomainElement(intValues);
//        } else {
//            position -= getComponent(i).getCardinality() - 1;
//        }
//    }

    @Override
    public int getCardinality() {
        if (simpleDomains.length == 0) return 0;

        int cardinality = 1;
        for (SimpleDomain simpleDomain : simpleDomains) {
            cardinality *= simpleDomain.getCardinality();
        }
        return cardinality;
    }

    @Override
    public IDomain getComponent(int index) {
        return simpleDomains[index];
    }

    @Override
    public int getNumberOfComponents() {
        return this.simpleDomains.length;
    }
}
