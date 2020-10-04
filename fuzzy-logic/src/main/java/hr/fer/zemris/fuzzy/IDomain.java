package hr.fer.zemris.fuzzy;

public interface IDomain extends Iterable<DomainElement>{

    int getCardinality();
    IDomain getComponent(int index); //jel je index?
    int getNumberOfComponents();
    int indexOfElement(DomainElement element);
    DomainElement elementForIndex(int index); //??

}
