package de.ginisolutions.trader.learning.calibration.wrapper;

import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import de.ginisolutions.trader.learning.calibration.RelativeStrengthIndexValueFinder;
import de.ginisolutions.trader.learning.calibration.model.ProfitRSI;
import de.ginisolutions.trader.learning.calibration.model.ProfitSet;
import de.ginisolutions.trader.learning.calibration.utils.AttributeBuilder;
import de.ginisolutions.trader.trading.domain.enumeration.STRATEGY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

public class CalibrationWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalibrationWrapper.class);

    /**
     * @param barSeries
     * @return
     */
    public ProfitSet run(BarSeries barSeries, STRATEGY strategy) {
        final StrategyParameter[] parameters = AttributeBuilder.buildParameters(strategy);
        System.out.println("Number of combinations to try is " + parameters.length);
        LocalDateTime before, after;
        before = LocalDateTime.now();
        final ProfitSet[] profits = convertStreamThreadLocal(barSeries, parameters);
        after = LocalDateTime.now();
        ProfitSet max = Arrays.stream(profits).max(Comparator.comparing(ProfitSet::getProfit))
            .orElseThrow(NoSuchElementException::new);
        max.setDuration(Duration.between(before, after));
        LOGGER.info("Maximum Profit reached with " + max.toString());
        return max;
    }

    /**
     * @param barSeries
     * @param strategyParameters
     * @return
     */
    private static ProfitSet[] convertStreamThreadLocal(final BarSeries barSeries, final STRATEGY strategy, final StrategyParameter[] strategyParameters) {
        final ThreadLocal<WrapperRelativeStrengthIndex> dfLookup;

        switch (strategy) {
            case MM:
                dfLookup = ThreadLocal.withInitial(MovingMomentumValueFinder::buildStrategy);
            case RSI:
                dfLookup = ThreadLocal.withInitial(RelativeStrengthIndexValueFinder::buildStrategy);
        }


        AtomicReference<Integer> iteration = new AtomicReference<>(0);
        return Arrays.stream(strategyParameters)
            .parallel()
            .map(paramRSIAttribute -> {
                final double profit = dfLookup.get().runStrategy(
                    barSeries,
                    paramRSIAttribute
                );
                System.out.println(iteration.get() + "/" + strategyParameters.length);
                iteration.getAndSet(iteration.get() + 1);
                return new ProfitSet(profit, paramRSIAttribute);
            }).toArray(ProfitSet[]::new);
    }

    /**
     * @return
     */
    private static WrapperRelativeStrengthIndex buildStrategy() {
        return new WrapperRelativeStrengthIndex();
    }
}
