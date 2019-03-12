package com.maths.huim.utils;

import com.maths.huim.models.Constants;
import com.maths.huim.models.ItemTwuMap;
import com.maths.huim.models.Transaction;

import java.util.*;

public class ItemTwuMapUtils {

    private int compareItems(long item1, long item2) {
        long compare = item1 - item2;
        // if the same, use the lexical order otherwise use the TWU
        return (compare == 0)? (int)(item1 - item2) :  (int)compare;
    }

    public void sortDesc(ItemTwuMap itemTwuMap) {

        Set<Map.Entry<Integer, Long>> set = itemTwuMap.getMap().entrySet();
        List<Map.Entry<Integer, Long>> list = new ArrayList<Map.Entry<Integer, Long>>(set);
        Collections.sort( list, new Comparator<Map.Entry<Integer, Long>>()
        {
            public int compare( Map.Entry<Integer, Long> o1, Map.Entry<Integer, Long> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
                //return compareItems(o1.getValue(), o2.getValue());      // NEED TO VERIFY
            }
        } );
        Map<Integer, Long> sortedMap = new LinkedHashMap<Integer, Long>();
        for(Map.Entry<Integer, Long> entry:list){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        itemTwuMap.setMap(sortedMap);
    }

    public void prune(List<Transaction> transactions, ItemTwuMap itemTwuMap, Set<Integer> itemSet) {

        Map<Integer, Long> prunedMap = new LinkedHashMap<Integer, Long>();
        for(Map.Entry<Integer, Long> pair : itemTwuMap.getMap().entrySet()) {
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
