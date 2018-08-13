package test;

import com.maths.huim.api.ItemParamMap;
import com.maths.huim.dao.ItemUnitProfitMapDao;
import com.maths.huim.dao.TransactionDao;
import com.maths.huim.models.ItemTWUMap;
import com.maths.huim.models.Transaction;
import org.junit.jupiter.api.Test;

import java.util.*;

public class POC {

    @Test
    public void sanityTest() {

        ItemUnitProfitMapDao itemUnitProfitMapDao = new ItemUnitProfitMapDao();
        ItemParamMap itemUnitProfitMap = itemUnitProfitMapDao.fetch("base_test");
        System.out.println(itemUnitProfitMap);

        List<Transaction> transactions = (new TransactionDao()).fetch("base_test");
        System.out.println(transactions);

        ItemParamMap itemTWUMap = new ItemTWUMap();
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

        System.out.println(twuMap);

    }

}
