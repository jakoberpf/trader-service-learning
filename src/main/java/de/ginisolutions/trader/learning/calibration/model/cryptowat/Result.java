package de.ginisolutions.trader.learning.calibration.model.cryptowat;

//    "result":
//    {
//        "60":
//            [
//                [1589941320,9730,9735.62,9722.36,9734.4,56.676709,551424.13245443],
//                [1589941380,9733.6,9734.48,9726.55,9730.67,18.802491,182943.58004692],
//                [1590001260,9531.83,9531.83,9521.13,9524.53,46.882583,446597.84613773]
//            ]
//    },

import java.util.List;

public class Result {

    private CryptowatTestTick[] series60;

    public CryptowatTestTick[] getSeries60() {
        return series60;
    }

    public void setSeries60(CryptowatTestTick[] series60) {
        this.series60 = series60;
    }
}
