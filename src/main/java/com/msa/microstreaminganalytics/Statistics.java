package com.msa.microstreaminganalytics;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {

    private double mean;
    private double median;
    private double mode;
    private double standardDeviation;
    private double[] quartiles;
    private double maximum;
    private double minimum;

    public Statistics(@JsonProperty("mean")double mean, @JsonProperty("median")double median,
                      @JsonProperty("mode")double mode, @JsonProperty("standardDeviation")double standardDeviation,
                      @JsonProperty("quartiles")double[] quartiles, @JsonProperty("maximum")double maximum,
                      @JsonProperty("minimum")double minimum) {
        this.mean = mean;
        this.median = median;
        this.mode = mode;
        this.standardDeviation = standardDeviation;
        this.quartiles = quartiles;
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public double getMode() {
        return mode;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public double[] getQuartiles() {
        return quartiles;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getMinimum() {
        return minimum;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "mean=" + mean +
                ", median=" + median +
                ", mode=" + mode +
                ", standardDeviation=" + standardDeviation +
                ", quartiles=" + quartiles +
                ", maximum=" + maximum +
                ", minimum=" + minimum +
                '}';
    }
}
