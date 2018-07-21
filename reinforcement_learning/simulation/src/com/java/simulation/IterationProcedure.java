package com.java.simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class IterationProcedure {
    private List<Double> initialState;
    private List<Double> finalState;
    private Double alpha;
    private Double tol;
    private List<Double> initStateDist;
    private String fileName;
    private final static Integer maxIter = 10;
    private final static String SPACE = " ";
    private final static String ALPHA = "alppha";
    private final static String TOLERANCE = "tolerance";
    private final static String MAX_ITERATION = "max_iteration";
    private final static String INIT_STATE = "init_state";
    private final static String INIT_STATE_DIST = "init_state_dist";

    public IterationProcedure(List<Double> initialState, List<Double> finalState,
                              Double alpha, Double tol, List<Double> initStateDist, String fileName) {
        this.initialState = initialState;
        this.finalState = finalState;
        this.alpha = alpha;
        this.tol = tol;
        this.initStateDist = initStateDist;
        this.fileName = fileName;
    }

    //TODO
    public boolean iterate() {
        List<Double> currentState = initialState;
        List<Double> nextState = initialState;
        int iterCount = 0;
        List<Double> optimalValue = new ArrayList<Double>(2);
        optimalValue.add(0.0);
        optimalValue.add(0.0);

        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileName, true));
            String generalStatsInfo = ALPHA + SPACE + TOLERANCE + SPACE + MAX_ITERATION
                    + SPACE + INIT_STATE + SPACE + INIT_STATE_DIST;
            String generalStats = Double.toString(alpha) + SPACE + Double.toString(tol) + SPACE
                    + Integer.toString(maxIter) + SPACE + initialState.toString() + SPACE + initStateDist.toString();
            out.println(generalStatsInfo);
            out.println(generalStats);

            do {
                iterCount += 1;
                Double stepSize = 1.0/(double)iterCount;
                OptimalDecider optimalDecider = new OptimalDecider(currentState, alpha);

                // Decide the greedy policy for current state
                List<Double> jGreedy = optimalDecider.getOptimalLocalPolicyFunction();

                // Update the value function
                StateUpdater stateUpdater =
                        new StateUpdater(currentState, nextState, jGreedy, stepSize, initStateDist);
                stateUpdater.update();

                currentState = stateUpdater.getCurrentState();
                nextState = stateUpdater.getNextState();
                finalState = nextState;

                String stat = Integer.toString(iterCount) + SPACE + Double.toString(currentState.get(0)) + SPACE
                        + Double.toString(currentState.get(1));
                out.println(stat);

                if(maxNorm(currentState, optimalValue) <= tol) return true;

            } while(iterCount < maxIter);

            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    // Calculate the maxNorm of two state-vectors
    public double maxNorm(List<Double> j1, List<Double> j2) {
        Double x_diff = Math.abs(j1.get(0) - j2.get(0));
        Double y_diff = Math.abs(j1.get(1) - j2.get(1));
        return Math.max(x_diff, y_diff);
    }

    public List<Double> getFinalState() {
        return finalState;
    }
}
