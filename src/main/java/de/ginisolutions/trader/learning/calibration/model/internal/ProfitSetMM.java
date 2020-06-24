package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.ParameterMM;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;

import javax.validation.constraints.NotBlank;
import java.time.Duration;

public class ProfitSetMM implements ProfitSet {
    @NotBlank
    private double profit;

    @NotBlank
    private Duration duration;

    @NotBlank
    private ParameterMM parameterMM;

    public ProfitSetMM(double profit, ParameterMM parameterMM) {
        this.profit = profit;
        this.parameterMM = parameterMM;
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
        return parameterMM;
    }

    public void setParams(StrategyParameter params) {
        this.parameterMM = (ParameterMM) params;
    }
}
