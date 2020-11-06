package hr.fer.zemris.fuzzy.controlsystem;

import hr.fer.zemris.fuzzy.IFuzzySet;
import java.util.ArrayList;
import java.util.List;

import static hr.fer.zemris.fuzzy.Operations.*;

public class FuzzySystem implements IFuzzySystem {

    private final List<Rule> ruleBase;
    private final Defuzzifier defuzzifier;

    public FuzzySystem(Defuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
        this.ruleBase = new ArrayList<>();
    }

    @Override
    public void addRule(Rule rule) {
        ruleBase.add(rule);
    }

    @Override
    public int conclude(List<Integer> values) {
        if(ruleBase.size() == 0) throw new IllegalStateException("No rules were given");

        IFuzzySet conclusion = ruleBase.get(0).apply(values);
        for (int i = 1; i < ruleBase.size(); i++) {
            conclusion = binaryOperation(conclusion, ruleBase.get(i).apply(values), zadehOr());
        }
        return defuzzifier.defuzzy(conclusion);
    }

    public List<Rule> getRuleBase() {
        return ruleBase;
    }

    public int getNumberOfRules() { return ruleBase.size(); }

    public Defuzzifier getDefuzzifier() {
        return defuzzifier;
    }
}
