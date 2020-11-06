package hr.fer.zemris.fuzzy.controlsystem;

import hr.fer.zemris.fuzzy.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.IFuzzySet;

import static hr.fer.zemris.fuzzy.StandardFuzzySets.*;
import static hr.fer.zemris.fuzzy.controlsystem.Domains.*;

public class RuleHelper {
    public static final IFuzzySet SLOW = new CalculatedFuzzySet(SPEED, lFunction(30, 50));
    public static final IFuzzySet FAST = new CalculatedFuzzySet(SPEED, gammaFunction(45, 60));

    public static final IFuzzySet ACCELERATE = new CalculatedFuzzySet(ACCELERATION, gammaFunction(50, 70));
    public static final IFuzzySet DECELERATE = new CalculatedFuzzySet(ACCELERATION, lFunction(30, 45));

    public static final IFuzzySet CLOSE = new CalculatedFuzzySet(DISTANCE, lFunction(40, 80));
    public static final IFuzzySet REALLY_CLOSE = new CalculatedFuzzySet(DISTANCE, lFunction(20, 30));
    public static final IFuzzySet TURN_LEFT = new CalculatedFuzzySet(ANGLE, gammaFunction(120, 150));
    public static final IFuzzySet TURN_RIGHT = new CalculatedFuzzySet(ANGLE, lFunction(30, 40));

    public static final IFuzzySet WRONG_DIRECTION = new CalculatedFuzzySet(DIRECTION, lFunction(0, 1));

    private RuleHelper(){}
}
