package hr.fer.zemris.fuzzy.controlsystem;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class COADefuzzifier implements Defuzzifier {

    public COADefuzzifier() {
    }

    @Override
    public int defuzzy(IFuzzySet fuzzySet) {
        double sum1 = 0.0;
        double sum2 = 0.0;

        for(DomainElement element : fuzzySet.getDomain()){
            sum1 += element.getComponentValue(0) * fuzzySet.getValueAt(element);
            sum2 += fuzzySet.getValueAt(element);
        }
        return (int) (sum1/sum2);
    }
}
