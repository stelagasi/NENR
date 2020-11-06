package hr.fer.zemris.fuzzy.controlsystem;

import java.util.List;
import java.util.Map;

import static hr.fer.zemris.fuzzy.controlsystem.ConclusionMethod.*;
import static hr.fer.zemris.fuzzy.controlsystem.InputValue.*;
import static hr.fer.zemris.fuzzy.controlsystem.RuleHelper.*;

public class HelmFuzzySystem extends FuzzySystem {

    public HelmFuzzySystem(Defuzzifier defuzzifier) {
        super(defuzzifier);
        this.addRule(new Rule(Map.of(CLOSE, LK), TURN_RIGHT, MINIMUM_INFERENCE_ENGINE));
        this.addRule(new Rule(Map.of(CLOSE, DK), TURN_LEFT, MINIMUM_INFERENCE_ENGINE));
        this.addRule(new Rule(Map.of(REALLY_CLOSE, D), TURN_LEFT, MINIMUM_INFERENCE_ENGINE));
        this.addRule(new Rule(Map.of(REALLY_CLOSE, L), TURN_RIGHT, MINIMUM_INFERENCE_ENGINE));
        this.addRule(new Rule(Map.of(WRONG_DIRECTION, S), TURN_LEFT, MINIMUM_INFERENCE_ENGINE));
    }
}
