package com.java.simulation;

import java.util.List;
import java.util.Random;

public class InitStateGenerator {
    private List<Double> initStateDist;

    public InitStateGenerator(List<Double> initStateDist) {
        this.initStateDist = initStateDist;
    }

    // Decide which state to be updated in current iteration
    public Integer generate() {
        Random random = new Random();
        Double rv = random.nextDouble();

        Double cumulative = 0.0;
        for(Integer i = 0; i < initStateDist.size()-1; ++i) {
            cumulative += initStateDist.get(i);
            if(rv < cumulative) return i+1;
        }
        return initStateDist.size();
    }
}
