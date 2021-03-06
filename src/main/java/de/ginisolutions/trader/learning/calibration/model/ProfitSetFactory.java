package de.ginisolutions.trader.learning.calibration.model;

import de.ginisolutions.trader.common.strategy.parameter.ParameterCCI;
import de.ginisolutions.trader.common.strategy.parameter.ParameterMM;
import de.ginisolutions.trader.common.strategy.parameter.ParameterRSI;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import de.ginisolutions.trader.common.enumeration.INTERVAL;
import de.ginisolutions.trader.common.enumeration.MARKET;
import de.ginisolutions.trader.common.enumeration.SYMBOL;
import de.ginisolutions.trader.learning.calibration.model.internal.ProfitSetCCI;
import de.ginisolutions.trader.learning.calibration.model.internal.ProfitSetMM;
import de.ginisolutions.trader.learning.calibration.model.internal.ProfitSetRSI;
import de.ginisolutions.trader.common.enumeration.STRATEGY;

public class ProfitSetFactory {

    public static ProfitSet buildProfitSet(STRATEGY strategy, MARKET market, SYMBOL symbol, INTERVAL interval, Double profit, StrategyParameter strategyParameter) {
        switch (strategy) {
            case MM:
                return new ProfitSetMM(strategy, market, symbol, interval, profit, (ParameterMM) strategyParameter);
            case RSI:
                return new ProfitSetRSI(strategy, market, symbol, interval, profit, (ParameterRSI) strategyParameter);
            case CCIC:
                return new ProfitSetCCI(strategy, market, symbol, interval, profit, (ParameterCCI) strategyParameter);
            default:
                throw new IllegalArgumentException("Invalid strategy " + strategy);
        }
    }
}
