package de.ginisolutions.trader.learning.calibration.wrapper;

import de.ginisolutions.trader.common.strategy.impl.MovingMomentumStrategy;
import de.ginisolutions.trader.common.strategy.parameter.ParameterMovingMomentum;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BarSeriesManager;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;

/**
 * A wrapper class for running the moving momentum strategy
 */
public class WrapperMovingMomentum {
    public double runStrategy(BarSeries barSeries, ParameterMovingMomentum parameterMovingMomentum) {
        // Building the trading strategy
        Strategy strategy = MovingMomentumStrategy.buildStrategy(barSeries, parameterMovingMomentum);
        // Running the strategy
        BarSeriesManager seriesManager = new BarSeriesManager(barSeries);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        // Test results for profit
        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        return totalProfit.calculate(barSeries, tradingRecord).doubleValue();
    }
}
