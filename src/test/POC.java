package test;

import com.maths.huim.api.ItemParamMap;
import com.maths.huim.dao.TransactionDao;
import com.maths.huim.models.ItemTWUMap;
import com.maths.huim.models.ItemUnitProfitMap;
import com.maths.huim.models.Transaction;
import org.junit.jupiter.api.Test;

import java.util.*;

public class POC {

    @Test
    public void sanityTest() {

        Map<String, Long> twu = new HashMap<String, Long>() {{
           put("1", Long.valueOf(4));
           put("2", Long.valueOf(3));
           put("3", Long.valueOf(1));
           put("4", Long.valueOf(2));
           put("5", Long.valueOf(1));
           put("6", Long.valueOf(4));

        }};

        List<String> tr = new ArrayList<String>();
        tr.add("1 2 3 5:26:3 3 2 3");
        tr.add("2 4:20:2 7");
        tr.add("1 3 4 5:25:2 5 5 2");
        tr.add("1 3 4 5 6:29:1 6 5 1 2");

        ItemParamMap itemUnitProfitMap = new ItemUnitProfitMap();

        ItemParamMap itemTWUMap = new ItemTWUMap();

        List<Transaction> transactions = (new TransactionDao()).fetch("base_test");
        System.out.println(transactions);
    }

}
