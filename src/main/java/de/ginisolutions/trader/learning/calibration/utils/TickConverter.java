package de.ginisolutions.trader.learning.calibration.utils;

import de.ginisolutions.trader.history.domain.TickPackage;
import de.ginisolutions.trader.history.domain.enumeration.INTERVAL;
import de.ginisolutions.trader.history.domain.enumeration.MARKET;
import de.ginisolutions.trader.history.domain.enumeration.SYMBOL;
import de.ginisolutions.trader.learning.calibration.model.coinapi.CoinapiTestTick;
import de.ginisolutions.trader.learning.calibration.model.cryptowat.CryptowatTestTick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.num.PrecisionNum;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TickConverter {

    private static final Logger logger = LoggerFactory.getLogger(TickConverter.class);

    /**
     *
     * @param coinapiTestTicks
     * @param market
     * @param symbol
     * @param interval
     * @return
     */
    public static List<TickPackage> convertCoinapi2Tick(List<CoinapiTestTick> coinapiTestTicks, MARKET market, SYMBOL symbol, INTERVAL interval) {
        List<TickPackage> converted = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
        for (CoinapiTestTick tick : coinapiTestTicks) {
            TickPackage current = new TickPackage(
                    market,
                    symbol,
                    interval,
                    LocalDateTime.parse(tick.getTime_period_start(), formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    LocalDateTime.parse(tick.getTime_period_start(), formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    LocalDateTime.parse(tick.getTime_period_end(), formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    Double.valueOf(tick.getPrice_open()),
                    Double.valueOf(tick.getPrice_close()),
                    Double.valueOf(tick.getPrice_high()),
                    Double.valueOf(tick.getPrice_low()),
                    Double.valueOf(tick.getVolume_traded()),
                    true
            );
            converted.add(current);
        }
        logger.debug("Sorting List for Close Time");
        converted.sort(Comparator.comparing(TickPackage::getCloseTime).reversed());
        logger.debug("Looking for Tick Interval Overflow");
        for (TickPackage current : converted) {
            logger.debug("Checking: " + ZonedDateTime.ofInstant(Instant.ofEpochMilli(current.getCloseTime()), ZoneId.systemDefault()));
            if (current.getCloseTime() - current.getOpenTime() > 60000) {
                throw new RuntimeException("Tick is to long");
            }
        }
        logger.debug("Filtering List for duplicated and corrupted Ticks");
        List<TickPackage> filtered1 = new ArrayList<>();
        TickPackage last = null;
        for (TickPackage current : converted) {
            if (last!=null) {
                if(!last.getOpenTime().equals(current.getCloseTime())) {
                    logger.debug("Last: " + last.toString());
                    logger.debug("Current: " + current.toString());
                    if (last.getOpenTime().equals(current.getOpenTime())&&last.getCloseTime().equals(current.getCloseTime())) {
                        logger.debug("This tick is duplicated" + current.toString());
                    }
                } else {
                    logger.debug(String.valueOf(ZonedDateTime.ofInstant(Instant.ofEpochMilli(current.getCloseTime()), ZoneId.systemDefault())));
                    filtered1.add(current);
                }
            }
            last = current;
        }
        logger.debug("Sorting List for Close Time, reversed");
        filtered1.sort(Comparator.comparing(TickPackage::getCloseTime));
        logger.debug("Filtering List for duplicated and corrupted Ticks (Not Implemented)");
//        List<Tick> filtered2 = new ArrayList<>();
//        last = null;
//        for (Tick current : filtered1) {
//            if (last!=null) {
//                if(!last.getOpenTime().equals(current.getCloseTime())) {
//                    System.out.println("Last: " + last.toString());
//                    System.out.println("Current: " + current.toString());
//                    if (last.getOpenTime().equals(current.getOpenTime())&&last.getCloseTime().equals(current.getCloseTime())) {
//                        System.out.println("This tick is duplicated");
//                    }
//                } else {
//                    System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(current.getCloseTime()), ZoneId.systemDefault()));
//                    filtered2.add(current);
//                }
//            }
//            last = current;
//        }
        return filtered1;
    }

    public static List<TickPackage> convertCryptowat2Tick(List<CryptowatTestTick> asList, MARKET market, SYMBOL symbol, INTERVAL interval) {
        System.out.println(asList.size());
        return new ArrayList<>();
    }

    /**
     *
     * @param testTicks
     * @param market
     * @param symbol
     * @return
     */
    public static BarSeries convertTick2BarSeries(List<TickPackage> testTicks, MARKET market, SYMBOL symbol) {
        BarSeries barSeries = new BaseBarSeriesBuilder()
                .withName(market.getName() + "-" + symbol.getNameUpper())
                .withNumTypeOf(PrecisionNum.class)
                .build();
        testTicks.forEach(tick -> {
            logger.debug("Adding bar: " + ZonedDateTime.ofInstant(Instant.ofEpochMilli(tick.getCloseTime()), ZoneId.systemDefault()));
            barSeries.addBar(
                    Duration.ofMillis(tick.getInterval().getInterval() - 1),
                    ZonedDateTime.ofInstant(Instant.ofEpochMilli(tick.getCloseTime()), ZoneId.systemDefault()),
                    tick.getOpen(),
                    tick.getHigh(),
                    tick.getLow(),
                    tick.getClose(),
                    tick.getVolume()
            );
        });
        return barSeries;
    }


}
