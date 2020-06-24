//package de.ginisolutions.trader.learning.calibration;
//
//import de.ginisolutions.trader.learning.calibration.utils.AttributeBuilder;
//import de.ginisolutions.trader.learning.calibration.wrapper.WrapperMovingMomentum;
//import de.ginisolutions.trader.common.strategy.parameter.ParameterMM;
//import de.ginisolutions.trader.learning.calibration.model.internal.ProfitSetMM;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.ta4j.core.BarSeries;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.NoSuchElementException;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// *
// */
//public class MovingMomentumValueFinder {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MovingMomentumValueFinder.class);
//
//    /**
//     * @param barSeries
//     * @return
//     */
//    public ProfitSetMM run(BarSeries barSeries) {
//        final ParameterMM[] parameterMMArray = AttributeBuilder.buildMovingMomentumAttributes();
//        final LocalDateTime before, after;
//        before = LocalDateTime.now();
//        final ProfitSetMM[] profitSetMM = convertStreamThreadLocal(barSeries, parameterMMArray);
//        after = LocalDateTime.now();
//        ProfitSetMM max = Arrays.stream(profitSetMM).max(Comparator.comparing(ProfitSetMM::getProfit))
//                .orElseThrow(NoSuchElementException::new);
//        max.setDuration(Duration.between(before, after));
//        ProfitSetMM min = Arrays.stream(profitSetMM).min(Comparator.comparing(ProfitSetMM::getProfit))
//                .orElseThrow(NoSuchElementException::new);
//        min.setDuration(Duration.between(before, after));
//        LOGGER.info("Maximum Profit reached with " +
//                max.getParams().toString() + " -> " + max.getProfit());
//        LOGGER.info("Minimum Profit reached with " +
//                min.getParams().toString() + " -> " + max.getProfit());
//        return max;
//    }
//
//    /**
//     * @param barSeries
//     * @param parameterMMArray
//     * @return
//     */
//    private static ProfitSetMM[] convertStreamThreadLocal(final BarSeries barSeries, final ParameterMM[] parameterMMArray) {
//        final ThreadLocal<WrapperMovingMomentum> dfLookup = ThreadLocal.withInitial(MovingMomentumValueFinder::buildStrategy);
//        AtomicReference<Integer> iteration = new AtomicReference<>(0);
//        return Arrays.stream(parameterMMArray)
//                .parallel()
//                .map(paramMMAttribute -> {
//                    final double profit = dfLookup.get().runStrategy(
//                            barSeries,
//                            paramMMAttribute
//                    );
//                    iteration.getAndSet(iteration.get() + 1);
//                    return new ProfitSetMM(profit, paramMMAttribute);
//                }).toArray(ProfitSetMM[]::new);
//    }
//
//    /**
//     * @return
//     */
//    private static WrapperMovingMomentum buildStrategy() {
//        return new WrapperMovingMomentum();
//    }
//}
