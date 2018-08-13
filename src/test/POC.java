package test;

import com.maths.huim.api.ItemParamMap;
import com.maths.huim.dao.ItemUnitProfitMapDao;
import com.maths.huim.dao.TransactionDao;
import com.maths.huim.impl.ItemTwuMapImpl;
import com.maths.huim.impl.ItemUtilityTableImpl;
import com.maths.huim.models.ItemTwuMap;
import com.maths.huim.models.ItemUnitProfitMap;
import com.maths.huim.models.ItemUtilityTable;
import com.maths.huim.models.Transaction;
import com.maths.huim.utils.ItemTwuMapUtils;
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

        //Sorting Items
        ItemTwuMapUtils itemTwuMapUtils = new ItemTwuMapUtils();
        itemTwuMapUtils.sortDesc(itemTwuMap);
        System.out.println(itemTwuMap);


        // Calculating Item Utility Mapping
        Map<String, ItemUtilityTable> itemUtilityTableMap = (new ItemUtilityTableImpl()).calculate(transactions, itemUnitProfitMap, itemTwuMap);
        System.out.println(itemUtilityTableMap);
    }

}
