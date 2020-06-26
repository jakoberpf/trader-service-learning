//package de.ginisolutions.trader.learning.calibration.wrapper;
//
//import de.ginisolutions.trader.common.strategy.impl.CCIC_Strategy;
//import de.ginisolutions.trader.common.strategy.parameter.ParameterCCI;
//import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
//import de.ginisolutions.trader.learning.calibration.Wrapper;
//import org.ta4j.core.BarSeries;
//import org.ta4j.core.BarSeriesManager;
//import org.ta4j.core.Strategy;
//import org.ta4j.core.TradingRecord;
//import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
//
///**
// * A wrapper class for running the commodity channel index (CCI) strategy
// */
//public class WrapperCommodityChannelIndex implements Wrapper {
//    public double runStrategy(BarSeries barSeries, StrategyParameter parameterCCI) {
//        // Building the trading strategy
//        Strategy strategy = CCIC_Strategy.buildStrategy(barSeries, (ParameterCCI) parameterCCI);
//        // Running the strategy
//        BarSeriesManager seriesManager = new BarSeriesManager(barSeries);
//        TradingRecord tradingRecord = seriesManager.run(strategy);
//        // Test results for profit and return value
//        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
//        return totalProfit.calculate(barSeries, tradingRecord).doubleValue();
//    }
//
//    /**
//     * @return an new object of itself
//     */
//    public static WrapperCommodityChannelIndex buildStrategy() {
//        return new WrapperCommodityChannelIndex();
//    }
//}
