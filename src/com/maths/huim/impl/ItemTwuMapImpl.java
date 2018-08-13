package com.maths.huim.impl;

import com.maths.huim.models.ItemTwuMap;
import com.maths.huim.models.ItemUnitProfitMap;
import com.maths.huim.models.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTwuMapImpl {

    public ItemTwuMap calculate(List<Transaction> transactions, ItemUnitProfitMap itemUnitProfitMap) {

        Map<String, Long> twuMap = new HashMap<String, Long>();

        for(Transaction transaction : transactions) {
            for(Map.Entry<String, Long> pair : itemUnitProfitMap.getMap().entrySet()) {
                if(transaction.getItemCountMap().containsKey(pair.getKey())) {
                    if(twuMap.containsKey(pair.getKey())) {
                        twuMap.put(pair.getKey(), transaction.getTotalUtil() + twuMap.get(pair.getKey()));

                    }
                    else {
                        twuMap.put(pair.getKey(), transaction.getTotalUtil());
                    }
                }
            }
        }

        return new ItemTwuMap(twuMap);
    }
}
