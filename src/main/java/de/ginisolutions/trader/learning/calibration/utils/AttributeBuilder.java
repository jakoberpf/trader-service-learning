package de.ginisolutions.trader.learning.calibration.utils;

import de.ginisolutions.trader.common.strategy.parameter.ParameterCommodityChannelIndex;
import de.ginisolutions.trader.common.strategy.parameter.ParameterMovingMomentum;
import de.ginisolutions.trader.common.strategy.parameter.ParameterRelativeStrengthIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AttributeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeBuilder.class);

    // Static Builder for Moving Momentum Parameters
    public static ParameterMovingMomentum[] buildMovingMomentumAttributes() {
        List<ParameterMovingMomentum> list = new ArrayList<>();
        for (int i = 5; i < 15; i++) {
            for (int j = 25; j < 35; j++) {
                for (int k = 5; k < 25; k++) {
                    for (int l = 5; l < 18; l++) {
                        for (int m = 19; m < 35; m++) {
                            for (int n = 10; n < 30; n++) {
                                for (int o = 15; o < 25; o++) {
                                    list.add(new ParameterMovingMomentum(i, j, k, l, m, n, o));
                                }
                            }
                        }
                    }
                }
            }
        }
        ParameterMovingMomentum[] parameterMovingMomentums = new ParameterMovingMomentum[list.size()];
        for (int i = 0; i < parameterMovingMomentums.length; i++) {
            parameterMovingMomentums[i] = list.get(i);
        }
        LOGGER.debug("Number of combinations: " + parameterMovingMomentums.length);
        return parameterMovingMomentums;
    }

    // Static builder for Relative Strength Index Parameters
    public static ParameterRelativeStrengthIndex[] buildRelativeStrengthIndexAttributes() {
        List<ParameterRelativeStrengthIndex> list = new ArrayList<>();
        for (int i = 5; i < 15; i++) {
            for (int j = 180; j < 220; j++) {
                for (int k = 1; k < 6; k++) {
                    for (int l = 2; l < 10; l++) {
                        for (int m = 80; m < 120; m++) {
                            list.add(new ParameterRelativeStrengthIndex(i, j, k, l, m));
                        }
                    }
                }
            }
        }
        ParameterRelativeStrengthIndex[] parameterRelativeStrengthIndices = new ParameterRelativeStrengthIndex[list.size()];
        for (int i = 0; i < parameterRelativeStrengthIndices.length; i++) {
            parameterRelativeStrengthIndices[i] = list.get(i);
        }
        LOGGER.debug("Number of combinations: " + parameterRelativeStrengthIndices.length);
        return parameterRelativeStrengthIndices;
    }

    // Static builder for Commodity Channel Index Parameters
    public static ParameterCommodityChannelIndex[] buildCommodityChannelIndexAttributes() {
        List<ParameterCommodityChannelIndex> list = new ArrayList<>();
        for (int i = 190; i < 210; i++) {
            for (int j = -15; j < 5; j++) {
                for (int k = 90; k < 110; k++) {
                    for (int l = -110; l < -90; l++) {
                        for (int m = -5; m < 15; m++) {
                            list.add(new ParameterCommodityChannelIndex(i, j, k, l, m));
                        }
                    }
                }
            }
        }
        ParameterCommodityChannelIndex[] parameterCommodityChannelIndices = new ParameterCommodityChannelIndex[list.size()];
        for (int i = 0; i < parameterCommodityChannelIndices.length; i++) {
            parameterCommodityChannelIndices[i] = list.get(i);
        }
        LOGGER.debug("Number of combinations: " + parameterCommodityChannelIndices.length);
        return parameterCommodityChannelIndices;
    }
}
