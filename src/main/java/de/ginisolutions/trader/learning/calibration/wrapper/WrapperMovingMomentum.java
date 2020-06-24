package de.ginisolutions.trader.learning.calibration.wrapper;

import de.ginisolutions.trader.common.strategy.impl.MovingMomentumStrategy;
import de.ginisolutions.trader.common.strategy.parameter.ParameterMM;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BarSeriesManager;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;

/**
 * A wrapper class for running the moving momentum strategy
 */
public class WrapperMovingMomentum implements Wrapper {
    public double runStrategy(BarSeries barSeries, StrategyParameter parameterMM) {
        // Building the trading strategy
        Strategy strategy = MovingMomentumStrategy.buildStrategy(barSeries, (ParameterMM) parameterMM);
        // Running the strategy
        BarSeriesManager seriesManager = new BarSeriesManager(barSeries);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        // Test results for profit
        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        return totalProfit.calculate(barSeries, tradingRecord).doubleValue();
    }

    /**
     * @return an new object of itself
     */
    public static WrapperMovingMomentum buildStrategy() {
        return new WrapperMovingMomentum();
    }
}
