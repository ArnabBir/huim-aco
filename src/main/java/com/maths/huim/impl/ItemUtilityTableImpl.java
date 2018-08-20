package com.maths.huim.impl;

import com.maths.huim.models.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemUtilityTableImpl {

    public Map< List<String>, ItemUtilityTable> init(List<Transaction> transactions, ItemUnitProfitMap itemUnitProfitMap, ItemTwuMap itemTwuMap) {

        List<Transaction> trns = transactions;
        Map<List<String>, ItemUtilityTable> itemUtilityTableMap = new LinkedHashMap<List<String>, ItemUtilityTable>();
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
            itemUtilityTableMap.put(Arrays.asList(itemTwu.getKey()), new ItemUtilityTable(Arrays.asList(itemTwu.getKey()), itemTransactionUtilityMap));
        }

        return itemUtilityTableMap;
    }

    public ItemUtilityTable union(ItemUtilityTable table1, ItemUtilityTable table2) { // ASSUMING THE SECOND TABLE IS A PRIMARY ONE

        List<String> unionItemSet = Stream.concat(table1.getItemSet().stream(), table2.getItemSet().stream()).collect(Collectors.toList());

        Map<Integer, ItemTransactionUtility> itemTransactionUtilityMap = new HashMap<Integer, ItemTransactionUtility>();
        for(Map.Entry<Integer, ItemTransactionUtility> map : table1.getItemTransactionUtilities().entrySet()) {
            if(table2.getItemTransactionUtilities().containsKey(map.getKey())) {
                long eu = map.getValue().getItemUtility() + table2.getItemTransactionUtilities().get(map.getKey()).getItemUtility();
                long ru = map.getValue().getResidualUtility() - table2.getItemTransactionUtilities().get(map.getKey()).getItemUtility();
                itemTransactionUtilityMap.put( map.getKey(), new ItemTransactionUtility(map.getKey(), eu, ru));
            }
        }
        return new ItemUtilityTable(unionItemSet, itemTransactionUtilityMap);
    }
}
