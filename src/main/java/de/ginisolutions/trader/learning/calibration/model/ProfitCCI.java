package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.ParameterCommodityChannelIndex;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;

import javax.validation.constraints.NotBlank;
import java.time.Duration;

public class ProfitCCI {
    @NotBlank
    private double profit;

    @NotBlank
    private Duration duration;

    @NotBlank
    private ParameterCommodityChannelIndex parameterCommodityChannelIndex;

    public ProfitCCI(double profit, ParameterCommodityChannelIndex parameterCommodityChannelIndex) {
        this.profit = profit;
        this.parameterCommodityChannelIndex = parameterCommodityChannelIndex;
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
        return parameterCommodityChannelIndex;
    }

    public void setParams(StrategyParameter params) {
        this.parameterCommodityChannelIndex = (ParameterCommodityChannelIndex) params;
    }

    @Override
    public String toString() {
        return "Profit2CCI{" +
                "profit=" + profit +
                ", duration=" + duration +
                ", parameterCommodityChannelIndex=" + parameterCommodityChannelIndex +
                '}';
    }
}
