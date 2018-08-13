package com.maths.huim.impl;

import com.maths.huim.models.ItemTransactionUtility;
import com.maths.huim.models.ItemUnitProfitMap;
import com.maths.huim.models.ItemUtilityTable;
import com.maths.huim.models.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtilityTableImpl {

    public Map<String, ItemUtilityTable> calculate(List<Transaction> transactions, ItemUnitProfitMap itemUnitProfitMap) {

        List<Transaction> trns = transactions;
        Map<String, ItemUtilityTable> itemUtilityTableMap = new HashMap<String, ItemUtilityTable>();
        for(Map.Entry<String, Long> itemUnitProfit: itemUnitProfitMap.getMap().entrySet()) {
            Map<Integer, ItemTransactionUtility> itemTransactionUtilityMap = new HashMap<Integer, ItemTransactionUtility>();
            for(Transaction transaction : trns) {
                long totalUtil = transaction.getTotalUtil();
                if(transaction.getItemCountMap().containsKey(itemUnitProfit.getKey())) {
                    long eu = transaction.getItemCountMap().get(itemUnitProfit.getKey()) * itemUnitProfit.getValue();
                    itemTransactionUtilityMap.put(transaction.getTid(),
                            new ItemTransactionUtility(transaction.getTid(), eu, totalUtil - eu));
                    transaction.setTotalUtil(totalUtil - eu);
                }
            }
            itemUtilityTableMap.put(itemUnitProfit.getKey(), new ItemUtilityTable(itemUnitProfit.getKey(), itemTransactionUtilityMap));
        }

        return itemUtilityTableMap;
    }
}
