package com.java.simulation;

import java.util.ArrayList;
import java.util.List;

public class StateUpdater {
    private List<Double> currentState;
    private List<Double> nextState;
    private List<Double> greedyState;
    // $\gamma_t$ in optimistic policy iteration algorithm
    private Double stepSize;
    private Boolean useRandomEffect;
    private List<Double> iniStateDist;

    public StateUpdater(List<Double> currentState, List<Double> nextState, List<Double> greedyState,
                        Double stepSize, List<Double> iniStateDist) {
        this.currentState = currentState;
        this.nextState = nextState;
        this.greedyState = greedyState;
        this.stepSize = stepSize;
        this.useRandomEffect = false;
        this.iniStateDist = iniStateDist;
    }

    public void update() {
        List<Double> randomEffect = new ArrayList<Double>(2);
        randomEffect.add(0.0);
        randomEffect.add(0.0);
        if(useRandomEffect) {}

        List<Double> currentTemp = currentState;
        currentState = nextState;

        // update nextState
        Integer stateToUpdate = getStateToUpdate();
        if(stateToUpdate == 1) {
            Double x = (1.0 - stepSize)*currentTemp.get(0) + stepSize*(greedyState.get(0) + randomEffect.get(0));
            Double y = nextState.get(1);
            nextState.set(0, x);
            nextState.set(1, y);
        } else if(stateToUpdate == 2) {
            Double x = nextState.get(0);
            Double y = (1.0 - stepSize)*currentTemp.get(1) + stepSize*(greedyState.get(1) + randomEffect.get(1));
            nextState.set(0, x);
            nextState.set(1, y);
        }
    }

    public List<Double> getCurrentState() {
        return currentState;
    }

    public List<Double> getNextState() {
        return nextState;
    }

    public Integer getStateToUpdate() {
        InitStateGenerator initStateGenerator = new InitStateGenerator(iniStateDist);
        return initStateGenerator.generate();
    }
}
