package de.jakoberpf.trader.backend.StrategyExamples;

import de.jakoberpf.trader.backend.TestDataProvider.Provider;
import de.jakoberpf.trader.backend.spring.repository.model.learning.parameter.ParameterRelativeStrengthIndex;
import de.jakoberpf.trader.backend.strategies.impl.RelativeStrengthIndexStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BarSeriesManager;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.*;

import static de.jakoberpf.trader.backend.spring.repository.model.exchange.ExchangeEnum.Binance;
import static de.jakoberpf.trader.backend.spring.repository.model.exchange.IntervalEnum.*;
import static de.jakoberpf.trader.backend.spring.repository.model.exchange.MarketEnum.BTCUSDT;

public class RelativeStrengthIndexExample {

    private static final Logger logger = LoggerFactory.getLogger(RelativeStrengthIndexExample.class);

    public static void main(String[] args) throws Exception {
//        Binance_BTCUSD_minutely_TestSeries();
        Binance_BTCUSD_minutely_LiveSeries();
//        Binance_BTCUSDT_hourly_LiveSeries();
//        Binance_BTCUSDT_daily_LiveSeries();
    }

    public static void Binance_BTCUSD_minutely_TestSeries() {
        final BarSeries barSeries = Provider.getTestBarSeries(Binance, BTCUSDT, ONE_MINUTE);
        final ParameterRelativeStrengthIndex parameterRSI = new ParameterRelativeStrengthIndex(5, 200, 2, 5, 95);
        backtest(barSeries, parameterRSI);
    }

    public static void Binance_BTCUSD_minutely_LiveSeries() {
        final BarSeries barSeries = Provider.getLiveBarSeries(Binance, BTCUSDT, ONE_MINUTE);
        final ParameterRelativeStrengthIndex parameterRSI = new ParameterRelativeStrengthIndex(5, 200, 2, 5, 95);
        backtest(barSeries, parameterRSI);
    }

    public static void Binance_BTCUSDT_hourly_LiveSeries() {
        final BarSeries barSeries = Provider.getLiveBarSeries(Binance, BTCUSDT, HOURLY);
        final ParameterRelativeStrengthIndex parameterRSI = new ParameterRelativeStrengthIndex(5, 200, 2, 5, 95);
        backtest(barSeries, parameterRSI);
    }

    public static void Binance_BTCUSDT_daily_LiveSeries() {
        final BarSeries barSeries = Provider.getLiveBarSeries(Binance, BTCUSDT, DAILY);
        final ParameterRelativeStrengthIndex parameterRSI = new ParameterRelativeStrengthIndex(5, 200, 2, 5, 95);
        backtest(barSeries, parameterRSI);
    }

    public static void backtest(BarSeries barSeries, ParameterRelativeStrengthIndex parameterRSI) {
        logger.info("Test RelativeStrengthIndex Strategy");

        // Building the trading strategy
        Strategy strategy = RelativeStrengthIndexStrategy.buildStrategy(barSeries, parameterRSI);

        // Running the strategy
        BarSeriesManager seriesManager = new BarSeriesManager(barSeries);
        TradingRecord tradingRecord = seriesManager.run(strategy);

        // Back Test Results
        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        // Total profits
        logger.info("Total profit: " + totalProfit.calculate(barSeries, tradingRecord));
        // Number of relevant bars
//        System.out.println("Number of relevant bars: " + new NumberOfBarsCriterion().calculate(barSeries, tradingRecord));
        // Number of all bars
//        System.out.println("Number of all bars: " + tradingRecord.getTradeCount());
        // Average profit (per bar)
        logger.info("Average profit (per bar): " + new AverageProfitCriterion().calculate(barSeries, tradingRecord));
        // Number of trades
        logger.info("Number of trades: " + new NumberOfTradesCriterion().calculate(barSeries, tradingRecord));
        // Profitable trades ratio
        logger.info("Profitable trades ratio: " + new AverageProfitableTradesCriterion().calculate(barSeries, tradingRecord));
        // Maximum drawdown
        logger.info("Maximum drawdown: " + new MaximumDrawdownCriterion().calculate(barSeries, tradingRecord));
        // Reward-risk ratio
//        System.out.println("Reward-risk ratio: " + new RewardRiskRatioCriterion().calculate(barSeries, tradingRecord));
        // Total transaction cost
        logger.info("Total transaction cost (from $1000): " + new LinearTransactionCostCriterion(1000, 0.005).calculate(barSeries, tradingRecord));
        // Buy-and-hold
        logger.info("Buy-and-hold: " + new BuyAndHoldCriterion().calculate(barSeries, tradingRecord));
        // Total profit vs buy-and-hold
        logger.info("Custom strategy profit vs buy-and-hold strategy profit: "
                + new VersusBuyAndHoldCriterion(totalProfit).calculate(barSeries, tradingRecord));
    }
}
