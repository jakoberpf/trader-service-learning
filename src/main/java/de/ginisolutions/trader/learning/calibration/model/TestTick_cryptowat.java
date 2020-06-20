package de.ginisolutions.trader.learning.calibration.model;

/**
 * From https://docs.cryptowat.ch/rest-api/markets/ohlc
 * [
 * CloseTime,
 * OpenPrice,
 * HighPrice,
 * LowPrice,
 * ClosePrice,
 * Volume,
 * QuoteVolume
 * ]
 * Example Values:
 * [
 * 1573552800,
 * 8744,
 * 8756.1,
 * 8710,
 * 8753.5,
 * 91.58314308,
 * 799449.488966417
 * ],
 */


public class TestTick_cryptowat {

    private String CloseTime;
    private String OpenPrice;
    private String HighPrice;
    private String LowPrice;
    private String ClosePrice;
    private String Volume;
    private String QuoteVolume;

    public TestTick_cryptowat() {
    }

    public TestTick_cryptowat(String closeTime, String openPrice, String highPrice, String lowPrice, String closePrice, String volume, String quoteVolume) {
        CloseTime = closeTime;
        OpenPrice = openPrice;
        HighPrice = highPrice;
        LowPrice = lowPrice;
        ClosePrice = closePrice;
        Volume = volume;
        QuoteVolume = quoteVolume;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    public String getOpenPrice() {
        return OpenPrice;
    }

    public void setOpenPrice(String openPrice) {
        OpenPrice = openPrice;
    }

    public String getHighPrice() {
        return HighPrice;
    }

    public void setHighPrice(String highPrice) {
        HighPrice = highPrice;
    }

    public String getLowPrice() {
        return LowPrice;
    }

    public void setLowPrice(String lowPrice) {
        LowPrice = lowPrice;
    }

    public String getClosePrice() {
        return ClosePrice;
    }

    public void setClosePrice(String closePrice) {
        ClosePrice = closePrice;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getQuoteVolume() {
        return QuoteVolume;
    }

    public void setQuoteVolume(String quoteVolume) {
        QuoteVolume = quoteVolume;
    }

    @Override
    public String toString() {
        return "TestTick_cryptowat{" +
            "CloseTime='" + CloseTime + '\'' +
            ", OpenPrice='" + OpenPrice + '\'' +
            ", HighPrice='" + HighPrice + '\'' +
            ", LowPrice='" + LowPrice + '\'' +
            ", ClosePrice='" + ClosePrice + '\'' +
            ", Volume='" + Volume + '\'' +
            ", QuoteVolume='" + QuoteVolume + '\'' +
            '}';
    }
}
