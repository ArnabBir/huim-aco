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


import java.sql.SQLOutput;
import java.util.*;

public class POC {

    @Test
    public void run() {

        // Fetching transactions
        Set<String> itemSet = new HashSet<String>();
        List<Transaction> transactions = (new TransactionDao()).fetch("connect_utility_spmf", itemSet);

        // Calculating item twu map
        ItemTwuMapImpl itemTwuMapImpl = new ItemTwuMapImpl();
        ItemTwuMap itemTwuMap = itemTwuMapImpl.calculate(transactions, itemSet);
        itemTwuMapImpl = null;

        System.out.println("Hello Ji 1!");
        //Sorting Itemset
        ItemTwuMapUtils itemTwuMapUtils = new ItemTwuMapUtils();
        itemTwuMapUtils.sortDesc(itemTwuMap);

        //Pruning Itemset
        itemTwuMapUtils.prune(transactions, itemTwuMap, itemSet);
        itemSet = null;
        itemTwuMapUtils = null;
        System.out.println("Hello Ji 2!");
        // Calculating Item Utility Mapping
        ItemUtilityTableImpl itemUtilityTableImpl = new ItemUtilityTableImpl();
        Map<List<String>, ItemUtilityTable> itemUtilityTableMap = itemUtilityTableImpl.init(transactions, itemTwuMap);
        transactions = null;
        itemUtilityTableImpl = null;
        System.out.println("Hello Ji 3!");

        AntRoutingGraphUtils antRoutingGraphUtils = new AntRoutingGraphUtils();
        //AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.init(itemTwuMap);
        AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.bootstrapAntGraph(itemTwuMap);
        //System.out.println(antRoutingGraph);
        System.out.println("Hello Ji 4!");

        Map<List<String>, Long> itemSetCountMap = new HashMap<List<String>, Long>();
        long countNodes = 0;

        PathUtil maxPathUtil = new PathUtil();
        long keyCount = itemTwuMap.getMap().keySet().size();
        itemTwuMap = null;


        for(int g = 0; g < Constants.maxG && countNodes < keyCount; ++g) {
            maxPathUtil = new PathUtil();
            for (int i = 0; i < Constants.antCount && countNodes < keyCount; ++i) {
                countNodes += antRoutingGraphUtils.antTraverse(antRoutingGraph.getRoot(), itemUtilityTableMap, itemSetCountMap, maxPathUtil, 0);
            }
            //System.out.println(maxPathUtil);
            if(maxPathUtil.getUtil() > 0) {     // GLOBAL UPDATE IS NOT CONVERGING
                antRoutingGraphUtils.globalUpdatePheromone(antRoutingGraph, maxPathUtil);
            }
            System.out.println(g);
        }

        System.out.println(keyCount + " -> " + countNodes);
        System.out.println(itemSetCountMap);

    }

