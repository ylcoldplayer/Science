package com.java.simulation;

import java.util.ArrayList;
import java.util.List;

public class PolicyIteration {
    public static void main(String[] args) {
        List<List<Double>> initialStates = new ArrayList<List<Double>>(10);

        List<Double> l1 = new ArrayList<Double>(2);
        l1.add(1.8);
        l1.add(1.8 - 4.0/3.0 - 0.0001);
        initialStates.add(l1);

        List<List<Double>> finalStates = new ArrayList<List<Double>>(initialStates.size());

        List<Double> lf = new ArrayList<Double>(2);
        lf.add(0.0);
        lf.add(0.0);
        for(Integer i = 0; i < initialStates.size(); ++i) finalStates.add(lf);

        Double alpha = 0.9;
        Double tol = 0.0001;

        List<Double> initStateDist = new ArrayList<Double>();
        initStateDist.add(0.1);
        initStateDist.add(0.9);

        for (Integer i = 0; i < initialStates.size(); ++i) {
            String fileName = "states_record_" + Integer.toString(i) + ".txt";
            IterationProcedure iterationProcedure = new IterationProcedure(initialStates.get(i), finalStates.get(i),
                    alpha, tol, initStateDist, fileName);

            Boolean convergent = iterationProcedure.iterate();
            if (convergent) {
                System.out.println(iterationProcedure.getFinalState().toString());
            }
        }
    }
}