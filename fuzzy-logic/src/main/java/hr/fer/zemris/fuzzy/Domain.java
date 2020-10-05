package hr.fer.zemris.fuzzy;


public abstract class Domain implements IDomain {

    public static IDomain intRange(int first, int last){
        return new SimpleDomain(first, last);
    }

    public static Domain combine(IDomain first, IDomain second){
        if(first instanceof SimpleDomain && second instanceof SimpleDomain) {
            return new CompositeDomain((SimpleDomain) first, (SimpleDomain) second);
        } else throw new IllegalArgumentException();
    }

    //todo kaj je ovo
    public int indexOfElement(DomainElement element){
        if(this instanceof SimpleDomain){
            SimpleDomain simpleDomain = (SimpleDomain) this;
            return element.getComponentValue(0) - simpleDomain.getFirst();
        } else {
            CompositeDomain compositeDomain = (CompositeDomain) this;
        }
    }

    public DomainElement elementForIndex(int index){
        return null;
    }
}
