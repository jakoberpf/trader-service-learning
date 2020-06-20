package de.ginisolutions.trader.learning.calibration.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ginisolutions.trader.common.market.market.BinanceMarket;
import de.ginisolutions.trader.history.domain.TickPackage;
import de.ginisolutions.trader.history.domain.enumeration.INTERVAL;
import de.ginisolutions.trader.history.domain.enumeration.MARKET;
import de.ginisolutions.trader.history.domain.enumeration.SYMBOL;
import de.ginisolutions.trader.learning.calibration.model.TestTick_coinapi;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class TickProvider {

    private static final Logger logger = LoggerFactory.getLogger(TickProvider.class);

    private static final String address = "https://rest.coinapi.io";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final BinanceMarket binanceMarket = new BinanceMarket();

    public static BarSeries getLiveBarSeries(MARKET market, SYMBOL symbol, INTERVAL interval) {
        switch (market) {
            case BINANCE:
                return TickConverter.convertTick2BarSeries(binanceMarket.getCandlesticks(symbol, interval), market, symbol);
            default:
                throw new IllegalArgumentException("Not Implemented Request for Market Timeline" + market);
        }
    }

//    public static BarSeries getTestBarSeries(MARKET market, SYMBOL symbol, INTERVAL interval) {
//        final List<TickPackage> ticks = TickConverter.convertCoinAPI2Tick(getStaticBarSeries(market, symbol, interval), market, symbol, interval);
//        return TickConverter.convertTick2BarSeries(ticks, market, symbol);
//    }

    private static List<TestTick_coinapi> getStaticBarSeries(MARKET market, SYMBOL symbol, INTERVAL interval) {
        logger.info("Reading TestData from file");
        File file = new File(
                Objects.requireNonNull(TickConverter.class.getClassLoader().getResource("static/coinapi_timeseries_BTCUSD.json")).getFile()
        );
        logger.info(file.getAbsolutePath());
        try {
            TestTick_coinapi[] timeline = objectMapper.readValue(file, TestTick_coinapi[].class);
            return Arrays.asList(timeline);
        } catch (IOException e) {
            throw new RuntimeException("Error during reading timeline file", e);
        }
    }

//    public List<Tick> getMaxTimeLine() {
//        try {
//            final HashMap<String, String> map = new HashMap<>();
//            map.put("period_id", "1MIN");
//            map.put("limit", "100000");
//            HttpResponse<String> response = this.makeRequest("/v1/ohlcv/BTC/USDT/", map);
//            logger.debug("Received HttpResponse: " + response.body() + " from: " + response.uri());
//            ResponseTickers responseTickers = this.objectMapper.readValue(response.body(), ResponseTickers.class);
//            return responseTickers.getData().getTicker();
//        } catch (IOException e) {
//            throw new RuntimeException("Error during handling HttpResponse", e);
//        }
//    }

//    /**
//     * @param subAddress
//     * @param parameters
//     * @return
//     */
//    private HttpResponse<String> makeRequest(String subAddress, Map<String, String> parameters) {
//        try {
//            HttpUrl.Builder urlBuilder = HttpUrl.parse(this.address).newBuilder();
//            parameters.forEach((k, v) -> urlBuilder.addQueryParameter(k, v));
//            String url = urlBuilder.build().toString();
//
//            Request request = new Request.Builder()
//                    .header("X-TestDataProvider-Key", "73034021-THIS-IS-SAMPLE-KEY")
//                    .url(url)
//                    .build();
//
//        } catch (URISyntaxException | IOException | InterruptedException e) {
//            throw new RuntimeException("Error during making HttpRequest", e);
//        }
//    }
}
