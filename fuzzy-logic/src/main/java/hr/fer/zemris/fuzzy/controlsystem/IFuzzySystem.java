package hr.fer.zemris.fuzzy.controlsystem;

import java.util.List;

public interface IFuzzySystem {

    int conclude(List<Integer> values);
    void addRule(Rule rule);
    List<Rule> getRuleBase();
    int getNumberOfRules();
}
