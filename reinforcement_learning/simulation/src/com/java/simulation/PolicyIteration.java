package com.java.simulation;

import java.util.ArrayList;
import java.util.List;

public class PolicyIteration {
    public static void main(String[] args) {
        List<List<Double>> initialStates = new ArrayList<List<Double>>(10);
        List<Double> l1 = new ArrayList<Double>(2);
        l1.add(1.34);
        l1.add(1.34 - 4.0/3.0 - 0.001);
        initialStates.add(l1);
        List<List<Double>> finalStates = new ArrayList<List<Double>>(initialStates.size());
        List<Double> l2 = new ArrayList<Double>(2);
        l2.add(0.0);
        l2.add(0.0);
        finalStates.add(l2);

        Double alpha = 0.75;
        Double tol = 0.00001;

        List<Double> initStateDist = new ArrayList<Double>();
        initStateDist.add(0.5);
        initStateDist.add(0.5);

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