package test;

import com.maths.huim.dao.ItemUnitProfitMapDao;
import com.maths.huim.dao.TransactionDao;
import com.maths.huim.impl.ItemTwuMapImpl;
import com.maths.huim.impl.ItemUtilityTableImpl;
import com.maths.huim.models.*;
import com.maths.huim.utils.ItemTwuMapUtils;
import com.maths.huim.utils.TransactionUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

public class POC {

    @Test
    public void sanityTest() {

        // Fetching item unit profit
        ItemUnitProfitMapDao itemUnitProfitMapDao = new ItemUnitProfitMapDao();
        ItemUnitProfitMap itemUnitProfitMap = itemUnitProfitMapDao.fetch("base_test");
        System.out.println(itemUnitProfitMap);

        // Fetching transactions
        List<Transaction> transactions = (new TransactionDao()).fetch("base_test");
        System.out.println(transactions);

        // Calculating item twu map
        ItemTwuMapImpl itemTwuMapImpl = new ItemTwuMapImpl();
        ItemTwuMap itemTwuMap = itemTwuMapImpl.calculate(transactions, itemUnitProfitMap);
        System.out.println(itemTwuMap);

        //Sorting Itemset
        ItemTwuMapUtils itemTwuMapUtils = new ItemTwuMapUtils();
        itemTwuMapUtils.sortDesc(itemTwuMap);
        System.out.println(itemTwuMap);

        //Pruning Itemset
        TransactionUtil transactionUtil = new TransactionUtil();
        Map<String, Long> prunedMap = new LinkedHashMap<String, Long>();
        for(Map.Entry<String, Long> pair : itemTwuMap.getMap().entrySet()) {
            if(pair.getValue() < Constants.minUtil) {

                for (final ListIterator<Transaction> itrTrn = transactions.listIterator(); itrTrn.hasNext();) {
                    Transaction transaction = itrTrn.next();
                    if(transaction.getItemCountMap().containsKey(pair.getKey())) {
                        transaction.setTotalUtil(transaction.getTotalUtil() - transaction.getItemCountMap().get(pair.getKey()) * itemUnitProfitMap.getMap().get(pair.getKey()));
                        itrTrn.set(transaction);
                    }
                }
//                for(int i = 0; i < transactions.size(); ++i) {
//                    if(transactions.get(i).getItemCountMap().containsKey(pair.getKey())) {
//
//                        transactionUtil.
//                        transactionUtil.updateItemCountMap(transactions.get(i), pair.getKey(),
//                                pair.getValue() - transactions.get(i).getItemCountMap().get(pair.getKey()) * itemUnitProfitMap.getMap().get(pair.getKey()));
//                    }
//                }
            }
            else    prunedMap.put(pair.getKey(), pair.getValue());
        }

        itemTwuMap.setMap(prunedMap);
        System.out.println("Pruned Map = " + itemTwuMap);

        // Calculating Item Utility Mapping
        Map<String, ItemUtilityTable> itemUtilityTableMap = (new ItemUtilityTableImpl()).calculate(transactions, itemUnitProfitMap, itemTwuMap);
        System.out.println(itemUtilityTableMap);
    }

}
