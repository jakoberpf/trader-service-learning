//package de.ginisolutions.trader.learning.calibration.manual;
//
//import de.ginisolutions.trader.common.enumeration.INTERVAL;
//import de.ginisolutions.trader.common.enumeration.MARKET;
//import de.ginisolutions.trader.common.enumeration.SYMBOL;
//import de.ginisolutions.trader.learning.calibration.CalibratorTask;
//import de.ginisolutions.trader.learning.calibration.utils.TickProvider;
//import de.ginisolutions.trader.common.enumeration.STRATEGY;
//
//import static de.ginisolutions.trader.common.enumeration.INTERVAL.ONE_MINUTE;
//import static de.ginisolutions.trader.common.enumeration.MARKET.BINANCE;
//import static de.ginisolutions.trader.common.enumeration.SYMBOL.BTCUSDT;
//
//public class MM {
//
//    private static final MARKET market = BINANCE;
//
//    private static final SYMBOL symbol = BTCUSDT;
//
//    private static final INTERVAL interval = ONE_MINUTE;
//
//    private static final CalibratorTask valueFinder = new CalibratorTask();
//
//    public static void main(String[] args){
//        valueFinder.run(STRATEGY.MM, market, symbol, interval, TickProvider.getLiveBarSeries(market, symbol, interval));
//    }
//}
