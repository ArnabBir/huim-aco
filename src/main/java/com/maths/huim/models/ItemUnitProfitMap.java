package com.maths.huim.models;

import com.maths.huim.api.ItemParamMap;

import java.util.Map;

public class ItemUnitProfitMap implements ItemParamMap {

    private Map<String, Long> map;

    public ItemUnitProfitMap(Map<String, Long> map) {
        this.map = map;
    }

    public Map<String, Long> getMap() {
        return map;
    }

    public void setMap(Map<String, Long> map) {
        this.map = map;
    }

    public Long getParam(String key) {
        return map.get(key);
    }

    public Long setParam(String key, Long value) {
        return map.put(key, value);
    }

    public Long getUnitProfit(String key) {
        return getParam(key);
    }

    public Long setUnitProfit(String key, Long value) {
        return setParam(key, value);
    }


    @Override
    public String toString() {
        return "ItemUnitProfitMap{" +
                "map=" + map +
                '}';
    }
}
