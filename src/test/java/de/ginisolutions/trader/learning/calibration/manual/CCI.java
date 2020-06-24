package de.ginisolutions.trader.learning.calibration.manual;

import de.ginisolutions.trader.history.domain.enumeration.INTERVAL;
import de.ginisolutions.trader.history.domain.enumeration.MARKET;
import de.ginisolutions.trader.history.domain.enumeration.SYMBOL;
import de.ginisolutions.trader.learning.calibration.Calibrator;
import de.ginisolutions.trader.learning.calibration.utils.TickProvider;
import de.ginisolutions.trader.trading.domain.enumeration.STRATEGY;

import static de.ginisolutions.trader.history.domain.enumeration.INTERVAL.ONE_MINUTE;
import static de.ginisolutions.trader.history.domain.enumeration.MARKET.BINANCE;
import static de.ginisolutions.trader.history.domain.enumeration.SYMBOL.BTCUSDT;

public class CCI {

    private static final MARKET market = BINANCE;

    private static final SYMBOL symbol = BTCUSDT;

    private static final INTERVAL interval = ONE_MINUTE;

    private static final Calibrator valueFinder = new Calibrator();

    public static void main(String[] args){
        valueFinder.run(STRATEGY.CCI, market, symbol, interval, TickProvider.getLiveBarSeries(market, symbol, interval));
    }
}
