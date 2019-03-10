package com.maths.huim.utils;

import com.maths.huim.models.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MAUTableUtils {

    public long gcd(long a, long b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }

    public Map<Integer, Long> fetchUnitProfits(List<Transaction> transactions) {

        Map<Integer, Long> itemUnitProfitMap = new HashMap<Integer, Long>();
        for(Transaction transaction : transactions) {
            Map<Integer, Long> itemCountMap = transaction.getItemCountMap();
            for(Map.Entry<Integer, Long> pair : itemCountMap.entrySet()) {
                if(itemUnitProfitMap.containsKey(pair.getKey())) {
                    long prev = itemUnitProfitMap.get(pair.getKey());
                    long curr = pair.getValue();
                    itemUnitProfitMap.put(pair.getKey(), gcd(prev, curr));
                }
                else {
                    itemUnitProfitMap.put(pair.getKey(), pair.getValue());
                }
            }
        }
        return itemUnitProfitMap;
    }
}
