package com.maths.huim.utils;

import com.maths.huim.models.Transaction;

import java.util.Map;

public class TransactionUtil {

    public void updateItemCountMap(Transaction transaction, String item, Long count) {

        Map<String, Long> map = transaction.getItemCountMap();
        map.put(item, count);
        transaction.setItemCountMap(map);
    }
}
