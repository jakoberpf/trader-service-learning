package de.ginisolutions.trader.learning.calibration.model.cryptowat;

//    "allowance":
//    {
//        "cost":2247438,
//        "remaining":3997636160,
//        "remainingPaid":0,
//        "upgrade":"Upgrade for a higher allowance, starting at $15/month for 16 seconds/hour. https://cryptowat.ch/pricing"
//    }

public class Allowance {

    private Integer cost;
    private Integer remaining;
    private Integer remainingPaid;
    private String upgrade;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getRemainingPaid() {
        return remainingPaid;
    }

    public void setRemainingPaid(Integer remainingPaid) {
        this.remainingPaid = remainingPaid;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }
}
