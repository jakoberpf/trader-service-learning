package de.ginisolutions.trader.learning.calibration;

import de.ginisolutions.trader.common.strategy.parameter.ParameterCommodityChannelIndex;
import de.ginisolutions.trader.history.domain.enumeration.INTERVAL;
import de.ginisolutions.trader.history.domain.enumeration.MARKET;
import de.ginisolutions.trader.history.domain.enumeration.SYMBOL;
import de.ginisolutions.trader.learning.calibration.model.ProfitCCI;
import de.ginisolutions.trader.learning.calibration.utils.AttributeBuilder;
import de.ginisolutions.trader.learning.calibration.wrapper.WrapperCommodityChannelIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class CommodityChannelIndexValueFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityChannelIndexValueFinder.class);

    private final MARKET market;

    private final SYMBOL symbol;

    private final INTERVAL interval;

    public CommodityChannelIndexValueFinder(MARKET market, SYMBOL symbol, INTERVAL interval) {
        this.market = market;
        this.symbol = symbol;
        this.interval = interval;
    }

    /**
     * @param barSeries
     * @return
     */
    public ProfitCCI run(BarSeries barSeries) {
        final ParameterCommodityChannelIndex[] parameterCommodityChannelIndices = AttributeBuilder.buildCommodityChannelIndexAttributes();
        final LocalDateTime before, after;
        before = LocalDateTime.now();
        final ProfitCCI[] profit2RelativeStrengthIndices = convertStreamThreadLocal(barSeries, market, symbol, interval, parameterCommodityChannelIndices);
        after = LocalDateTime.now();
        ProfitCCI max = Arrays.stream(profit2RelativeStrengthIndices).max(Comparator.comparing(ProfitCCI::getProfit))
            .orElseThrow(NoSuchElementException::new);
        max.setDuration(Duration.between(before, after));
        ProfitCCI min = Arrays.stream(profit2RelativeStrengthIndices).min(Comparator.comparing(ProfitCCI::getProfit))
            .orElseThrow(NoSuchElementException::new);
        min.setDuration(Duration.between(before, after));
        LOGGER.info("Maximum Profit reached with " +
            max.getParams().toString() + " -> " + max.getProfit());
        LOGGER.info("Minimum Profit reached with " +
            min.getParams().toString() + " -> " + min.getProfit());
        return max;
    }

    /**
     * @param barSeries
     * @param parameterCommodityChannelIndexStream
     * @return
     */
    private static ProfitCCI[] convertStreamThreadLocal(BarSeries barSeries, MARKET market, SYMBOL symbol, INTERVAL interval, ParameterCommodityChannelIndex[] parameterCommodityChannelIndexStream) {
        final ThreadLocal<WrapperCommodityChannelIndex> dfLookup = ThreadLocal.withInitial(CommodityChannelIndexValueFinder::buildStrategy);
        AtomicReference<Integer> iteration = new AtomicReference<>(0);
        return Arrays.stream(parameterCommodityChannelIndexStream)
            .parallel()
            .map(parameterCommodityChannelIndex -> {
                final double profit = dfLookup.get().runStrategy(
                    barSeries,
                    parameterCommodityChannelIndex
                );
                iteration.getAndSet(iteration.get() + 1);
                return new ProfitCCI(profit, market, symbol, interval, parameterCommodityChannelIndex);
            }).toArray(ProfitCCI[]::new);
    }

    /**
     * @return
     */
    private static WrapperCommodityChannelIndex buildStrategy() {
        return new WrapperCommodityChannelIndex();
    }
}
