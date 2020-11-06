package hr.fer.zemris.fuzzy.controlsystem;

import hr.fer.zemris.fuzzy.IFuzzySet;

public interface Defuzzifier {

    int defuzzy(IFuzzySet fuzzySet);

}
