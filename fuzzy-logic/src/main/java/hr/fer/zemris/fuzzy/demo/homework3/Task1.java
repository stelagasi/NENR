package hr.fer.zemris.fuzzy.demo.homework3;

import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.controlsystem.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {

        Defuzzifier def = new COADefuzzifier();
        IFuzzySystem fsAccel = new AccelerationFuzzySystem(def);
        IFuzzySystem fsHelm = new HelmFuzzySystem(def);
        int numberOfAccelRules = fsAccel.getNumberOfRules();
        int numberOfHelmRules = fsHelm.getNumberOfRules();
        List<Integer> values = new ArrayList<>();

        Scanner s = new Scanner(System.in);

        System.out.println("Odaberite jedno od pravila 0-" + (numberOfAccelRules + numberOfHelmRules - 1) + ":");
        int numberOfRule = s.nextInt();

        if(numberOfRule >= (numberOfAccelRules + numberOfHelmRules) || numberOfRule < 0) throw new IllegalArgumentException();

        Rule rule;
        if(numberOfRule >= numberOfAccelRules) {
            rule = fsHelm.getRuleBase().get(numberOfRule-numberOfAccelRules);
        } else {
            rule = fsAccel.getRuleBase().get(numberOfRule);
        }

        System.out.println("Unesite L, D, LK, DK, V, S: ");
        for (int i = 0; i < 6; i++) {
            values.add(s.nextInt());
        }
        MutableFuzzySet conclusion = rule.apply(values);
        System.out.print(conclusion);
        System.out.print(def.defuzzy(conclusion));
    }
}

