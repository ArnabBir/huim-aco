package com.maths.huim;

import com.maths.huim.dao.ItemUnitProfitMapDao;
import com.maths.huim.dao.TransactionDao;
import com.maths.huim.impl.ItemTwuMapImpl;
import com.maths.huim.impl.ItemUtilityTableImpl;
import com.maths.huim.models.*;
import com.maths.huim.utils.AntRoutingGraphUtils;
import com.maths.huim.utils.ItemTwuMapUtils;

import org.junit.*;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        // Calcualte union of 2 tables
        ItemUtilityTable itemUtilityTable = itemUtilityTableImpl.union(itemUtilityTableMap.get(Arrays.asList( "1")), itemUtilityTableMap.get(Arrays.asList( "3")));
        System.out.println(" Union of 1 and 3 : ");
        System.out.println(itemUtilityTable);
        itemUtilityTableMap.put(itemUtilityTable.getItemSet(), itemUtilityTable);
        itemUtilityTable = itemUtilityTableImpl.union(itemUtilityTableMap.get(Arrays.asList( "1", "3")), itemUtilityTableMap.get(Arrays.asList( "4")));
        System.out.println(" Union of 1, 3 and 4 : ");
        System.out.println(itemUtilityTable);

        //Get the initial graph
        AntRoutingGraphUtils antRoutingGraphUtils = new AntRoutingGraphUtils();
        AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.init(itemTwuMap);
        System.out.println(antRoutingGraph);


    }

    @Test
    public void asulaTest() {
//        TspProblemConfiguration configurationProvider = new TspProblemConfiguration(problemRepresentation);
//        AntColony<Integer, TspEnvironment> colony = getAntColony(configurationProvider);
//        TspEnvironment environment = new TspEnvironment(problemRepresentation);
//
//        AcoProblemSolver<Integer, TspEnvironment> solver = new AcoProblemSolver<>();
//        solver.initialize(environment, colony, configurationProvider);
//        solver.addDaemonActions(new StartPheromoneMatrix<Integer, TspEnvironment>(),
//                new PerformEvaporation<Integer, TspEnvironment>());
//
//        solver.addDaemonActions(getPheromoneUpdatePolicy());
//
//        solver.getAntColony().addAntPolicies(new RandomNodeSelection<Integer, TspEnvironment>());
//        solver.solveProblem();
    }

}
