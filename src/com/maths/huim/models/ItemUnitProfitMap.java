package com.maths.huim.models;

import java.util.Map;

public class ItemUnitProfitMap {

    private Map<String, Long> map;

    public Map<String, Long> getMap() {
        return map;
    }

    public void setMap(Map<String, Long> map) {
        this.map = map;
    }

    public Long getUnitProfit(String key) {
        return map.get(key);
    }

    public Long setUnitProfit(String key, Long value) {
        return map.put(key, value);
    }
}
