package de.ginisolutions.trader.learning.calibration;

import de.ginisolutions.trader.history.domain.enumeration.INTERVAL;
import de.ginisolutions.trader.history.domain.enumeration.MARKET;
import de.ginisolutions.trader.history.domain.enumeration.SYMBOL;
import de.ginisolutions.trader.learning.calibration.model.internal.ProfitSetRSI;
import de.ginisolutions.trader.learning.calibration.utils.TickProvider;
import de.ginisolutions.trader.trading.domain.enumeration.STRATEGY;
import org.ta4j.core.BarSeries;

import static de.ginisolutions.trader.history.domain.enumeration.INTERVAL.ONE_MINUTE;
import static de.ginisolutions.trader.history.domain.enumeration.MARKET.BINANCE;
import static de.ginisolutions.trader.history.domain.enumeration.SYMBOL.BTCUSDT;

public class RSIFinderTest {

    private static final STRATEGY strategy = STRATEGY.CCI;

    private static final MARKET market = BINANCE;

    private static final SYMBOL symbol = BTCUSDT;

    private static final INTERVAL interval = ONE_MINUTE;

    public static void main(String[] args) {
        Calibrator finder = new Calibrator();
//        final BarSeries barSeries = TickProvider.getStaticBarSeries(BINANCE, BTCUSDT, ONE_MINUTE, "kaggle_bitfinex", 10080);
        final BarSeries barSeries = TickProvider.getLiveBarSeries(BINANCE, BTCUSDT, ONE_MINUTE);
        System.out.println("Number of bars is " + barSeries.getBarCount());
        final ProfitSetRSI profit = (ProfitSetRSI) finder.run(strategy, market, symbol, interval, barSeries);
        System.out.println("Maximum Profit reached with " + profit.getParameter().toString() + " -> " + profit.getProfit());
    }
}
