package com.maths.huim;

import com.maths.huim.dao.ItemUnitProfitMapDao;
import com.maths.huim.dao.TransactionDao;
import com.maths.huim.impl.GenChuiImpl;
import com.maths.huim.impl.ItemTwuMapImpl;
import com.maths.huim.impl.ItemUtilityTableImpl;
import com.maths.huim.models.*;
import com.maths.huim.utils.AntRoutingGraphUtils;
import com.maths.huim.utils.ItemTwuMapUtils;

import org.junit.*;


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
        ItemUtilityTableImpl itemUtilityTableImpl = new ItemUtilityTableImpl();
        Map<List<String>, ItemUtilityTable> itemUtilityTableMap = itemUtilityTableImpl.init(transactions, itemUnitProfitMap, itemTwuMap);
        System.out.println(itemUtilityTableMap);
        boolean var = itemUtilityTableMap.containsKey(Arrays.asList("1"));
        System.out.println(var);

        // Calcualte computeClosure of 2 tables
        ItemUtilityTable itemUtilityTable;
        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList( "1")), itemUtilityTableMap.get(Arrays.asList( "3")));
        System.out.println(" Union of 1 and 3 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList( "1", "3")), itemUtilityTableMap.get(Arrays.asList( "5")));
        System.out.println(" Union of 1, 3 and 5 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList( "1", "3", "5")), itemUtilityTableMap.get(Arrays.asList( "4")));
        System.out.println(" Union of 1, 3, 5 and 4 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList( "1", "3", "5", "4")), itemUtilityTableMap.get(Arrays.asList( "2")));
        System.out.println(" Union of 1, 3, 5, 4 and 2 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        // Gen - CHUI
        GenChui genChui = new GenChui();
        genChui.setItemSet(Collections.<String>emptyList());
        genChui.setPrevSet(Collections.<String>emptyList());
        List<String> postSet = new ArrayList<String>(itemTwuMap.getMap().keySet());
        //postSet.remove("1");
        genChui.setPostSet(postSet);
        System.out.println(genChui);
        GenChuiImpl genChuiImpl = new GenChuiImpl();
        List<List<String>> closedItemSets = new ArrayList<>();
        genChuiImpl.execute(genChui, itemUtilityTableMap, closedItemSets);
        System.out.println(" closedItemSets = " + closedItemSets);

        //Get the initial graph
        System.out.println(itemTwuMap);
        AntRoutingGraphUtils antRoutingGraphUtils = new AntRoutingGraphUtils();
        AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.init(itemTwuMap);
        System.out.println(antRoutingGraph);

        // Traverse through the graph
        Map<List<String>, Long> itemSetCountMap = new HashMap<List<String>, Long>();
        long countNodes = 0;
        for(int i = 0; i < 10000 && countNodes < Math.pow(2,5) - 1; ++i) {
            //System.out.println(i);
            countNodes += antRoutingGraphUtils.antTraverse(antRoutingGraph.getRoot(), itemUtilityTableMap, itemSetCountMap, 0);
        }

        System.out.println(countNodes);
        System.out.println(itemSetCountMap);

    }

}
