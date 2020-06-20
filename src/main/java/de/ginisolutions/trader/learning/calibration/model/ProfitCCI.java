package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.ParameterCommodityChannelIndex;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import de.ginisolutions.trader.history.domain.enumeration.INTERVAL;
import de.ginisolutions.trader.history.domain.enumeration.MARKET;
import de.ginisolutions.trader.history.domain.enumeration.SYMBOL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

public class ProfitCCI {
    @NotNull
    private double profit;

    @NotBlank
    private Duration duration;

    @NotNull
    private MARKET market;

    @NotNull
    private SYMBOL symbol;

    @NotNull
    private INTERVAL interval;

    @NotNull
    private ParameterCommodityChannelIndex parameterCommodityChannelIndex;

    public ProfitCCI(@NotNull double profit, @NotNull MARKET market, @NotNull SYMBOL symbol, @NotNull INTERVAL interval, @NotNull ParameterCommodityChannelIndex parameterCommodityChannelIndex) {
        this.profit = profit;
        this.market = market;
        this.symbol = symbol;
        this.interval = interval;
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
        return "ProfitCCI{" +
            "profit=" + profit +
            ", duration=" + duration +
            ", market=" + market +
            ", symbol=" + symbol +
            ", interval=" + interval +
            ", parameterCommodityChannelIndex=" + parameterCommodityChannelIndex +
            '}';
    }
}
