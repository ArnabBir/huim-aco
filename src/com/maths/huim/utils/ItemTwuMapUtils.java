package com.maths.huim.utils;

import com.maths.huim.models.ItemTwuMap;

import java.util.*;

public class ItemTwuMapUtils {

    public void sortDesc(ItemTwuMap itemTwuMap) {

        Set<Map.Entry<String, Long>> set = itemTwuMap.getMap().entrySet();
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Long>>()
        {
            public int compare( Map.Entry<String, Long> o1, Map.Entry<String, Long> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
        for(Map.Entry<String, Long> entry:list){
            sortedMap.put(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey()+" ==== "+entry.getValue());
        }
        itemTwuMap.setMap(sortedMap);
    }
}
