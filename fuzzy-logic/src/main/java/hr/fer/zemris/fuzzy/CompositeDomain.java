package hr.fer.zemris.fuzzy;

import java.util.Iterator;

public class CompositeDomain extends Domain {

    private final IDomain[] domains;

    public CompositeDomain(IDomain... domains) {
        this.domains = domains;
    }

    //todo može biti nula domains.length
//    @Override
//    public Iterator<DomainElement> iterator() {
//        return new Iterator<>() {
//            private int current = 0;
//
//            @Override
//            public boolean hasNext() {
//                return current < getCardinality();
//            }
//
//            @Override
//            public DomainElement next() {
//                for(int i = 0; i < getNumberOfComponents(); i++){
//                    for(int j = 0; j < getComponent(i).getCardinality(); j++){
//                        if(i * getNumberOfComponents() + j == current){
//                            return new DomainElement(g)
//                        }
//                    }
//                }
//            }
//        };
//    }

    @Override
    public int getCardinality() {
        if(domains.length == 0) return 0;

        int cardinality = 1;
        for(IDomain domain : domains){
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
