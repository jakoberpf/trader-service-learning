package de.ginisolutions.trader.learning.calibration.model.coinapi;

public class CoinapiTestTick {

    /**
     * From https://docs.coinapi.io/#ohlcv
     * {
     * "time_period_start": "2020-04-19T08:14:00.0000000Z",
     * "time_period_end": "2020-04-19T08:15:00.0000000Z",
     * "time_open": "2020-04-19T08:14:02.4010000Z",
     * "time_close": "2020-04-19T08:14:18.9536040Z",
     * "price_open": 7143.60000,
     * "price_high": 7143.60000,
     * "price_low": 7135.48,
     * "price_close": 7135.5,
     * "volume_traded": 13.56602023,
     * "trades_count": 17
     * }
     */

    private String time_period_start;
    private String time_period_end;
    private String time_open;
    private String time_close;
    private String price_open;
    private String price_high;
    private String price_low;
    private String price_close;
    private String volume_traded;
    private String trades_count;

    public CoinapiTestTick() {
    }

    public CoinapiTestTick(String time_period_start, String time_period_end, String time_open, String time_close, String price_open, String price_high, String price_low, String price_close, String volume_traded, String trades_count) {
        this.time_period_start = time_period_start;
        this.time_period_end = time_period_end;
        this.time_open = time_open;
        this.time_close = time_close;
        this.price_open = price_open;
        this.price_high = price_high;
        this.price_low = price_low;
        this.price_close = price_close;
        this.volume_traded = volume_traded;
        this.trades_count = trades_count;
    }

    public String getTime_period_start() {
        return time_period_start;
    }

    public void setTime_period_start(String time_period_start) {
        this.time_period_start = time_period_start;
    }

    public String getTime_period_end() {
        return time_period_end;
    }

    public void setTime_period_end(String time_period_end) {
        this.time_period_end = time_period_end;
    }

    public String getTime_open() {
        return time_open;
    }

    public void setTime_open(String time_open) {
        this.time_open = time_open;
    }

    public String getTime_close() {
        return time_close;
    }

    public void setTime_close(String time_close) {
        this.time_close = time_close;
    }

    public String getPrice_open() {
        return price_open;
    }

    public void setPrice_open(String price_open) {
        this.price_open = price_open;
    }

    public String getPrice_high() {
        return price_high;
    }

    public void setPrice_high(String price_high) {
        this.price_high = price_high;
    }

    public String getPrice_low() {
        return price_low;
    }

    public void setPrice_low(String price_low) {
        this.price_low = price_low;
    }

    public String getPrice_close() {
        return price_close;
    }

    public void setPrice_close(String price_close) {
        this.price_close = price_close;
    }

    public String getVolume_traded() {
        return volume_traded;
    }

    public void setVolume_traded(String volume_traded) {
        this.volume_traded = volume_traded;
    }

    public String getTrades_count() {
        return trades_count;
    }

    public void setTrades_count(String trades_count) {
        this.trades_count = trades_count;
    }

    @Override
    public String toString() {
        return "TestTick{" +
            "time_period_start='" + time_period_start + '\'' +
            ", time_period_end='" + time_period_end + '\'' +
            ", time_open='" + time_open + '\'' +
            ", time_close='" + time_close + '\'' +
            ", price_open='" + price_open + '\'' +
            ", price_high='" + price_high + '\'' +
            ", price_low='" + price_low + '\'' +
            ", price_close='" + price_close + '\'' +
            ", volume_traded='" + volume_traded + '\'' +
            ", trades_count='" + trades_count + '\'' +
            '}';
    }
}
