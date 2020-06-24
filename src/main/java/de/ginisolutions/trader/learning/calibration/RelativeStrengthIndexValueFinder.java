//package de.ginisolutions.trader.learning.calibration;
//
//import de.ginisolutions.trader.learning.calibration.utils.AttributeBuilder;
//import de.ginisolutions.trader.learning.calibration.wrapper.WrapperRelativeStrengthIndex;
//import de.ginisolutions.trader.common.strategy.parameter.ParameterRSI;
//import de.ginisolutions.trader.learning.calibration.model.internal.ProfitSetRSI;
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
//public class RelativeStrengthIndexValueFinder {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RelativeStrengthIndexValueFinder.class);
//
//    /**
//     * @param barSeries
//     * @return
//     */
//    public ProfitSetRSI run(BarSeries barSeries) {
//        final ParameterRSI[] attributeRelativeStrengthIndices = AttributeBuilder.buildRelativeStrengthIndexAttributes();
//        System.out.println("Number of combinations to try is " + attributeRelativeStrengthIndices.length);
//        LocalDateTime before, after;
//        before = LocalDateTime.now();
//        final ProfitSetRSI[] profit2RelativeStrengthIndices = convertStreamThreadLocal(barSeries, attributeRelativeStrengthIndices);
//        after = LocalDateTime.now();
//        ProfitSetRSI max = Arrays.stream(profit2RelativeStrengthIndices).max(Comparator.comparing(ProfitSetRSI::getProfit))
//                .orElseThrow(NoSuchElementException::new);
//        max.setDuration(Duration.between(before, after));
//        ProfitSetRSI min = Arrays.stream(profit2RelativeStrengthIndices).min(Comparator.comparing(ProfitSetRSI::getProfit))
//                .orElseThrow(NoSuchElementException::new);
//        min.setDuration(Duration.between(before, after));
//        LOGGER.info("Maximum Profit reached with " + max.toString());
//        return max;
//    }
//
//    /**
//     * @param barSeries
//     * @param attributeRelativeStrengthIndices
//     * @return
//     */
//    private static ProfitSetRSI[] convertStreamThreadLocal(final BarSeries barSeries, final ParameterRSI[] attributeRelativeStrengthIndices) {
//        final ThreadLocal<WrapperRelativeStrengthIndex> dfLookup = ThreadLocal.withInitial(RelativeStrengthIndexValueFinder::buildStrategy);
//        AtomicReference<Integer> iteration = new AtomicReference<>(0);
//        return Arrays.stream(attributeRelativeStrengthIndices)
//                .parallel()
//                .map(paramRSIAttribute -> {
//                    final double profit = dfLookup.get().runStrategy(
//                            barSeries,
//                            paramRSIAttribute
//                    );
//                    System.out.println(iteration.get() + "/" + attributeRelativeStrengthIndices.length);
//                    iteration.getAndSet(iteration.get() + 1);
//                    return new ProfitSetRSI(profit, paramRSIAttribute);
//                }).toArray(ProfitSetRSI[]::new);
//    }
//
//    /**
//     * @return
//     */
//    private static WrapperRelativeStrengthIndex buildStrategy() {
//        return new WrapperRelativeStrengthIndex();
//    }
//}
//
