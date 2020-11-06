package hr.fer.zemris.fuzzy.controlsystem;

import hr.fer.zemris.fuzzy.*;

import java.util.List;
import java.util.Map;

import static hr.fer.zemris.fuzzy.controlsystem.ConclusionMethod.*;
import static java.lang.Double.MAX_VALUE;

public class Rule {
    private final Map<IFuzzySet, InputValue> antecedents;
    private final IFuzzySet consequence;
    private final ConclusionMethod conclusionMethod;

    public Rule(Map<IFuzzySet, InputValue> antecedents, IFuzzySet consequence, ConclusionMethod conclusionMethod) {
        this.antecedents = antecedents;
        this.consequence = consequence;
        this.conclusionMethod = conclusionMethod;
    }

    public MutableFuzzySet apply(List<Integer> values) {
        double antecedentResult = MAX_VALUE;

        for (var antecedent : antecedents.entrySet()) {
            antecedentResult = Math.min(antecedentResult, antecedent.getKey().getValueAt(new DomainElement(values.get(antecedent.getValue().getOrder()))));
        }

        MutableFuzzySet result = new MutableFuzzySet(consequence.getDomain());

        for (int i = 0; i < consequence.getDomain().getCardinality(); i++) {
            DomainElement domainElement = consequence.getDomain().elementForIndex(i);
            if (conclusionMethod == PRODUCT_INFERENCE_ENGINE) {
                result.set(domainElement, consequence.getValueAt(domainElement) * antecedentResult);
            } else if (conclusionMethod == MINIMUM_INFERENCE_ENGINE) {
                result.set(domainElement, Math.min(consequence.getValueAt(domainElement), antecedentResult));
           }
        }

        return result;
    }

    public Map<IFuzzySet, InputValue> getAntecedents() {
        return antecedents;
    }

    public IFuzzySet getConsequence() {
        return consequence;
    }
}
