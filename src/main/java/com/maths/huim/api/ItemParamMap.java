package com.maths.huim.api;

import java.util.HashMap;
import java.util.Map;

public interface ItemParamMap {

    public Map<String, Object> map = new HashMap<String, Object>();

    public Map<String, Long> getMap();

    public void setMap(Map<String, Long> map);

    public Long getParam(String key);

    public Long setParam(String key, Long value);

}
