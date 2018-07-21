package com.java.simulation;

import java.util.List;

public class OptimalDecider {
    private List<Double> state;
    // Contraction factor in policy iteration
    private Double alpha;

    public OptimalDecider(List<Double> state, Double alpha) {
        this.state = state;
        this.alpha = alpha;
    }

    public List<Double> getOptimalLocalPolicyFunction() {
        OptimalPolicyGenerator generator = new OptimalPolicyGenerator(alpha);
        Double x = state.get(0);
        Double y = state.get(1);

        if (y < x - 1.0/alpha) return generator.getJMuL();
        else if (y > x + 1.0/alpha) return generator.getJMuR();
        else return generator.getJMuG();
    }
}
