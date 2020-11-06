package hr.fer.zemris.fuzzy.demo.homework3;

import hr.fer.zemris.fuzzy.controlsystem.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        Defuzzifier def = new COADefuzzifier();
        IFuzzySystem fsAccel = new AccelerationFuzzySystem(def);
        IFuzzySystem fsHelm = new HelmFuzzySystem(def);

        int L=0,D=0,LK=0,DK=0,V=0,S=0;
        String line = null;
        while(true){
            if((line = input.readLine())!=null){
                if(line.charAt(0)=='K') break;
                Scanner s = new Scanner(line);
                L = s.nextInt();
                D = s.nextInt();
                LK = s.nextInt();
                DK = s.nextInt();
                V = s.nextInt();
                S = s.nextInt();
            }


            int acceleration = fsAccel.conclude(List.of(L, D, LK, DK, V, S));
            int helm = fsHelm.conclude(List.of(L, D, LK, DK, V, S));
            System.out.println(acceleration + " " + helm);
            System.out.flush();
        }
    }
}
