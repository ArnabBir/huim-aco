package com.maths.huim.impl;

import com.maths.huim.models.ItemTwuMap;
import com.maths.huim.models.ItemUnitProfitMap;
import com.maths.huim.models.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemTwuMapImpl {

    public ItemTwuMap calculate(List<Transaction> transactions, Set<String> itemSet) {

        Map<String, Long> twuMap = new HashMap<String, Long>();

        for(Transaction transaction : transactions) {
            for(String pair : itemSet) {
                if(transaction.getItemCountMap().containsKey(pair)) {
                    if(twuMap.containsKey(pair)) {
                        twuMap.put(pair, transaction.getTotalUtil() + twuMap.get(pair));

                    }
                    else {
                        twuMap.put(pair, transaction.getTotalUtil());
                    }
                }
            }
        }

        return new ItemTwuMap(twuMap);
    }
}
