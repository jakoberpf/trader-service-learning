package de.ginisolutions.trader.learning.calibration.wrapper;

import de.ginisolutions.trader.common.strategy.impl.RelativeStrengthIndexStrategy;
import de.ginisolutions.trader.common.strategy.parameter.ParameterRSI;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BarSeriesManager;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;

/**
 * A wrapper class for running the relative strength index (RSI) strategy
 */
public class WrapperRelativeStrengthIndex implements Wrapper {
    public double runStrategy(BarSeries barSeries, StrategyParameter parameterRSI) {
        // Building the trading strategy
        Strategy strategy = RelativeStrengthIndexStrategy.buildStrategy(barSeries, (ParameterRSI) parameterRSI);
        // Running the strategy
        BarSeriesManager seriesManager = new BarSeriesManager(barSeries);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        // Test results for profit and return value
        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        return totalProfit.calculate(barSeries, tradingRecord).doubleValue();
    }

    /**
     * @return an new object of itself
     */
    public static WrapperRelativeStrengthIndex buildStrategy() {
        return new WrapperRelativeStrengthIndex();
    }
}
