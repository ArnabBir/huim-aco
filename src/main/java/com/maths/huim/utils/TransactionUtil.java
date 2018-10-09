package com.maths.huim.utils;

import com.maths.huim.models.Transaction;

import java.util.Map;

public class TransactionUtil {

    public void updateItemCountMap(Transaction transaction, Integer item, Long count) {

        Map<Integer, Long> map = transaction.getItemCountMap();
        map.put(item, count);
        transaction.setItemCountMap(map);
    }
}
