package com.java.simulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.java.simulation.Constants.*;

public class IterationProcedure {
    private List<Double> initialState;
    private List<Double> finalState;
    private Double alpha;
    private Double tol;
    private List<Double> initStateDist;
    private String fileName;
    private final static Integer maxIter = 1000000000;

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
        int iterCount = 1;
        List<Double> optimalValue = new ArrayList<Double>(2);
        optimalValue.add(0.0);
        optimalValue.add(0.0);

        try {
            File file = new File(fileName);
            boolean existed = Files.deleteIfExists(file.toPath());
            PrintWriter out = new PrintWriter(new FileWriter(fileName, true));
            String generalStatsInfo = ALPHA + SPACE + TOLERANCE + SPACE + MAX_ITERATION
                    + SPACE + INIT_STATE + SPACE + INIT_STATE_DIST;
            String generalStats = Double.toString(alpha) + SPACE + Double.toString(tol) + SPACE
                    + Integer.toString(maxIter) + SPACE + initialState.toString() + SPACE + initStateDist.toString();
            //out.println(generalStatsInfo);
            //out.println(generalStats);

            String statsInfo = ITER_COUNT + SPACE + CURRENT_STATE_X + SPACE + CURRENT_STATE_Y + SPACE + STATE_TO_UPDATE
                    + SPACE + GREEDY_X + SPACE + GREEDY_Y;
            out.println(statsInfo);

            do {
                iterCount += 1;
                //Double stepSize = 1.0/(double)iterCount;
                Double stepSize = 0.00001;
                OptimalDecider optimalDecider = new OptimalDecider(currentState, alpha);

                // Decide the greedy policy for current state
                List<Double> jGreedy = optimalDecider.getOptimalLocalPolicyFunction();

                StateUpdater stateUpdater =
                        new StateUpdater(currentState, nextState, jGreedy, stepSize, initStateDist);
                Integer stateToUpdate = stateUpdater.getStateToUpdate();

                // Log state update info
                if(iterCount % 10000 == 0) {
                    String stat = Integer.toString(iterCount) + SPACE + Double.toString(currentState.get(0)) + SPACE
                            + Double.toString(currentState.get(1)) + SPACE + Integer.toString(stateToUpdate) + SPACE
                            + Double.toString(jGreedy.get(0)) + SPACE + Double.toString(jGreedy.get(1));
                    out.println(stat);
                }

                // Update the value function
                stateUpdater.update();

                currentState = stateUpdater.getCurrentState();
                nextState = stateUpdater.getNextState();
                finalState = nextState;

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
