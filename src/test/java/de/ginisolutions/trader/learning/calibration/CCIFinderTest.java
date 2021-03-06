//package de.ginisolutions.trader.learning.calibration;
//
//import de.ginisolutions.trader.common.enumeration.INTERVAL;
//import de.ginisolutions.trader.common.enumeration.MARKET;
//import de.ginisolutions.trader.common.enumeration.SYMBOL;
//import de.ginisolutions.trader.learning.calibration.model.internal.ProfitSetCCI;
//import de.ginisolutions.trader.learning.calibration.utils.TickProvider;
//import de.ginisolutions.trader.common.enumeration.STRATEGY;
//import org.ta4j.core.BarSeries;
//
//import static de.ginisolutions.trader.common.enumeration.INTERVAL.ONE_MINUTE;
//import static de.ginisolutions.trader.common.enumeration.MARKET.BINANCE;
//import static de.ginisolutions.trader.common.enumeration.SYMBOL.BTCUSDT;
//
//public class CCIFinderTest {
//
//    private static final STRATEGY strategy = STRATEGY.CCI;
//
//    private static final MARKET market = BINANCE;
//
//    private static final SYMBOL symbol = BTCUSDT;
//
//    private static final INTERVAL interval = ONE_MINUTE;
//
//    public static void main(String[] args) {
//        CalibratorTask finder = new CalibratorTask();
////        final BarSeries barSeries = TickProvider.getStaticBarSeries(BINANCE, BTCUSDT, ONE_MINUTE, "kaggle_bitfinex", 10080);
//        final BarSeries barSeries = TickProvider.getLiveBarSeries(market, symbol, interval);
//        System.out.println("Number of bars is " + barSeries.getBarCount());
//        final ProfitSetCCI profit = (ProfitSetCCI) finder.run(strategy, market, symbol, interval, barSeries);
//        System.out.println("Maximum Profit reached with " + profit.getParameter().toString() + " -> " + profit.getProfit());
//    }
//}
