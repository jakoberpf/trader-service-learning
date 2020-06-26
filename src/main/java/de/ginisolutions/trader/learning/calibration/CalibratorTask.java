//package de.ginisolutions.trader.learning.calibration;
//
//import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
//import de.ginisolutions.trader.common.enumeration.INTERVAL;
//import de.ginisolutions.trader.common.enumeration.MARKET;
//import de.ginisolutions.trader.common.enumeration.SYMBOL;
//import de.ginisolutions.trader.learning.calibration.model.ProfitSet;
//import de.ginisolutions.trader.learning.calibration.model.ProfitSetFactory;
//import de.ginisolutions.trader.learning.calibration.utils.AttributeBuilder;
//import de.ginisolutions.trader.learning.calibration.wrapper.WrapperCommodityChannelIndex;
//import de.ginisolutions.trader.learning.calibration.wrapper.WrapperMovingMomentum;
//import de.ginisolutions.trader.learning.calibration.wrapper.WrapperRelativeStrengthIndex;
//import de.ginisolutions.trader.common.enumeration.STRATEGY;
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
//public class CalibratorTask {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(CalibratorTask.class);
//
//    /**
//     * @param barSeries
//     * @return
//     */
//    public ProfitSet run(STRATEGY strategy, MARKET market, SYMBOL symbol, INTERVAL interval, BarSeries barSeries) {
//        final StrategyParameter[] parameters = AttributeBuilder.buildParameters(strategy);
//        System.out.println("Number of combinations to try is " + parameters.length);
//        LocalDateTime before, after;
//        before = LocalDateTime.now();
//        final ProfitSet[] profits = convertStreamThreadLocal(strategy, market, symbol, interval, barSeries, parameters);
//        after = LocalDateTime.now();
//        ProfitSet profitSet = Arrays.stream(profits).max(Comparator.comparing(ProfitSet::getProfit))
//            .orElseThrow(NoSuchElementException::new);
//        profitSet.setDuration(Duration.between(before, after));
//        LOGGER.info("Maximum Profit reached with " + profitSet.toString());
//        return profitSet;
//    }
//
//    /**
//     * @param barSeries
//     * @param strategyParameters
//     * @return
//     */
//    private static ProfitSet[] convertStreamThreadLocal(final STRATEGY strategy, MARKET market, SYMBOL symbol, INTERVAL interval, final BarSeries barSeries, final StrategyParameter[] strategyParameters) {
//        final ThreadLocal<Wrapper> dfLookup;
//        switch (strategy) {
//            case MM:
//                dfLookup = ThreadLocal.withInitial(WrapperMovingMomentum::buildStrategy);
//                break;
//            case RSI:
//                dfLookup = ThreadLocal.withInitial(WrapperRelativeStrengthIndex::buildStrategy);
//                break;
//            case CCI:
//                dfLookup = ThreadLocal.withInitial(WrapperCommodityChannelIndex::buildStrategy);
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid strategy " + strategy);
//        }
//        AtomicReference<Integer> iteration = new AtomicReference<>(0);
//        return Arrays.stream(strategyParameters)
//            .parallel()
//            .map(strategyParameter -> {
//                final double profit = dfLookup.get().runStrategy(
//                    barSeries,
//                    strategyParameter
//                );
//                System.out.println(iteration.get() + "/" + strategyParameters.length);
//                iteration.getAndSet(iteration.get() + 1);
//                return ProfitSetFactory.buildProfitSet(strategy, market, symbol, interval, profit, strategyParameter);
//            }).toArray(ProfitSet[]::new);
//    }
//}