    @Test
    public void sanityTest() {

        // Fetching item unit profit
        ItemUnitProfitMapDao itemUnitProfitMapDao = new ItemUnitProfitMapDao();
        ItemUnitProfitMap itemUnitProfitMap = itemUnitProfitMapDao.fetch("base_test");
        System.out.println(itemUnitProfitMap);

        // Fetching transactions
        Set<String> itemSet = new HashSet<String>();
        List<Transaction> transactions = (new TransactionDao()).fetch("base_test", itemSet);
        System.out.println(transactions);
        System.out.println(itemSet);

        // Calculating item twu map
        ItemTwuMapImpl itemTwuMapImpl = new ItemTwuMapImpl();
        ItemTwuMap itemTwuMap = itemTwuMapImpl.calculate(transactions, itemSet);
        System.out.println(itemTwuMap);

        //Sorting Itemset
        ItemTwuMapUtils itemTwuMapUtils = new ItemTwuMapUtils();
        itemTwuMapUtils.sortDesc(itemTwuMap);
        System.out.println("Sorted Map = " + itemTwuMap);

        //Pruning Itemset
        itemTwuMapUtils.prune(transactions, itemTwuMap, itemSet);
        System.out.println("Pruned Map = " + itemTwuMap);
        System.out.println(transactions);

        // Calculating Item Utility Mapping
        ItemUtilityTableImpl itemUtilityTableImpl = new ItemUtilityTableImpl();
        Map<List<String>, ItemUtilityTable> itemUtilityTableMap = itemUtilityTableImpl.init(transactions, itemTwuMap);
        System.out.println(itemUtilityTableMap);
        boolean var = itemUtilityTableMap.containsKey(Arrays.asList("1"));
        System.out.println(var);

        // Calcualte computeClosure of 2 tables
        ItemUtilityTable itemUtilityTable;
        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList("1")), itemUtilityTableMap.get(Arrays.asList("3")));
        System.out.println(" Union of 1 and 3 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList("1", "3")), itemUtilityTableMap.get(Arrays.asList("5")));
        System.out.println(" Union of 1, 3 and 5 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList("1", "3", "5")), itemUtilityTableMap.get(Arrays.asList("4")));
        System.out.println(" Union of 1, 3, 5 and 4 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(Arrays.asList("1", "3", "5", "4")), itemUtilityTableMap.get(Arrays.asList("2")));
        System.out.println(" Union of 1, 3, 5, 4 and 2 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);

        System.out.println(" sumItemUtility = " + itemUtilityTableImpl.sumItemUtility(itemUtilityTable));
        System.out.println(" sumResidualUtility = " + itemUtilityTableImpl.sumResidualUtility(itemUtilityTable));

        // Gen - CHUI
//        GenChui genChui = new GenChui();
//        genChui.setItemSet(Collections.<String>emptyList());
//        genChui.setPrevSet(Collections.<String>emptyList());
//        List<String> postSet = new ArrayList<String>(itemTwuMap.getMap().keySet());
//        //postSet.remove("1");
//        genChui.setPostSet(postSet);
//        System.out.println(genChui);
//        GenChuiImpl genChuiImpl = new GenChuiImpl();
//        List<List<String>> closedItemSets = new ArrayList<>();
//        genChuiImpl.execute(genChui, itemUtilityTableMap, closedItemSets);
//        System.out.println(" closedItemSets = " + closedItemSets);

        //Get the initial graph
        System.out.println(itemTwuMap);
        AntRoutingGraphUtils antRoutingGraphUtils = new AntRoutingGraphUtils();
        //AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.init(itemTwuMap);
        AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.bootstrapAntGraph(itemTwuMap);
        System.out.println(antRoutingGraph);

        // Traverse through the graph
        Map<List<String>, Long> itemSetCountMap = new HashMap<List<String>, Long>();
        long countNodes = 0;

        PathUtil maxPathUtil = new PathUtil();

        //for(int g = 0; g < Constants.maxG && countNodes < Math.pow(2, itemTwuMap.getMap().keySet().size()) - 1; ++g) {
        for(int g = 0; g < Constants.maxG && countNodes < itemTwuMap.getMap().keySet().size(); ++g) {
            maxPathUtil = new PathUtil();
            for (int i = 0; i < Constants.antCount && countNodes < itemTwuMap.getMap().keySet().size(); ++i) {
                countNodes += antRoutingGraphUtils.antTraverse(antRoutingGraph.getRoot(), itemUtilityTableMap, itemSetCountMap, maxPathUtil, 0);
            }
            System.out.println(g + " -> " + countNodes + " -> " + itemSetCountMap);
            //System.out.println(maxPathUtil);
            if(maxPathUtil.getUtil() > 0) {     // GLOBAL UPDATE IS NOT CONVERGING
                //antRoutingGraphUtils.globalUpdatePheromone(antRoutingGraph, maxPathUtil);
            }
        }

        System.out.println(countNodes);
        System.out.println(itemSetCountMap);

    }

}
