package de.ginisolutions.trader.learning.calibration.model.internal;

import de.ginisolutions.trader.common.strategy.parameter.ParameterMM;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import de.ginisolutions.trader.common.enumeration.INTERVAL;
import de.ginisolutions.trader.common.enumeration.MARKET;
import de.ginisolutions.trader.common.enumeration.SYMBOL;
import de.ginisolutions.trader.learning.calibration.model.ProfitSet;
import de.ginisolutions.trader.common.enumeration.STRATEGY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

public class ProfitSetMM implements ProfitSet {

    @NotNull
    private STRATEGY strategy;

    @NotNull
    private MARKET market;

    @NotNull
    private SYMBOL symbol;

    @NotNull
    private INTERVAL interval;

    @NotNull
    private double profit;

    @NotBlank
    private ParameterMM parameter;

    @NotBlank
    private Duration duration;

    public ProfitSetMM(@NotNull STRATEGY strategy, @NotNull MARKET market, @NotNull SYMBOL symbol, @NotNull INTERVAL interval, @NotNull double profit, @NotBlank ParameterMM parameter) {
        this.strategy = strategy;
        this.market = market;
        this.symbol = symbol;
        this.interval = interval;
        this.profit = profit;
        this.parameter = parameter;
    }

    @Override
    public STRATEGY getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(STRATEGY strategy) {
        this.strategy = strategy;
    }

    @Override
    public MARKET getMarket() {
        return market;
    }

    @Override
    public void setMarket(MARKET market) {
        this.market = market;
    }

    @Override
    public SYMBOL getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(SYMBOL symbol) {
        this.symbol = symbol;
    }

    @Override
    public INTERVAL getInterval() {
        return interval;
    }

    @Override
    public void setInterval(INTERVAL interval) {
        this.interval = interval;
    }

    @Override
    public double getProfit() {
        return profit;
    }

    @Override
    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public StrategyParameter getParameter() {
        return parameter;
    }

    @Override
    public void setParameter(StrategyParameter parameter) {
        this.parameter = (ParameterMM) parameter;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ProfitSetMM{" +
            "strategy=" + strategy +
            ", market=" + market +
            ", symbol=" + symbol +
            ", interval=" + interval +
            ", profit=" + profit +
            ", parameter=" + parameter +
            ", duration=" + duration +
            '}';
    }
}
