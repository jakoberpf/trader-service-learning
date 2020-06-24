package de.ginisolutions.trader.learning.strategy;

import de.ginisolutions.trader.common.strategy.impl.CommodityChannelIndexStrategy;
import de.ginisolutions.trader.common.strategy.parameter.ParameterCCI;
import de.ginisolutions.trader.learning.calibration.utils.TickProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BarSeriesManager;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.*;

import static de.ginisolutions.trader.history.domain.enumeration.INTERVAL.*;
import static de.ginisolutions.trader.history.domain.enumeration.MARKET.BINANCE;
import static de.ginisolutions.trader.history.domain.enumeration.SYMBOL.BTCUSDT;


public class CommodityChannelIndexExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityChannelIndexExample.class);

    public static void main(String[] args) {
        Binance_BTCUSD_minutely_StaticSeries();
//        Binance_BTCUSD_minutely_LiveSeries();
//        Binance_BTCUSDT_hourly_LiveSeries();
//        Binance_BTCUSDT_daily_LiveSeries();
    }

    public static void Binance_BTCUSD_minutely_StaticSeries() {
        final BarSeries barSeries = TickProvider.getStaticBarSeries(BINANCE, BTCUSDT, ONE_MINUTE, "kaggle_bitfinex", 800000); // kaggle_bitfinex
        final ParameterCCI parameterCCI = new ParameterCCI(200, -5, 100, -100, 5);
        execute(barSeries, parameterCCI);
    }

    public static void Binance_BTCUSD_minutely_LiveSeries() {
        final BarSeries barSeries = TickProvider.getLiveBarSeries(BINANCE, BTCUSDT, ONE_MINUTE);
        final ParameterCCI parameterCCI = new ParameterCCI(200, 5, 100, -100, 5);
        execute(barSeries, parameterCCI);
    }

    public static void Binance_BTCUSDT_hourly_LiveSeries() {
        final BarSeries barSeries = TickProvider.getLiveBarSeries(BINANCE, BTCUSDT, HOURLY);
//        final ParameterCCI parameterCCI = new ParameterCCI(200, 5, 100, -100, 5);
        final ParameterCCI parameterCCI = new ParameterCCI(199, 4, 94, -91, -5);
        execute(barSeries, parameterCCI);
    }

    public static void Binance_BTCUSDT_daily_LiveSeries() {
        final BarSeries barSeries = TickProvider.getLiveBarSeries(BINANCE, BTCUSDT, DAILY);
        final ParameterCCI parameterCCI = new ParameterCCI(200, 5, 100, -100, 5);
        execute(barSeries, parameterCCI);
    }

    public static void execute(BarSeries barSeries, ParameterCCI parameterCCI) {
        LOGGER.info("Test RelativeStrengthIndex Strategy");

        // Building the trading strategy
        Strategy strategy = CommodityChannelIndexStrategy.buildStrategy(barSeries, parameterCCI);

        // Running the strategy
        BarSeriesManager seriesManager = new BarSeriesManager(barSeries);
        TradingRecord tradingRecord = seriesManager.run(strategy);

        // Back Test Results
        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        // Total profits
        LOGGER.info("Total profit: " + totalProfit.calculate(barSeries, tradingRecord));
        // Number of all bars
        LOGGER.info("Number of all bars: " + seriesManager.getBarSeries().getBarCount());
        // Average profit (per bar)
        LOGGER.info("Average profit (per bar): " + new AverageProfitCriterion().calculate(barSeries, tradingRecord));
        // Number of trades
        LOGGER.info("Number of trades: " + new NumberOfTradesCriterion().calculate(barSeries, tradingRecord));
        // Profitable trades ratio
        LOGGER.info("Profitable trades ratio: " + new AverageProfitableTradesCriterion().calculate(barSeries, tradingRecord));
        // Maximum drawdown
        LOGGER.info("Maximum drawdown: " + new MaximumDrawdownCriterion().calculate(barSeries, tradingRecord));
        // Reward-risk ratio
        LOGGER.info("Reward-risk ratio: " + new RewardRiskRatioCriterion().calculate(barSeries, tradingRecord));
        // Total transaction cost
        LOGGER.info("Total transaction cost (from $1000): " + new LinearTransactionCostCriterion(1000, 0.002).calculate(barSeries, tradingRecord));
        // Buy-and-hold
        LOGGER.info("Buy-and-hold: " + new BuyAndHoldCriterion().calculate(barSeries, tradingRecord));
        // Total profit vs buy-and-hold
        LOGGER.info("Custom strategy profit vs buy-and-hold strategy profit: "
            + new VersusBuyAndHoldCriterion(totalProfit).calculate(barSeries, tradingRecord));
    }
}
