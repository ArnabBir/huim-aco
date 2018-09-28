package com.maths.huim.models;

public class ItemPropensity {

    String item;
    double propensity;

    public ItemPropensity(String item, double propensity) {
        this.item = item;
        this.propensity = propensity;
    }

    public ItemPropensity() {
    }

    public String getItem() {
        return item;
    }

    public double getPropensity() {
        return propensity;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPropensity(double propensity) {
        this.propensity = propensity;
    }
}
