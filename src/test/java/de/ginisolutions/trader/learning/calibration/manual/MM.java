package de.ginisolutions.trader.learning.calibration.manual;

import de.ginisolutions.trader.learning.calibration.MovingMomentumValueFinder;
import de.ginisolutions.trader.learning.calibration.utils.TickProvider;

import static de.ginisolutions.trader.history.domain.enumeration.INTERVAL.ONE_MINUTE;
import static de.ginisolutions.trader.history.domain.enumeration.MARKET.BINANCE;
import static de.ginisolutions.trader.history.domain.enumeration.SYMBOL.BTCUSDT;

public class MM {

    private static final MovingMomentumValueFinder valueFinder = new MovingMomentumValueFinder();

    public static void main(String[] args){
        valueFinder.run(TickProvider.getLiveBarSeries(BINANCE, BTCUSDT, ONE_MINUTE));
    }
}
