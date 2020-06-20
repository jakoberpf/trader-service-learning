package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.ParameterRelativeStrengthIndex;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;

import javax.validation.constraints.NotBlank;
import java.time.Duration;

public class ProfitRSI implements ProfitSet {
    @NotBlank
    private double profit;

    @NotBlank
    private Duration duration;

    @NotBlank
    private ParameterRelativeStrengthIndex parameterRelativeStrengthIndex;

    public ProfitRSI(double profit, ParameterRelativeStrengthIndex parameterRelativeStrengthIndex) {
        this.profit = profit;
        this.parameterRelativeStrengthIndex = parameterRelativeStrengthIndex;
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
        return parameterRelativeStrengthIndex;
    }

    public void setParams(StrategyParameter params) {
        this.parameterRelativeStrengthIndex = (ParameterRelativeStrengthIndex) params;
    }
}
