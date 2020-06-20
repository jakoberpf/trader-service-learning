package de.ginisolutions.trader.learning.calibration;

import de.ginisolutions.trader.learning.calibration.utils.AttributeBuilder;
import de.ginisolutions.trader.learning.calibration.wrapper.WrapperMovingMomentum;
import de.ginisolutions.trader.common.strategy.parameter.ParameterMovingMomentum;
import de.ginisolutions.trader.learning.calibration.model.ProfitMM;
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
public class MovingMomentumValueFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingMomentumValueFinder.class);

    /**
     * @param barSeries
     * @return
     */
    public ProfitMM run(BarSeries barSeries) {
        final ParameterMovingMomentum[] parameterMovingMomentumArray = AttributeBuilder.buildMovingMomentumAttributes();
        final LocalDateTime before, after;
        before = LocalDateTime.now();
        final ProfitMM[] profitMM = convertStreamThreadLocal(barSeries, parameterMovingMomentumArray);
        after = LocalDateTime.now();
        ProfitMM max = Arrays.stream(profitMM).max(Comparator.comparing(ProfitMM::getProfit))
                .orElseThrow(NoSuchElementException::new);
        max.setDuration(Duration.between(before, after));
        ProfitMM min = Arrays.stream(profitMM).min(Comparator.comparing(ProfitMM::getProfit))
                .orElseThrow(NoSuchElementException::new);
        min.setDuration(Duration.between(before, after));
        LOGGER.info("Maximum Profit reached with " +
                max.getParams().toString() + " -> " + max.getProfit());
        LOGGER.info("Minimum Profit reached with " +
                min.getParams().toString() + " -> " + max.getProfit());
        return max;
    }

    /**
     * @param barSeries
     * @param parameterMovingMomentumArray
     * @return
     */
    private static ProfitMM[] convertStreamThreadLocal(final BarSeries barSeries, final ParameterMovingMomentum[] parameterMovingMomentumArray) {
        final ThreadLocal<WrapperMovingMomentum> dfLookup = ThreadLocal.withInitial(MovingMomentumValueFinder::buildStrategy);
        AtomicReference<Integer> iteration = new AtomicReference<>(0);
        return Arrays.stream(parameterMovingMomentumArray)
                .parallel()
                .map(paramMMAttribute -> {
                    final double profit = dfLookup.get().runStrategy(
                            barSeries,
                            paramMMAttribute
                    );
                    iteration.getAndSet(iteration.get() + 1);
                    return new ProfitMM(profit, paramMMAttribute);
                }).toArray(ProfitMM[]::new);
    }

    /**
     * @return
     */
    private static WrapperMovingMomentum buildStrategy() {
        return new WrapperMovingMomentum();
    }
}
