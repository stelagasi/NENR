package hr.fer.zemris.fuzzy;

public class DomainElement {
    int[] values;

    DomainElement(int[] values){
        this.values = values;
    }

    public int getNumberOfComponents(){
        return values.length;
    }

    public int getComponentValue(int index){
        return values[index];
    }

    public int hashCode(){
        return values.hashCode();
    }

    public boolean equals(Object object){
        if(!object instanceof DomainElement) return false;
        DomainElement domainElement = (DomainElement) object;

        if(this.values.length != domainElement.values.length) return false;

        for(int i = 0; i < this.values.length; i++){
            if(this.values[i] != domainElement.values[i]) return false;
        }
        return true;
    }

    public String toString(){ //TODO

    }

    public static DomainElement of(int[] values){
        return new DomainElement(values);
    }
}
