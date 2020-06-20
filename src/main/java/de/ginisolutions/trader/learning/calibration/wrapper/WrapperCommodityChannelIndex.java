package de.ginisolutions.trader.learning.calibration.wrapper;

import de.ginisolutions.trader.common.strategy.impl.CommodityChannelIndexStrategy;
import de.ginisolutions.trader.common.strategy.parameter.ParameterCommodityChannelIndex;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BarSeriesManager;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;

/**
 * A wrapper class for running the commodity channel index (CCI) strategy
 */
public class WrapperCommodityChannelIndex {
    public double runStrategy(BarSeries barSeries, ParameterCommodityChannelIndex parameterCommodityChannelIndex) {
        // Building the trading strategy
        Strategy strategy = CommodityChannelIndexStrategy.buildStrategy(barSeries, parameterCommodityChannelIndex);
        // Running the strategy
        BarSeriesManager seriesManager = new BarSeriesManager(barSeries);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        // Test results for profit and return value
        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        return totalProfit.calculate(barSeries, tradingRecord).doubleValue();
    }
}
