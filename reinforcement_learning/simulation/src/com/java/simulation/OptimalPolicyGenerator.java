package com.java.simulation;

import java.util.ArrayList;
import java.util.List;

public class OptimalPolicyGenerator {
    private Double alpha;

    public OptimalPolicyGenerator(Double alpha) {
        this.alpha = alpha;
    }

    public List<Double> getJMuR() {
        Double x = alpha/(1.0-alpha);
        Double y = 1.0/(1.0-alpha);
        List<Double> result = new ArrayList<Double>(2);
        result.add(x);
        result.add(y);
        return result;
    }

    public List<Double> getJMuL() {
        Double x = 1.0/(1.0-alpha);
        Double y = alpha/(1.0-alpha);
        List<Double> result = new ArrayList<Double>(2);
        result.add(x);
        result.add(y);
        return result;
    }

    public List<Double> getJMuW() {
        Double x = 1.0/(1.0-alpha);
        Double y = 1.0/(1.0-alpha);
        List<Double> result = new ArrayList<Double>(2);
        result.add(x);
        result.add(y);
        return result;
    }

    public List<Double> getJMuG() {
        Double x = 0.0;
        Double y = 0.0;
        List<Double> result = new ArrayList<Double>(2);
        result.add(x);
        result.add(y);
        return result;
    }

}
