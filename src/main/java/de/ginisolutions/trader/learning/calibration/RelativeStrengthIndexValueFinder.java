package de.ginisolutions.trader.learning.calibration;

import de.ginisolutions.trader.learning.calibration.utils.AttributeBuilder;
import de.ginisolutions.trader.learning.calibration.wrapper.WrapperRelativeStrengthIndex;
import de.ginisolutions.trader.common.strategy.parameter.ParameterRelativeStrengthIndex;
import de.ginisolutions.trader.learning.calibration.model.ProfitRSI;
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
public class RelativeStrengthIndexValueFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelativeStrengthIndexValueFinder.class);

    /**
     * @param barSeries
     * @return
     */
    public ProfitRSI run(BarSeries barSeries) {

        final ParameterRelativeStrengthIndex[] attributeRelativeStrengthIndices = AttributeBuilder.buildRelativeStrengthIndexAttributes();

        LocalDateTime before, after;

        before = LocalDateTime.now();
        final ProfitRSI[] profit2RelativeStrengthIndices = convertStreamThreadLocal(barSeries, attributeRelativeStrengthIndices);
        after = LocalDateTime.now();
        ProfitRSI max = Arrays.stream(profit2RelativeStrengthIndices).max(Comparator.comparing(ProfitRSI::getProfit))
                .orElseThrow(NoSuchElementException::new);
        max.setDuration(Duration.between(before, after));
        ProfitRSI min = Arrays.stream(profit2RelativeStrengthIndices).min(Comparator.comparing(ProfitRSI::getProfit))
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
     * @param attributeRelativeStrengthIndices
     * @return
     */
    private static ProfitRSI[] convertStreamThreadLocal(final BarSeries barSeries, final ParameterRelativeStrengthIndex[] attributeRelativeStrengthIndices) {
        final ThreadLocal<WrapperRelativeStrengthIndex> dfLookup = ThreadLocal.withInitial(RelativeStrengthIndexValueFinder::buildStrategy);
        AtomicReference<Integer> iteration = new AtomicReference<>(0);
        return Arrays.stream(attributeRelativeStrengthIndices)
                .parallel()
                .map(paramRSIAttribute -> {
                    final double profit = dfLookup.get().runStrategy(
                            barSeries,
                            paramRSIAttribute
                    );
                    iteration.getAndSet(iteration.get() + 1);
                    return new ProfitRSI(profit, paramRSIAttribute);
                }).toArray(ProfitRSI[]::new);
    }

    /**
     * @return
     */
    private static WrapperRelativeStrengthIndex buildStrategy() {
        return new WrapperRelativeStrengthIndex();
    }
}

