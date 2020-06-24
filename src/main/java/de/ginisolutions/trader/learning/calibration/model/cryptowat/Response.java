package de.ginisolutions.trader.learning.calibration.model.cryptowat;

//{
//    "result":
//    {
//        "60":
//            [
//                [1589941320,9730,9735.62,9722.36,9734.4,56.676709,551424.13245443],
//                [1589941380,9733.6,9734.48,9726.55,9730.67,18.802491,182943.58004692],
//                [1590001260,9531.83,9531.83,9521.13,9524.53,46.882583,446597.84613773]
//            ]
//    },
//    "allowance":
//    {
//        "cost":2247438,
//        "remaining":3997636160,
//        "remainingPaid":0,
//        "upgrade":"Upgrade for a higher allowance, starting at $15/month for 16 seconds/hour. https://cryptowat.ch/pricing"
//    }
//}

public class Response {

    private Result result;
    private Allowance allowance;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Allowance getAllowance() {
        return allowance;
    }

    public void setAllowance(Allowance allowance) {
        this.allowance = allowance;
    }
}
