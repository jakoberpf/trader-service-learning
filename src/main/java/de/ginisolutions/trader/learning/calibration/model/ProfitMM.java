package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.ParameterMovingMomentum;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;

import javax.validation.constraints.NotBlank;
import java.time.Duration;

public class ProfitMM implements ProfitSet {
    @NotBlank
    private double profit;

    @NotBlank
    private Duration duration;

    @NotBlank
    private ParameterMovingMomentum parameterMovingMomentum;

    public ProfitMM(double profit, ParameterMovingMomentum parameterMovingMomentum) {
        this.profit = profit;
        this.parameterMovingMomentum = parameterMovingMomentum;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public StrategyParameter getParams() {
        return parameterMovingMomentum;
    }

    public void setParams(StrategyParameter params) {
        this.parameterMovingMomentum = (ParameterMovingMomentum) params;
    }
}
