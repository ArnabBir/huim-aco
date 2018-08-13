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
        System.out.println("Sorted Map = " + itemTwuMap);

        //Pruning Itemset
        itemTwuMapUtils.prune(transactions, itemTwuMap, itemUnitProfitMap);
        System.out.println("Pruned Map = " + itemTwuMap);

        // Calculating Item Utility Mapping
        Map<String, ItemUtilityTable> itemUtilityTableMap = (new ItemUtilityTableImpl()).calculate(transactions, itemUnitProfitMap, itemTwuMap);
        System.out.println(itemUtilityTableMap);
    }

}
