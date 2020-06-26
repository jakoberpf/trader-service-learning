package de.ginisolutions.trader.learning.calibration.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import de.ginisolutions.trader.common.market.market.BinanceMarket;
import de.ginisolutions.trader.common.model.tick.CommonTick;
import de.ginisolutions.trader.history.domain.TickPackage;
import de.ginisolutions.trader.common.enumeration.INTERVAL;
import de.ginisolutions.trader.common.enumeration.MARKET;
import de.ginisolutions.trader.common.enumeration.SYMBOL;
import de.ginisolutions.trader.common.model.tick.pojo.CoinapiTestTick;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.num.PrecisionNum;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class TickProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickProvider.class);

    private static final String address = "https://rest.coinapi.io";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final BinanceMarket binanceMarket = new BinanceMarket();

    public static BarSeries getLiveBarSeries(@NotNull MARKET market, SYMBOL symbol, INTERVAL interval) {
        switch (market) {
            case BINANCE:
                return convertTick2BarSeries(binanceMarket.getCandlesticks(symbol, interval), market, symbol);
            default:
                throw new IllegalArgumentException("Not Implemented Request for Market Timeline" + market);
        }
    }

    public static BarSeries getStaticBarSeries(MARKET market, SYMBOL symbol, INTERVAL interval, String provider, int length) {
        LOGGER.info("Reading TestData from file");
        File file;
        BarSeries barSeries;
        switch (provider) {
            case "coinapi":
                file = new File(
                    Objects.requireNonNull(TickProvider.class.getClassLoader().getResource("static/coinapi_timeseries_BTCUSD.json")).getFile()
                );
                LOGGER.info(file.getAbsolutePath());
                CoinapiTestTick[] coinapi_timeline;
                try {
                    coinapi_timeline = objectMapper.readValue(file, CoinapiTestTick[].class);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.toString());
                }
                List<TickPackage> converted = new ArrayList<>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
                for (CoinapiTestTick tick : coinapi_timeline) {
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
                LOGGER.debug("Sorting List for Close Time");
                converted.sort(Comparator.comparing(TickPackage::getCloseTime).reversed());
                LOGGER.debug("Looking for Tick Interval Overflow");
                for (TickPackage current : converted) {
                    LOGGER.debug("Checking: " + ZonedDateTime.ofInstant(Instant.ofEpochMilli(current.getCloseTime()), ZoneId.systemDefault()));
                    if (current.getCloseTime() - current.getOpenTime() > 60000) {
                        throw new RuntimeException("Tick is to long");
                    }
                }
                LOGGER.debug("Filtering List for duplicated and corrupted Ticks");
                List<TickPackage> filtered1 = new ArrayList<>();
                TickPackage last = null;
                for (TickPackage current : converted) {
                    if (last != null) {
                        if (!last.getOpenTime().equals(current.getCloseTime())) {
                            LOGGER.debug("Last: " + last.toString());
                            LOGGER.debug("Current: " + current.toString());
                            if (last.getOpenTime().equals(current.getOpenTime()) && last.getCloseTime().equals(current.getCloseTime())) {
                                LOGGER.debug("This tick is duplicated" + current.toString());
                            }
                        } else {
                            LOGGER.debug(String.valueOf(ZonedDateTime.ofInstant(Instant.ofEpochMilli(current.getCloseTime()), ZoneId.systemDefault())));
                            filtered1.add(current);
                        }
                    }
                    last = current;
                }
                LOGGER.debug("Sorting List for Close Time, reversed");
                filtered1.sort(Comparator.comparing(TickPackage::getCloseTime));
                LOGGER.debug("Filtering List for duplicated and corrupted Ticks (Not Implemented)");
//                List<TickPackage> filtered2 = new ArrayList<>();
//                last = null;
//                for (TickPackage current : filtered1) {
//                    if (last != null) {
//                        if (!last.getOpenTime().equals(current.getCloseTime())) {
//                            System.out.println("Last: " + last.toString());
//                            System.out.println("Current: " + current.toString());
//                            if (last.getOpenTime().equals(current.getOpenTime()) && last.getCloseTime().equals(current.getCloseTime())) {
//                                System.out.println("This tick is duplicated");
//                            }
//                        } else {
//                            System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(current.getCloseTime()), ZoneId.systemDefault()));
//                            filtered2.add(current);
//                        }
//                    }
//                    last = current;
//                }
                barSeries = new BaseBarSeriesBuilder()
                    .withName(market.getName() + "-" + symbol.getNameUpper())
                    .withNumTypeOf(PrecisionNum.class)
                    .build();
                filtered1.forEach(tick -> {
                    LOGGER.debug("Adding bar: " + ZonedDateTime.ofInstant(Instant.ofEpochMilli(tick.getCloseTime()), ZoneId.systemDefault()));
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
            case "api_cryptowat":
                file = new File(
                    Objects.requireNonNull(TickProvider.class.getClassLoader().getResource("static/cryptowat_ohlc_BTCUSDT.json")).getFile()
                );
                LOGGER.info(file.getAbsolutePath());
                try {
                    Double[][] timeline = objectMapper.readValue(file, Double[][].class);
                    System.out.println(Arrays.deepToString(timeline));
                    throw new IllegalArgumentException("");
                } catch (IOException e) {
                    throw new RuntimeException("Error during reading timeline file", e);
                }
            case "kaggle_bitfinex":
                // https://www.kaggle.com/tencars/392-crypto-currency-pairs-at-minute-resolution
                file = new File(Objects.requireNonNull(TickProvider.class.getClassLoader().getResource("static/kaggle_bitfinex/btceur.csv")).getFile());
                List<List<String>> bitfinexTimeline = new ArrayList<>();
                try (CSVReader csvReader = new CSVReader(new FileReader(file));) {
                    String[] values;
                    while ((values = csvReader.readNext()) != null) {
                        bitfinexTimeline.add(Arrays.asList(values));
                    }
                    bitfinexTimeline.remove(0);
                } catch (IOException | CsvValidationException e) {
                    e.printStackTrace();
                }
                System.out.println(bitfinexTimeline.size());

                List<List<String>> bitfinexTimelineSubArray = new ArrayList<>(bitfinexTimeline.subList(bitfinexTimeline.size() - length, bitfinexTimeline.size() - 1));

                LOGGER.info(String.valueOf(bitfinexTimelineSubArray.size()));

                LOGGER.info("Start of Timeline" + ZonedDateTime.ofInstant(Instant.ofEpochMilli(
                    Long.parseLong(
                        bitfinexTimelineSubArray.get(0).get(0))
                ), ZoneId.systemDefault()));

                LOGGER.info("End of Timeline" + ZonedDateTime.ofInstant(Instant.ofEpochMilli(
                    Long.parseLong(
                        bitfinexTimelineSubArray.get(bitfinexTimelineSubArray.size() - 1).get(0))
                ), ZoneId.systemDefault()));

                barSeries = new BaseBarSeriesBuilder()
                    .withName(market.getName() + "-" + symbol.getNameUpper())
                    .withNumTypeOf(PrecisionNum.class)
                    .build();
                bitfinexTimelineSubArray.forEach(tick -> {
                    LOGGER.debug("Adding bar: " + ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(tick.get(0))), ZoneId.systemDefault()));
                    barSeries.addBar(
                        Duration.ofMillis(interval.getInterval()),
                        ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(tick.get(0))), ZoneId.systemDefault()),
                        Double.parseDouble(tick.get(1)),
                        Double.parseDouble(tick.get(3)),
                        Double.parseDouble(tick.get(4)),
                        Double.parseDouble(tick.get(2)),
                        Double.parseDouble(tick.get(5)));
                });
                return barSeries;
            default:
                throw new IllegalArgumentException("Provider Type not supported: " + provider);
        }
    }

    /**
     * @param testTicks
     * @param market
     * @param symbol
     * @return
     */
    public static BarSeries convertTick2BarSeries(List<CommonTick> testTicks, MARKET market, SYMBOL symbol) {
        BarSeries barSeries = new BaseBarSeriesBuilder()
            .withName(market.getName() + "-" + symbol.getNameUpper())
            .withNumTypeOf(PrecisionNum.class)
            .build();
        testTicks.forEach(tick -> {
            LOGGER.debug("Adding bar: " + ZonedDateTime.ofInstant(Instant.ofEpochMilli(tick.getCloseTime()), ZoneId.systemDefault()));
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
