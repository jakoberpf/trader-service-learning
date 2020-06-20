package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;

import java.time.Duration;

public interface ProfitSet {
    double getProfit();

    void setProfit(double profit);

    Duration getDuration();

    void setDuration(Duration duration);

    StrategyParameter getParams();

    void setParams(StrategyParameter strategyParameter);
}
