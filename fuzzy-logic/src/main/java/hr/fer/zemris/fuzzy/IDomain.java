package hr.fer.zemris.fuzzy;

public interface IDomain<T> extends Iterable<T>{

    int getCardinality();
    IDomain getComponent(int index); //jel je index?
    int getNumberOfComponents();
    int indexOfElement(DomainElement element);
    DomainElement elementForIndex(int index); //??

}
