package de.ginisolutions.trader.learning.calibration.utils;

import de.ginisolutions.trader.common.strategy.parameter.ParameterCCI;
import de.ginisolutions.trader.common.strategy.parameter.ParameterMM;
import de.ginisolutions.trader.common.strategy.parameter.ParameterRSI;
import de.ginisolutions.trader.common.strategy.parameter.StrategyParameter;
import de.ginisolutions.trader.trading.domain.enumeration.STRATEGY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AttributeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeBuilder.class);

    public static StrategyParameter[] buildParameters(STRATEGY strategy) {
        switch (strategy) {
            case MM:
                return buildMovingMomentumAttributes();
            case RSI:
                return buildRelativeStrengthIndexAttributes();
            case CCI:
                return buildCommodityChannelIndexAttributes();
            default:
                throw new IllegalArgumentException("Invalid strategy" + strategy);
        }
    }

    /**
     * @return array of possible parameters for MM
     */
    public static ParameterMM[] buildMovingMomentumAttributes() {
        List<ParameterMM> list = new ArrayList<>();
        for (int i = 5; i < 15; i++) {
            for (int j = 25; j < 35; j++) {
                for (int k = 5; k < 25; k++) {
                    for (int l = 5; l < 18; l++) {
                        for (int m = 19; m < 35; m++) {
                            for (int n = 10; n < 30; n++) {
                                for (int o = 15; o < 25; o++) {
                                    list.add(new ParameterMM(i, j, k, l, m, n, o));
                                }
                            }
                        }
                    }
                }
            }
        }
        ParameterMM[] parameterMM = new ParameterMM[list.size()];
        for (int i = 0; i < parameterMM.length; i++) {
            parameterMM[i] = list.get(i);
        }
        LOGGER.info("Finished building array with " + parameterMM.length + " parameter possibilities");
        return parameterMM;
    }

    /**
     * @return array of possible parameters for RSI
     */
    public static ParameterRSI[] buildRelativeStrengthIndexAttributes() {
        List<ParameterRSI> list = new ArrayList<>();
        for (int i = 5; i < 15; i++) {
//            for (int j = 180; j < 220; j++) {
            for (int j = 30; j < 50; j++) {
                for (int k = 1; k < 6; k++) {
                    for (int l = 2; l < 10; l++) {
                        for (int m = 80; m < 120; m++) {
                            list.add(new ParameterRSI(i, j, k, l, m));
                        }
                    }
                }
            }
        }
        ParameterRSI[] parameterRSI = new ParameterRSI[list.size()];
        for (int i = 0; i < parameterRSI.length; i++) {
            parameterRSI[i] = list.get(i);
        }
        LOGGER.info("Finished building array with " + parameterRSI.length + " parameter possibilities");
        return parameterRSI;
    }

    /**
     * @return array of possible parameters for CCI
     */
    public static ParameterCCI[] buildCommodityChannelIndexAttributes() {
        List<ParameterCCI> list = new ArrayList<>();
        for (int i = 190; i < 210; i++) {
            for (int j = -15; j < 5; j++) {
                for (int k = 90; k < 110; k++) {
                    for (int l = -110; l < -90; l++) {
                        for (int m = -5; m < 15; m++) {
                            list.add(new ParameterCCI(i, j, k, l, m));
                        }
                    }
                }
            }
        }
        ParameterCCI[] parameterCCI = new ParameterCCI[list.size()];
        for (int i = 0; i < parameterCCI.length; i++) {
            parameterCCI[i] = list.get(i);
        }
        LOGGER.info("Finished building array with " + parameterCCI.length + " parameter possibilities");
        return parameterCCI;
    }
}
