/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.math;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Explicitly represented discrete probability distribution for a number of objects.
 */
public class WeightedDistribution {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeightedDistribution.class);

    private List<Measure> weights = new ArrayList<Measure>();
    private Measure[] probabilityDistribution;
    private boolean completed = false;

    /**
     * Add a new weighted object to the distribution.
     */
    public void add(Object object, double weight) {
        if (completed)
            throw new IllegalStateException("distribution is closed - cannot add");
        if (object == null) {
            LOGGER.warn("attempt to add a null object to distribution - ignored");
            return;
        }
        weights.add(new Measure(object, weight));
    }

    /**
     * Indicate that all members have been added, and make the distribution immutable.
     */
    public void complete() {
        if (completed)
            return;

        if (weights.isEmpty())
            throw new IllegalStateException("distribution is empty - cannot complete");

        completed = true;
        probabilityDistribution = new Measure[weights.size()];
        double totalWeight = 0;
        for (Measure m : weights)
            totalWeight += m.weight;
        int i = 0;
        double distribution = 0;
        for (Measure m : weights) {
            distribution += m.weight / totalWeight;
            probabilityDistribution[i++] = new Measure(m.object, distribution);
        }
    }

    /**
     * Get a random member of this distribution according to probabilities determined by each
     * member''s associated weight.
     * 
     * @return A random member of the distribution.
     */
    public Object randomMember() {
        double randomNumber = Math.random();

        int i = 0;
        for (; i < probabilityDistribution.length; i++)
            if (randomNumber <= probabilityDistribution[i].weight)
                break;
        // In case of roundoff issues.
        if (i == probabilityDistribution.length)
            --i;
        return probabilityDistribution[i].object;
    }

    /**
     * A number associated with an object.
     */
    private static class Measure {
        public final Object object;
        public final double weight;

        public Measure(Object object, double weight) {
            super();
            this.object = object;
            this.weight = weight;
        }

    }
}
