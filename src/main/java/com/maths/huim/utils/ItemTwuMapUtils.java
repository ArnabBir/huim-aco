package com.maths.huim.utils;

import com.maths.huim.models.Constants;
import com.maths.huim.models.ItemTwuMap;
import com.maths.huim.models.ItemUnitProfitMap;
import com.maths.huim.models.Transaction;

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
        }
        itemTwuMap.setMap(sortedMap);
    }

    public void prune(List<Transaction> transactions, ItemTwuMap itemTwuMap, Set<String> itemSet) {

        Map<String, Long> prunedMap = new LinkedHashMap<String, Long>();
        for(Map.Entry<String, Long> pair : itemTwuMap.getMap().entrySet()) {
            if(pair.getValue() < Constants.minUtil) {
                for (final ListIterator<Transaction> itrTrn = transactions.listIterator(); itrTrn.hasNext();) {
                    Transaction transaction = itrTrn.next();
                    if(transaction.getItemCountMap().containsKey(pair.getKey())) {
                        transaction.setTotalUtil(transaction.getTotalUtil() - transaction.getItemCountMap().get(pair.getKey()));
                        itrTrn.set(transaction);
                    }
                }
            }
            else    prunedMap.put(pair.getKey(), pair.getValue());
        }
        itemTwuMap.setMap(prunedMap);
    }
}
