package hr.fer.zemris.fuzzy.demo.homework3;

import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.controlsystem.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static hr.fer.zemris.fuzzy.Operations.binaryOperation;
import static hr.fer.zemris.fuzzy.Operations.zadehOr;

public class Task2 {
    public static void main(String[] args) {

        Defuzzifier def = new COADefuzzifier();
        IFuzzySystem fsAccel = new AccelerationFuzzySystem(def);
        IFuzzySystem fsHelm = new HelmFuzzySystem(def);
        List<Integer> values = new ArrayList<>();
        List<Rule> rulesAccel = fsAccel.getRuleBase();
        List<Rule> rulesHelm = fsHelm.getRuleBase();

        Scanner s = new Scanner(System.in);

        System.out.println("Unesite L, D, LK, DK, V, S: ");
        for (int i = 0; i < 6; i++) {
            values.add(s.nextInt());
        }

        IFuzzySet conclusionAccel = rulesAccel.get(0).apply(values);
        for (int i = 1; i < rulesAccel.size(); i++) {
            conclusionAccel = binaryOperation(conclusionAccel, rulesAccel.get(i).apply(values), zadehOr());
        }

        IFuzzySet conclusionHelm = rulesHelm.get(0).apply(values);
        for (int i = 1; i < rulesHelm.size(); i++) {
            conclusionHelm = binaryOperation(conclusionHelm, rulesHelm.get(i).apply(values), zadehOr());
        }

        System.out.println("Akceleracija: ");
        System.out.println(conclusionAccel);
        System.out.println(def.defuzzy(conclusionAccel));
        System.out.println("------------------------------------");
        System.out.println("Kormilo: ");
        System.out.println(conclusionHelm);
        System.out.println(def.defuzzy(conclusionHelm));
    }
}
