package hr.fer.zemris.fuzzy.controlsystem;

import java.util.List;
import java.util.Map;

import static hr.fer.zemris.fuzzy.controlsystem.ConclusionMethod.*;
import static hr.fer.zemris.fuzzy.controlsystem.InputValue.*;
import static hr.fer.zemris.fuzzy.controlsystem.RuleHelper.*;

public class AccelerationFuzzySystem extends FuzzySystem {

    public AccelerationFuzzySystem(Defuzzifier defuzzifier) {
        super(defuzzifier);
        this.addRule(new Rule(Map.of(SLOW, V), ACCELERATE, MINIMUM_INFERENCE_ENGINE));
        this.addRule(new Rule(Map.of(FAST, V), DECELERATE, MINIMUM_INFERENCE_ENGINE));
//        this.addRule(new Rule(Map.of(CLOSE, LK), DECELERATE, MINIMUM_INFERENCE_ENGINE));
//        this.addRule(new Rule(Map.of(CLOSE, DK), DECELERATE, MINIMUM_INFERENCE_ENGINE));
    }
}
