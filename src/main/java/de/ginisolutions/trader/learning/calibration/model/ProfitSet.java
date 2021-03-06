package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import de.ginisolutions.trader.common.enumeration.INTERVAL;
import de.ginisolutions.trader.common.enumeration.MARKET;
import de.ginisolutions.trader.common.enumeration.SYMBOL;
import de.ginisolutions.trader.common.enumeration.STRATEGY;

import java.time.Duration;

public interface ProfitSet {
    STRATEGY getStrategy();

    void setStrategy(STRATEGY strategy);

    MARKET getMarket();

    void setMarket(MARKET market);

    SYMBOL getSymbol();

    void setSymbol(SYMBOL symbol);

    INTERVAL getInterval();

    void setInterval(INTERVAL interval);

    double getProfit();

    void setProfit(double profit);

    StrategyParameter getParameter();

    void setParameter(StrategyParameter strategyParameter);

    Duration getDuration();

    void setDuration(Duration duration);
}
