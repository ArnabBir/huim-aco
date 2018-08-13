package com.maths.huim.impl;

import com.maths.huim.models.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemUtilityTableImpl {

    public Map<String, ItemUtilityTable> calculate(List<Transaction> transactions, ItemUnitProfitMap itemUnitProfitMap, ItemTwuMap itemTwuMap) {

        List<Transaction> trns = transactions;
        Map<String, ItemUtilityTable> itemUtilityTableMap = new LinkedHashMap<String, ItemUtilityTable>();
        for(Map.Entry<String, Long> itemTwu: itemTwuMap.getMap().entrySet()) {
            Map<Integer, ItemTransactionUtility> itemTransactionUtilityMap = new HashMap<Integer, ItemTransactionUtility>();
            for(Transaction transaction : trns) {
                long totalUtil = transaction.getTotalUtil();
                if(transaction.getItemCountMap().containsKey(itemTwu.getKey())) {
                    long eu = transaction.getItemCountMap().get(itemTwu.getKey()) * itemUnitProfitMap.getMap().get(itemTwu.getKey());
                    itemTransactionUtilityMap.put(transaction.getTid(),
                            new ItemTransactionUtility(transaction.getTid(), eu, totalUtil - eu));
                    transaction.setTotalUtil(totalUtil - eu);
                }
            }
            itemUtilityTableMap.put(itemTwu.getKey(), new ItemUtilityTable(itemTwu.getKey(), itemTransactionUtilityMap));
        }

        return itemUtilityTableMap;
    }
}
